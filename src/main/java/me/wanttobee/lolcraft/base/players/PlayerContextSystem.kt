package me.wanttobee.lolcraft.base.players

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object PlayerContextSystem : Listener {
    private val contextList : MutableMap<Player, PlayerContext> = mutableMapOf()

    fun getContext(p : Player) : PlayerContext {
        if (!contextList.containsKey(p))
            contextList[p] = PlayerContext(p)
        return contextList[p]!!
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
        if(contextList.containsKey(player))
            quitPlayers.add(player)
    }

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent){
        val joinedPlayer = event.player
        for(quitPlayer in quitPlayers){
            if(joinedPlayer.uniqueId != quitPlayer.uniqueId)
                continue

            quitPlayers.remove(quitPlayer)

            val context = contextList[quitPlayer] ?: return
            contextList.remove(quitPlayer)
            contextList[joinedPlayer] = context
            context.swapPlayer(joinedPlayer)
            return
        }
    }
}