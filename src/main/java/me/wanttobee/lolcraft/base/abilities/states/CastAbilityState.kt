package me.wanttobee.lolcraft.base.abilities.states

import me.wanttobee.everythingitems.interactiveitems.InteractiveHotBarItem
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.champions.ChampionState

open class CastAbilityState<T>(championState: T, ability : IAbility<T>) : PassiveAbilityState<T>(championState, ability)  where T : ChampionState {
    var manaCost : Int? = null
        set(value) {
            field = value
            item.setManaCost(value)
        }

    var enoughMana : Boolean = true
        private set

    init {
        owner.stats.mana.subscribe(::onManaChange, 999) // we want this to be somewhere at the end. we just want to read if the ability is castable, thats all
    }

    private fun onManaChange(manaChange: Double) : Double{
        val oldEnough = enoughMana
        enoughMana = manaCost == null || owner.stats.mana.current >= manaCost!!
        if(oldEnough == enoughMana)
            return manaChange

        item.setNoMana(!enoughMana)
        if(!enoughMana)
            onDisrupted()

        return manaChange
    }

    protected open fun invokeRightClick() {
        invokeInitial()
    }

    override fun invokeInitial() {
        if(!initialized)
            throw IllegalStateException("Cannot invoke passive on an ability that has not been initialized yet. " +
                    "[ability: ${ability.title} - player: ${owner.player.name}]")

        if(!canCast())
            return

        subtractMana()
        ability.invokeInitial(this)
        startCoolDown()
    }

    override fun canCast(): Boolean {
        if(!enoughMana)
            return false
        return super.canCast()
    }

    protected fun subtractMana(){
        if(manaCost != null)
            owner.stats.mana.change(-manaCost!!.toDouble())
    }

    override fun createNewHotBarItem() : InteractiveHotBarItem {
        return InteractiveHotBarItem(item)
            .setRightClickEvent { _,_ -> invokeRightClick() }
    }
}