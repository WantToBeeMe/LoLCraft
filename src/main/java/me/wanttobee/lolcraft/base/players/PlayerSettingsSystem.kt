package me.wanttobee.lolcraft.base.players

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

// IMPORTANT!!!
// This code (and the rest of the code) is not written with big, 24/7 servers in mind
// E.g. This will go well if you have the server up and 100 people join, 200 people, 300... so on
// However!!, the list never shrinks, and so its memory usages grows with the amount of players that have joined
// I could design something to make it dynamic like that, like having it only be here when the player is online and stuff, but that would involve writing a file and stuff, but it's not worth to me spending that time on it
object PlayerSettingsSystem : Listener {
    private val settings : MutableMap<Player, PlayerSettings> = mutableMapOf()

    fun getSettings(p : Player) : PlayerSettings{
        if (!settings.containsKey(p))
            settings[p] = PlayerSettings(p)
        return settings[p]!!
    }

    // if someone has an internet problem and they accedently leave, we don't want to ruin everything
    // if they rejoin we still want them to be able to play again
    // so whe save the player if they leave
    // and once they rejoin again, we check this list if they are in it (by checking there uniqueID which should never change)
    // and if they are, we check in every team that contains the quited version of this player,
    // and we replace that quited player with this newly rejoined player (even though it is the same one, do you still follow?)
    private val quitPlayers : MutableList<Player> = mutableListOf()
    @EventHandler
    fun onPlayerLeave(event : PlayerQuitEvent){
        val player = event.player
        if(settings.containsKey(player))
            quitPlayers.add(player)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        val player = event.player
        for(quitPlayer in quitPlayers){
            if(player.uniqueId == quitPlayer.uniqueId){
                for(set in settings){
                    if(set.key == quitPlayer && set.key != player){
                        settings[player] = settings[set.key]!!
                        settings.remove(set.key)
                        settings[player]!!.swapPlayer(player)
                    }
                }
                quitPlayers.remove(quitPlayer)
                return
            }
        }
    }

}