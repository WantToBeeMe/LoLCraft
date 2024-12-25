package me.wanttobee.lolcraft.base.abilities

import me.wanttobee.everythingitems.UniqueItemStack
import me.wanttobee.everythingitems.interactiveitems.InteractiveHotBarItem
import me.wanttobee.lolcraft.MinecraftPlugin
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

abstract class Ability(private val defaultSlot : Slot){
    enum class Slot(val index: Int) {
        PASSIVE(8),
        Q_ABILITY(0),
        W_ABILITY(1),
        E_ABILITY(2),
        R_ABILITY(3),
        D_SPELL(4),
        F_SPELL(5),
        RECALL(6),
        WARD(7)
    }

    protected val item = UniqueItemStack(Material.STICK, "Unknown Ability", null, 1)

    abstract fun invoke(player: Player, chargeCount: Int)

    private val chargeCounters = mutableMapOf<Player, Int>()
    private val chargeTasks = mutableMapOf<Player, Int>()

    fun setToPlayer(player: Player, slot : Slot = defaultSlot){
        val hotBarItem = InteractiveHotBarItem(item)
        hotBarItem.setSlot(slot.index).giveToPlayer(player)
        hotBarItem.setRightClickEvent { p, _ -> chargeAbility(p) }
    }

    private fun chargeAbility(player: Player) {
        val currentCharge = chargeCounters.getOrDefault(player, 0) + 1
        chargeCounters[player] = currentCharge
        chargeTasks[player]?.let { Bukkit.getScheduler().cancelTask(it) }

        val taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(MinecraftPlugin.instance, {
            if (chargeCounters.containsKey(player)) {
                invoke(player, chargeCounters[player]!!)
                chargeCounters.remove(player)
                chargeTasks.remove(player)
            }
        }, 5L) //(note that delay is in ticks)
        // 5L For normal mode / 11L for Quick Cast mode
        // Note that you can do +1 for both to ensure server does not lag and stuff. also note that this may also change depending on the server specs

        chargeTasks[player] = taskId
    }
}