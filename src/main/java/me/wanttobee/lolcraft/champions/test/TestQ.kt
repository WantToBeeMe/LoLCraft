package me.wanttobee.lolcraft.champions.test

import me.wanttobee.lolcraft.base.abilities.Ability
import org.bukkit.entity.Player

class TestQ : Ability(Slot.Q_ABILITY) {
    override fun invoke(player: Player, chargeCount: Int) {
        player.sendMessage("Q  ${chargeCount}")
    }
}