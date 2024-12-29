package me.wanttobee.lolcraft.base.abilities.states

import me.wanttobee.lolcraft.MinecraftPlugin
import org.bukkit.Bukkit

open class ChannelAbilityState {

    /*
    private var chargeCounters = 0
    private var chargeTasks : Int? = null

    fun chargeAbility() {
        chargeCounters += 1
        chargeTasks?.let { Bukkit.getScheduler().cancelTask(it) }

        val taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(MinecraftPlugin.instance, {
            //invoke(chargeCounters)
            chargeCounters = 0
            chargeTasks = null
        }, 5L) //(note that delay is in ticks)
        // 5L For normal mode / 11L for Quick Cast mode (11 for the first!!, the rest can probably be lower)
        // Note that you can do +1 for both to ensure server does not lag and stuff. also note that this may also change depending on the server specs

        chargeTasks = taskId
    }
    */
}