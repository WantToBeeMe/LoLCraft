package me.wanttobee.lolcraft.champions.test

import me.wanttobee.lolcraft.base.abilities.AbilityState
import me.wanttobee.lolcraft.base.abilities.AbilityItem
import me.wanttobee.lolcraft.base.champions.ChampionState
import me.wanttobee.lolcraft.base.util.AbilitySlot
import org.bukkit.entity.Player

class TestQ(championState: ChampionState) : AbilityState(championState, TestAbility,AbilitySlot.Q_ABILITY, AbilityItem("anivia_q", "Unknown Ability", null)) {

    init{
        item.setMaxLevel(5)
        item.setCurrentLevel(2)
    }

}