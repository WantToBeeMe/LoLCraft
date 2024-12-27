package me.wanttobee.lolcraft.champions.test

import me.wanttobee.lolcraft.base.abilities.AbilityState
import me.wanttobee.lolcraft.base.abilities.IAbility

object TestAbility : IAbility {
    override fun invokeInitial(state: AbilityState) {
        state.owner.player.sendMessage("INITIAL INVOKE")
    }

    override fun invokeRecast(state: AbilityState, recastCount: Int) {
        state.owner.player.sendMessage("RECAST $recastCount")
    }

    override fun invokePassive(state: AbilityState) {
        state.owner.player.sendMessage("PASSIVE")
    }
}