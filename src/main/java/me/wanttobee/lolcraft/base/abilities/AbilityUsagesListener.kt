package me.wanttobee.lolcraft.base.abilities

import me.wanttobee.lolcraft.base.players.PlayerContextSystem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent

object AbilityUsagesListener : Listener {

    @EventHandler
    fun onItemHeld(event: PlayerItemHeldEvent) {
        val player = event.player

        val settings = PlayerContextSystem.getContext(player).settings
        if (!settings.quickCastEnabled) return

        if( player.inventory.heldItemSlot != Ability.Slot.PASSIVE.index)
            simulateClick(player)

        player.inventory.heldItemSlot = Ability.Slot.PASSIVE.index
    }

    // even though we can also just invoke the click event right away, there might be different things that we should do with left click button
    // And to make it, so it all goes through the exact same path, we might as well just simulate the complete left click
    private fun simulateClick(player: Player) {
        val heldItem = player.inventory.itemInMainHand
        if (heldItem.type == Material.AIR) return

        val interactEvent = PlayerInteractEvent(
            player, Action.RIGHT_CLICK_AIR,
            heldItem, null,
            BlockFace.SELF
        )
        Bukkit.getPluginManager().callEvent(interactEvent)
    }
}
