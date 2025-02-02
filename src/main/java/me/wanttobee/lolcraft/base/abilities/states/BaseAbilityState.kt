package me.wanttobee.lolcraft.base.abilities.states

import me.wanttobee.everythingitems.interactiveitems.InteractiveHotBarItem
import me.wanttobee.lolcraft.base.abilities.AbilityItem
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.champions.ChampionState
import me.wanttobee.lolcraft.base.players.PlayerContext
import me.wanttobee.lolcraft.base.players.state.CCGroupState
import me.wanttobee.lolcraft.base.players.state.CCState

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

    // Not usefully for all abilities
    var level: Int = 0
        private set

    init {
        owner.playerState.disrupts.subscribe(::onStunOrSilence)
        owner.playerState.isDead.subscribe(::onDeath)
        item.setMaxLevel(ability.maxLevel)
    }

    private fun onStunOrSilence(disrupt: CCGroupState) {
        isDisrupted = disrupt.value
        onDisrupted()
        item.setDisrupted(isDisrupted)
    }

    private fun onDeath(disrupt: CCState) {
        item.setDead(isDisrupted)
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

    // TODO: This should also be locked behind a "CanUpgrade" method or something
    //  That if it does not have the upgrade icon, that it also can't actually upgrade or something
    fun upgrade() {
        if(ability.maxLevel == 0)
            throw IllegalStateException("Cannot upgrade an ability that has no levels. [ability: ${ability.title} - player: ${owner.player.name}]")

        if (level >= ability.maxLevel)
            return
        level++
        item.setCurrentLevel(level)

        ability.onAbilityLevelUp(this)
    }
}
