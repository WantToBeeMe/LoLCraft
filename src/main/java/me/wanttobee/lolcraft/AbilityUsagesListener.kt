package me.wanttobee.lolcraft

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemHeldEvent

object AbilityUsagesListener : Listener {


    @EventHandler
    fun onItemHeld(event: PlayerItemHeldEvent) {
        val player = event.player
        val previousSlot = event.previousSlot
        val newSlot = event.newSlot

        player.sendMessage("Switched from slot $previousSlot to slot $newSlot")
        player.inventory.heldItemSlot = 0
    }
}
