package me.wanttobee.lolcraft.champions.test

import me.wanttobee.lolcraft.base.abilities.AbilityState
import me.wanttobee.lolcraft.base.abilities.AbilityItem
import me.wanttobee.lolcraft.base.champions.ChampionState
import me.wanttobee.lolcraft.base.util.AbilitySlot
import org.bukkit.entity.Player

class TestR(championState: ChampionState) : AbilityState(championState, TestAbility,AbilitySlot.R_ABILITY, AbilityItem("anivia_r", "Unknown Ability", null)) {
    init {
        item.setMaxLevel(3)
        item.setCurrentLevel(2)
    }
}