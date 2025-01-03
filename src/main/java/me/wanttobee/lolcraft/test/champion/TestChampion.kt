package me.wanttobee.lolcraft.test.champion

import me.wanttobee.lolcraft.base.champions.ChampionState
import me.wanttobee.lolcraft.base.players.PlayerContext

class TestChampion(owner: PlayerContext): ChampionState(owner) {

    override val stats = TestChampionStats
    var toggleAbilityId : Int? = null
    var counter : Int = 0
}