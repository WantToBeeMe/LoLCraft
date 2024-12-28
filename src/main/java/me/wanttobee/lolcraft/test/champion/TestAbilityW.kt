package me.wanttobee.lolcraft.test.champion

import me.wanttobee.lolcraft.base.abilities.states.AbilityState
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.util.AbilitySlot

object TestAbilityW : IAbility {
    override val defaultSlot: AbilitySlot =  AbilitySlot.W_ABILITY
    override val iconTextureName: String = "anivia_w"
    override val title: String= "Unknown Ability w"

    override fun invokeInitial(state: AbilityState) {
        state.owner.player.sendMessage("INITIAL INVOKE W")
    }

    override fun invokeRecast(state: AbilityState, recastCount: Int) {
        state.owner.player.sendMessage("RECAST $recastCount W")
    }

    override fun invokePassive(state: AbilityState) {
        state.owner.player.sendMessage("PASSIVE W")
    }
}