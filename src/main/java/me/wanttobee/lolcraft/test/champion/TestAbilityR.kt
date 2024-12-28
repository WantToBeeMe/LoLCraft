package me.wanttobee.lolcraft.test.champion

import me.wanttobee.lolcraft.base.abilities.states.AbilityState
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.util.AbilitySlot

object TestAbilityR : IAbility {
    override val defaultSlot: AbilitySlot =  AbilitySlot.R_ABILITY
    override val iconTextureName: String = "anivia_r"
    override val title: String= "Unknown Ability"

    override fun invokeInitial(state: AbilityState) {
        state.owner.player.sendMessage("INITIAL INVOKE R")
    }

    override fun invokeRecast(state: AbilityState, recastCount: Int) {
        state.owner.player.sendMessage("RECAST $recastCount R")
    }

    override fun invokePassive(state: AbilityState) {
        state.owner.player.sendMessage("PASSIVE R")
    }
}