package me.wanttobee.lolcraft.base.players

import me.wanttobee.lolcraft.base.champions.ChampionState
import me.wanttobee.lolcraft.base.players.settings.PlayerSettings
import me.wanttobee.lolcraft.base.players.state.PlayerState
import me.wanttobee.lolcraft.base.players.stats.PlayerStats
import me.wanttobee.lolcraft.base.players.ui.PlayerUI
import org.bukkit.entity.Player

// This class is basically the container of all the info about the player
// This will just hold a reference to the Settings, Stats, current champion, things like
// I don't know what name I should give it, other names can be PlayerData, PlayerContext, PlayerProfile
class PlayerContext(private var _player: Player) {
    val player : Player
        get() = _player

    // Settings:
    //      any option that the player can change, and that in theory should be persistent between games (currently it's not though)
    val settings = PlayerSettings(this)
    // Player State:
    //      the current state of the player, state things that are for all the players, regardless of what champion they are playing (lvl, stuns, xp)
    val playerState = PlayerState(this)

    // UI:
    //      the current UI of the player, UI is the visual representation of the players stats and values
    //      This however, does not include the abilities, since they also have functionality, those als serve as functionality besides pure visuals
    //      (For the record, those are stored in the state instead)
    val ui: PlayerUI = PlayerUI(this)

    // Champion State:
    //      the current state of the players champion, state things that are specific to the champion (abilities, stacks, marks, ability things)
    var championState : ChampionState? = null
        private set;

    // Stats:
    //      the current stats of the player, stats are values that are calculated from the championState and the items they hold
    private var _stats: PlayerStats? = null
    val stats: PlayerStats
        get() {
            if(_stats == null)
                throw Error("This champion's stats are unknown (stats are not assigned in the context, even though they are trying to be accessed)")
            return _stats!!
        }

    fun setChampionState(newChampionState: ChampionState?) {
        if(championState != null && newChampionState != null)
            throw Error("Tried to give this player a champion, even though it already has a champion added." +
                    " Note this method should only be used for setting the initial champion")
        if(championState == null && newChampionState == null) return

        championState = newChampionState
        if(newChampionState == null) {
            ui.setPlayerStats(null)
            _stats?.reset()
            _stats = null
            return
        }else{
            _stats = PlayerStats(this, newChampionState)
            ui.setPlayerStats(_stats!!)
        }
    }

    fun reset() {
        playerState.reset()
        setChampionState(null)
    }

    fun swapPlayer(newPlayer: Player) {
        _player = newPlayer
    }
}