package me.wanttobee.lolcraft.test.champion

import me.wanttobee.lolcraft.base.abilities.states.BaseAbilityState
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.abilities.states.PassiveAbilityState
import me.wanttobee.lolcraft.base.util.AbilitySlot

object TestPassiveAbility : IAbility<TestChampion> {
    override val defaultSlot: AbilitySlot =  AbilitySlot.PASSIVE
    override val defaultIconTexture: String = "anivia_p"
    override val title: String = "Unknown Ability"
    override val maxLevel: Int = 0

    override fun initializeState(state: BaseAbilityState<TestChampion>) {
        val passiveState = state as PassiveAbilityState
        passiveState.maxCoolDown = 360
    }

    override fun invokeInitial(state: BaseAbilityState<TestChampion>) {
        state.owner.player.sendMessage("INITIAL INVOKE P")
    }
}