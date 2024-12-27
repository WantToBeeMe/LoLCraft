package me.wanttobee.lolcraft.champions.test

import me.wanttobee.lolcraft.base.abilities.Ability
import org.bukkit.entity.Player

class TestE : Ability(Slot.E_ABILITY) {
    init {
        item.updateStringCMD(listOf("test", "disabled", "recast")).pushUpdates()
    }

    override fun invoke(player: Player, chargeCount: Int) {
        player.sendMessage("E  ${chargeCount}")
    }
}