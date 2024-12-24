package me.wanttobee.lolcraft.base.players

import org.bukkit.entity.Player

// This class is basically the container of all the info about the player
// This will just hold a reference to the Settings, Stats, current champion, things like
// I don't know what name I should give it, other names can be PlayerData, PlayerContext, PlayerProfile
class PlayerContext(private var _player: Player) {
    val player : Player
        get() = _player

    val settings = PlayerSettings(this)

    fun swapPlayer(newPlayer: Player) {
        _player = newPlayer
    }

    fun isGameReady() : Boolean {
        return true
    }
}