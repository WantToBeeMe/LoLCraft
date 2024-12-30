package me.wanttobee.lolcraft.test.champion

import me.wanttobee.lolcraft.MinecraftPlugin
import me.wanttobee.lolcraft.base.abilities.states.BaseAbilityState
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.abilities.states.PassiveAbilityState
import me.wanttobee.lolcraft.base.util.AbilitySlot
import org.bukkit.Bukkit

object TestToggleAbility : IAbility<TestChampion> {
    override val defaultSlot: AbilitySlot =  AbilitySlot.R_ABILITY
    override val defaultIconTexture: String = "anivia_r"
    override val title: String= "Toggle Example"
    override val maxLevel: Int = 3

    override fun initializeState(state: BaseAbilityState<TestChampion>) {
        val passiveState = state as PassiveAbilityState
        passiveState.maxCoolDown = 2.0
    }

    private fun iterate(state: BaseAbilityState<TestChampion>){
        state.owner.player.sendMessage("Toggle Active ${state.championState.counter++}")
    }

    override fun invokeInitial(state: BaseAbilityState<TestChampion>) {
        val championState = state.championState

        championState.toggleAbilityId = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftPlugin.instance, {
            iterate(state)
            //championState.toggleAbilityId?.let { Bukkit.getScheduler().cancelTask(it) }
        }, 5L ,10L)
    }

    override fun invokeAbilityCancel(state: BaseAbilityState<TestChampion>, channelTime: Double) {
        state.owner.player.sendMessage("Toggle De-activated ")
        state.championState.counter = 0
        state.championState.toggleAbilityId?.let { Bukkit.getScheduler().cancelTask(it) }
    }
}