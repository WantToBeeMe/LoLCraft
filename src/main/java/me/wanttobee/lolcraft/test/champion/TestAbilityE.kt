package me.wanttobee.lolcraft.test.champion

import me.wanttobee.lolcraft.base.abilities.states.AbilityState
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.util.AbilitySlot

object TestAbilityE : IAbility {
    override val defaultSlot: AbilitySlot =  AbilitySlot.E_ABILITY
    override val iconTextureName: String = "anivia_e"
    override val title: String= "Unknown Ability"

    override fun invokeInitial(state: AbilityState) {
        state.owner.player.sendMessage("INITIAL INVOKE E")
    }

    override fun invokeRecast(state: AbilityState, recastCount: Int) {
        state.owner.player.sendMessage("RECAST $recastCount E")
    }

    override fun invokePassive(state: AbilityState) {
        state.owner.player.sendMessage("PASSIVE E")
    }
}