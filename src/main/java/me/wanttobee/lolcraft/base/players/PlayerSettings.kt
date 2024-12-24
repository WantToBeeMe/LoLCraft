package me.wanttobee.lolcraft.base.players

import me.wanttobee.lolcraft.base.abilities.Ability
import org.bukkit.entity.Player

class PlayerSettings(private var _player: Player) {
    private val inventory = PlayerSettingsInventory(this)

    // off: allows the user to scroll through the hotBar and left-click an ability to use
    // on: allows the user to click the slot of the ability, and imidialty cast it
    private var _quickCastEnabled = false
    var quickCastEnabled: Boolean
        get() = _quickCastEnabled
        set(value) {
            _quickCastEnabled = value
            if(value) player.inventory.heldItemSlot = Ability.Slot.PASSIVE.index
        }

    val player: Player
        get() = _player

    fun openSettings(){
        inventory.open(player)
    }

    fun swapPlayer(newPlayer: Player) {
        if (newPlayer != _player)
            _player = newPlayer
    }
}