package me.wanttobee.lolcraft.base.players

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
        "${ChatColor.DARK_AQUA}${settings.player.name} Settings")
    private var settingIndex = 0

    init{
        val emptyItem = UniqueItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ", null)

        addHotBarSetting()
        addLockedItem(1, emptyItem)
        addLockedItem(2, emptyItem)
        addLockedItem(3, emptyItem)
        addLockedItem(4, emptyItem)
    }

    // HotBar setting
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
