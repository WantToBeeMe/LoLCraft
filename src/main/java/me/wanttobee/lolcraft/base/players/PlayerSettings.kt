package me.wanttobee.lolcraft.base.players

import me.wanttobee.lolcraft.base.abilities.Ability

class PlayerSettings(val context: PlayerContext) {
    private val inventory = PlayerSettingsInventory(this)

    // off: allows the user to scroll through the hotBar and left-click an ability to use
    // on: allows the user to click the slot of the ability, and cast right away
    private var _quickCastEnabled = false
    var quickCastEnabled: Boolean
        get() = _quickCastEnabled
        set(value) {
            _quickCastEnabled = value
            if(value) context.player.inventory.heldItemSlot = Ability.Slot.PASSIVE.index
        }

    fun openSettings(){
        inventory.open(context.player)
    }
}