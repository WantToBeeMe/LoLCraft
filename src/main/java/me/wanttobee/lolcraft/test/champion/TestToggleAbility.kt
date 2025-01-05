package me.wanttobee.lolcraft.test.champion

import me.wanttobee.lolcraft.MinecraftPlugin
import me.wanttobee.lolcraft.base.abilities.states.BaseAbilityState
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.abilities.states.CastAbilityState
import me.wanttobee.lolcraft.base.abilities.states.PassiveAbilityState
import me.wanttobee.lolcraft.base.abilities.states.RecastAbilityState
import me.wanttobee.lolcraft.base.util.AbilitySlot
import org.bukkit.Bukkit

object TestToggleAbility : IAbility<TestChampion> {
    override val defaultSlot: AbilitySlot =  AbilitySlot.R_ABILITY
    override val defaultIconTexture: String = "anivia_r"
    override val title: String= "Toggle Example"
    override val maxLevel: Int = 3

    override fun initializeState(state: BaseAbilityState<TestChampion>) {
        if(state !is CastAbilityState) return

        state.maxCoolDown = 2.0
        state.manaCost = 60
        state.upgrade()
    }

    private fun iterate(state: BaseAbilityState<TestChampion>){
        state.owner.player.sendMessage("Toggle Active ${state.championState.counter++}")
        state.owner.stats.mana.change(-35 * 0.5) // -35 per second, but the task runs every 0.5 seconds
    }

    override fun invokeInitial(state: BaseAbilityState<TestChampion>) {
        if(state !is CastAbilityState) return
        val championState = state.championState
        state.manaCost = (35 / 2) + 1 // -35 per second, but the task runs every 0.5 seconds
        // also +1 since manaCost can only be an integer on the items themselves, and the +1 is just a buffer for the floor of the integer division
        championState.toggleAbilityId = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftPlugin.instance, {
            iterate(state)
            //championState.toggleAbilityId?.let { Bukkit.getScheduler().cancelTask(it) }
        }, 5L ,10L)
    }

    override fun invokeAbilityCancel(state: BaseAbilityState<TestChampion>, channelTime: Double) {
        if(state !is CastAbilityState) return
        state.manaCost = 60
        state.owner.player.sendMessage("Toggle De-activated ")
        state.championState.counter = 0
        state.championState.toggleAbilityId?.let { Bukkit.getScheduler().cancelTask(it) }
    }
}