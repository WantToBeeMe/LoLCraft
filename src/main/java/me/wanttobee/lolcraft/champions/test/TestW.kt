package me.wanttobee.lolcraft.champions.test

import me.wanttobee.lolcraft.base.abilities.AbilityState
import me.wanttobee.lolcraft.base.abilities.AbilityItem
import me.wanttobee.lolcraft.base.champions.ChampionState
import me.wanttobee.lolcraft.base.util.AbilitySlot
import org.bukkit.entity.Player

class TestW(championState: ChampionState) : AbilityState(championState, TestAbility,AbilitySlot.W_ABILITY, AbilityItem("anivia_w", "Unknown Ability", null)) {
    init{
        item.setMaxLevel(5)
        item.setCurrentLevel(3)
    }
}