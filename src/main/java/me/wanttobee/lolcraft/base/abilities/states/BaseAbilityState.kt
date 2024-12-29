package me.wanttobee.lolcraft.base.abilities.states

import me.wanttobee.everythingitems.interactiveitems.InteractiveHotBarItem
import me.wanttobee.lolcraft.base.abilities.AbilityItem
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.champions.ChampionState
import me.wanttobee.lolcraft.base.players.PlayerContext
import me.wanttobee.lolcraft.base.util.CCGroupState

// -= Ability State =-
// This is the base ability state, and it's basically only for passives that also don't have any invoking actions
//  Things like gnar's passive, akshan's passive
//        -=-
open class BaseAbilityState<T>(val championState: T, val ability : IAbility<T>) where T : ChampionState {
    protected var isDisrupted: Boolean = false
    val item = AbilityItem(ability.defaultIconTexture, ability.title, null)

    val owner: PlayerContext
        get() = championState.owner
    var initialized: Boolean = false
        private set

    init {
        owner.state.disrupts.subscribe(::onStunOrSilence)
        item.setMaxLevel(ability.maxLevel)
    }

    private fun onStunOrSilence(disrupt: CCGroupState) {
        isDisrupted = disrupt.value
        onDisrupted()
        item.setDisrupted(isDisrupted)
    }

    protected open fun onDisrupted() {}

     fun invokePassive(){
         if(!initialized)
                throw IllegalStateException("Cannot invoke passive on an ability that has not been initialized yet. " +
                        "[ability: ${ability.title} - player: ${owner.player.name}]")

         // Passives still go through if the player is disrupted
         // This is not any passive with a cooldown (e.g. anivia egg), instead this is passives like akshan's passives
         ability.invokePassive(this)
     }

    open fun createNewHotBarItem() : InteractiveHotBarItem{
        return InteractiveHotBarItem(item)
    }

    open fun postInitialize() : BaseAbilityState<T> {
        ability.initializeState(this)
        initialized = true
        return this
    }
}
