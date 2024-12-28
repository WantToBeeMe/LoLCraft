package me.wanttobee.lolcraft.champions.test

import me.wanttobee.lolcraft.base.abilities.states.AbilityState
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.util.AbilitySlot

object TestAbilityQ : IAbility {
    override val defaultSlot: AbilitySlot =  AbilitySlot.Q_ABILITY
    override val iconTextureName: String = "anivia_q"
    override val title: String= "Unknown Ability"

    override fun invokeInitial(state: AbilityState) {
        state.owner.player.sendMessage("INITIAL INVOKE Q")
    }

    override fun invokeRecast(state: AbilityState, recastCount: Int) {
        state.owner.player.sendMessage("RECAST $recastCount Q")
    }

    override fun invokePassive(state: AbilityState) {
        state.owner.player.sendMessage("PASSIVE Q")
    }
}