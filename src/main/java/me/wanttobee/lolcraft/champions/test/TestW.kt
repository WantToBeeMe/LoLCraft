package me.wanttobee.lolcraft.champions.test

import me.wanttobee.lolcraft.base.abilities.Ability
import org.bukkit.entity.Player

class TestW : Ability(Slot.W_ABILITY) {
    init{
        item.updateStringCMD("recast",2).pushUpdates()
    }
    override fun invoke(player: Player, chargeCount: Int) {
        player.sendMessage("W  ${chargeCount}")
    }
}