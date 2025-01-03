package me.wanttobee.lolcraft.base.players.stats

import me.wanttobee.lolcraft.base.champions.ChampionState
import me.wanttobee.lolcraft.base.players.IPlayerContextPart
import me.wanttobee.lolcraft.base.players.PlayerContext

class PlayerStats(override val context: PlayerContext, val championState: ChampionState) : IPlayerContextPart {
}