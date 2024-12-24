package me.wanttobee.lolcraft;

import me.wanttobee.commandtree.CommandTreeSystem
import me.wanttobee.everythingitems.ItemUtil
import me.wanttobee.lolcraft.base.abilities.AbilityUsagesListener
import me.wanttobee.lolcraft.base.players.PlayerContextSystem
import me.wanttobee.lolcraft.util.LolCommands
import me.wanttobee.lolcraft.util.MyLolCommands
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

class MinecraftPlugin : JavaPlugin() {
    companion object {
        lateinit var instance: MinecraftPlugin
        val title = "${ChatColor.GRAY}[${ChatColor.GOLD}LoL Craft${ChatColor.GRAY}]${ChatColor.RESET}"
    }

    override fun onEnable() {
        instance = this
        CommandTreeSystem.initialize(instance, "${ChatColor.GREEN}(C)$title")
        ItemUtil.initialize(instance, "${ChatColor.LIGHT_PURPLE}(I)$title")

        server.pluginManager.registerEvents(AbilityUsagesListener, this)
        server.pluginManager.registerEvents(PlayerContextSystem, this)

        CommandTreeSystem.createCommand(LolCommands)
        CommandTreeSystem.createCommand(MyLolCommands)

        server.onlinePlayers.forEach { player ->
            player.sendMessage("$title Plugin has been enabled!")
        }
    }

    override fun onDisable() {
        ItemUtil.disablePlugin()

        server.onlinePlayers.forEach { player ->
            player.sendMessage("$title Plugin has been disabled!")
        }
    }
}
