package me.wanttobee.lolcraft.test.champion

import me.wanttobee.lolcraft.base.abilities.states.BaseAbilityState
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.abilities.states.PassiveAbilityState
import me.wanttobee.lolcraft.base.abilities.states.RecastAbilityState
import me.wanttobee.lolcraft.base.util.AbilitySlot

object TestRecastAbility : IAbility<TestChampion> {
    override val defaultSlot: AbilitySlot =  AbilitySlot.Q_ABILITY
    override val defaultIconTexture: String = "anivia_q"
    override val title: String= "Unknown Ability"
    override val maxLevel: Int = 5

    override fun initializeState(state: BaseAbilityState<TestChampion>) {
        if(state !is RecastAbilityState) return

        state.maxCoolDown = 6
        state.maxChannelTime = 3.0
    }

    override fun invokeInitial(state: BaseAbilityState<TestChampion>) {
        state.owner.player.sendMessage("Start Recast")
    }

    override fun invokeRecast(state: BaseAbilityState<TestChampion>, channelTime: Double, recastCount: Int) {
        state.owner.player.sendMessage("Recast ended at $channelTime - $recastCount")
    }
}