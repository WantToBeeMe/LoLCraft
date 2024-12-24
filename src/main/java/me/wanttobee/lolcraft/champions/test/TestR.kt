package me.wanttobee.lolcraft.champions.test

import me.wanttobee.lolcraft.base.abilities.Ability
import org.bukkit.entity.Player

class TestR : Ability(Slot.R_ABILITY) {
    override fun invoke(player: Player) {
        player.sendMessage("R")
    }
}