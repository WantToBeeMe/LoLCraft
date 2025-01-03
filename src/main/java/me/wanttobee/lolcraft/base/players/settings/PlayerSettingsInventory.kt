package me.wanttobee.lolcraft.base.players.settings

import me.wanttobee.everythingitems.UniqueItemStack
import me.wanttobee.everythingitems.interactiveinventory.InteractiveInventory
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

class PlayerSettingsInventory(private val settings: PlayerSettings) : InteractiveInventory(){
    // We don't set the owner, since if the player leaves and then rejoins, that player instance is not the same anymore, and so the owner is wrong
    override var inventory: Inventory =  Bukkit.createInventory(null, InventoryType.HOPPER ,
        "${ChatColor.DARK_AQUA}${settings.context.player.name} Settings")
    private var settingIndex = 0

    init{
        val emptyItem = UniqueItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ", null)

        addHotBarSetting()
        addChannelAsRecastSettings()
        addChannelBufferSetting()

        addLockedItem(3, emptyItem)
        addLockedItem(4, emptyItem)
    }

    // HotBar Setting
    private fun addHotBarSetting(){
        val item = UniqueItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "HotBar usage", listOf(
            "${ChatColor.DARK_GRAY}Default: HotBar usage like default minecraft", "${ChatColor.DARK_GRAY}QuickCast: quick HotBar usage using the hotkeys"))
        addGenericSetting(item, {
            item.updateTitle("${ChatColor.DARK_AQUA}HotBar Usage:${ChatColor.YELLOW} ${if(settings.quickCastEnabled) "QuickCast" else "Default"}")
            .updateMaterial(if(settings.quickCastEnabled) Material.BLAZE_ROD else Material.BREEZE_ROD)
            .pushUpdates()
        }, { _, _ ->
            settings.quickCastEnabled = !settings.quickCastEnabled
        })
    }

    // Channel Buffer Setting
    private fun addChannelBufferSetting(){
        // This should be a number (Long) that increase by 1 with left mouse, and decrease by 1 with right mouse
        // It should go from 1 to 30

        val item = UniqueItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "Channel Buffer", listOf(
            "${ChatColor.DARK_GRAY}The amount of time in ticks",
            "${ChatColor.DARK_GRAY}that is added as a buffer for the channel",
            "${ChatColor.DARK_GRAY}(only relevant when channeling is enabled)"))
        addGenericSetting(item, {
            item.updateTitle("${ChatColor.DARK_AQUA}Channel Buffer:${ChatColor.YELLOW} ${settings.channelBuffer} ticks")
                .updateMaterial(if(settings.channelBuffer == 2L) Material.GLOWSTONE_DUST else if(settings.channelBuffer == 30L) Material.GUNPOWDER else Material.REDSTONE )
                .pushUpdates()
        }, { _, _ -> settings.channelBuffer++ }, { _, _ -> settings.channelBuffer-- })
    }

    // Channel As Recast Setting
    private fun addChannelAsRecastSettings(){
        val item = UniqueItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "Channel as Recast", listOf(
            "${ChatColor.DARK_GRAY}Default: Channeling is done by holding right-click", "${ChatColor.DARK_GRAY}Recast: Channeling is done by clicking right-click multiple times"))
        addGenericSetting(item, {
            item.updateTitle("${ChatColor.DARK_AQUA}Channel As Recast:${ChatColor.YELLOW} ${if(settings.channelAsRecast) "Recast" else "Default"}")
                .updateMaterial(if(settings.channelAsRecast) Material.BLAZE_ROD else Material.BREEZE_ROD)
                .pushUpdates()
        }, { _, _ ->
            settings.channelAsRecast = !settings.channelAsRecast
        })
    }


    // These are helper methods that's makes it a little bit easier to create a setting.
    // What this does is add 1 extra lambda, that only is responsible for updating the icon. And then the other
    // 2 actions can keep their responsibility for the functionality
    // (and it also internally calculates the slot)
    private fun addGenericSetting(item: UniqueItemStack, updateItem: ()->Unit, onClick: (Player, Boolean) -> Unit){
        addGenericSetting(item, updateItem, onClick, onClick)
    }
    private fun addGenericSetting(item: UniqueItemStack, updateItem:()->Unit, onLeftClick: (Player, Boolean) -> Unit, onRightClick: (Player, Boolean) -> Unit){
        // note that the itemStack only has to have the attributes that are not changed by the updateItem lambda
        // for example a lore that never changes, or a material that never changes
        updateItem.invoke()
        addLockedItem(
            settingIndex, item,
            { player, shift ->
                onLeftClick.invoke(player,shift)
                updateItem.invoke()
            }, { player, shift ->
                onRightClick.invoke(player, shift)
                updateItem.invoke()
            })

        // this is going to break when we get more settings than slots lol
        settingIndex++
    }
}
