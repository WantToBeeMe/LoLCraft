package me.wanttobee.lolcraft.base.abilities.states

import me.wanttobee.everythingitems.interactiveitems.InteractiveHotBarItem
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.champions.ChampionState

open class CastAbilityState<T>(championState: T, ability : IAbility<T>) : PassiveAbilityState<T>(championState, ability)  where T : ChampionState {

    protected open fun invokeRightClick() {
        invokeInitial()
    }

    override fun createNewHotBarItem() : InteractiveHotBarItem {
        return InteractiveHotBarItem(item)
            .setRightClickEvent { _,_ -> invokeRightClick() }
    }
}