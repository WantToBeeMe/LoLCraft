package me.wanttobee.lolcraft.champions.test

import me.wanttobee.lolcraft.base.abilities.Ability
import org.bukkit.entity.Player

class TestQ : Ability(Slot.Q_ABILITY) {
    init{
        item.updateStringCMD(mutableListOf("test", "disabled", "recast"))
            .updateStringCMD("on_cooldown", 1)
            .pushUpdates()
    }

    override fun invoke(player: Player, chargeCount: Int) {
        player.sendMessage("Q  ${chargeCount}")
    }
}