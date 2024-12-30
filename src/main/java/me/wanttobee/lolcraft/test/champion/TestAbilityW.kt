package me.wanttobee.lolcraft.test.champion

import me.wanttobee.lolcraft.base.abilities.states.BaseAbilityState
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.abilities.states.PassiveAbilityState
import me.wanttobee.lolcraft.base.util.AbilitySlot

object TestAbilityW : IAbility<TestChampion> {
    override val defaultSlot: AbilitySlot =  AbilitySlot.W_ABILITY
    override val defaultIconTexture: String = "anivia_w"
    override val title: String= "Unknown Ability"
    override val maxLevel: Int = 5

    override fun initializeState(state: BaseAbilityState<TestChampion>) {
        if(state !is PassiveAbilityState) return

        state.maxCoolDown = 6.0
    }

    override fun invokeInitial(state: BaseAbilityState<TestChampion>) {
        state.owner.player.sendMessage("INITIAL INVOKE W")
    }

    override fun invokeRecast(state: BaseAbilityState<TestChampion>, channelTime: Double, recastCount: Int) {
        state.owner.player.sendMessage("RECAST $channelTime W")
    }

    override fun invokePassive(state: BaseAbilityState<TestChampion>) {
        state.owner.player.sendMessage("PASSIVE W")
    }
}