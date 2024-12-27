package me.wanttobee.lolcraft.base.players

import me.wanttobee.lolcraft.base.abilities.AbilityState
import me.wanttobee.lolcraft.base.util.AbilitySlot

// -=- Player Settings -=-
//  The player context part that is responsible for all the settings
//  are all the info bits that the player can modify themselves and should in theory be saved, even if the server closes
//          -=-
class PlayerSettings(override val context: PlayerContext) : IPlayerContextPart {
    private val inventory = PlayerSettingsInventory(this)

    // off: allows the user to scroll through the hotBar and left-click an ability to use
    // on: allows the user to click the slot of the ability, and cast right away
    private var _quickCastEnabled = false
    var quickCastEnabled: Boolean
        get() = _quickCastEnabled
        set(value) {
            _quickCastEnabled = value
            if(value) context.player.inventory.heldItemSlot = AbilitySlot.PASSIVE.index
        }

    fun openSettings(){
        inventory.open(context.player)
    }
}