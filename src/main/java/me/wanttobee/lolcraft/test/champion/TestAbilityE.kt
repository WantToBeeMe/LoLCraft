package me.wanttobee.lolcraft.test.champion

import me.wanttobee.lolcraft.base.abilities.states.BaseAbilityState
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.abilities.states.CastAbilityState
import me.wanttobee.lolcraft.base.util.AbilitySlot

object TestAbilityE : IAbility<TestChampion> {
    override val defaultSlot: AbilitySlot =  AbilitySlot.E_ABILITY
    override val defaultIconTexture: String = "anivia_e"
    override val title: String= "Unknown Ability"
    override val maxLevel: Int = 5

    override fun initializeState(state: BaseAbilityState<TestChampion>) {
        if(state !is CastAbilityState) return

        state.maxCoolDown = 26.0
        state.manaCost = 50
    }

    override fun invokeInitial(state: BaseAbilityState<TestChampion>) {
        state.owner.player.sendMessage("INITIAL INVOKE E")
    }

    override fun invokeRecast(state: BaseAbilityState<TestChampion>, channelTime: Double, recastCount: Int) {
        state.owner.player.sendMessage("RECAST $channelTime E")
    }

    override fun invokePassive(state: BaseAbilityState<TestChampion>) {
        state.owner.player.sendMessage("PASSIVE E")
    }
}