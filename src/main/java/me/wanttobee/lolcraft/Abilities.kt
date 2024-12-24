package me.wanttobee.lolcraft

import me.wanttobee.everythingitems.UniqueItemStack
import me.wanttobee.everythingitems.interactiveitems.InteractiveHotBarItem
import me.wanttobee.lolcraft.base.abilities.Ability
import me.wanttobee.lolcraft.champions.test.*
import org.bukkit.Material
import org.bukkit.entity.Player

object Abilities {
    fun setAbilities(invoker: Player){
        TestE().setToPlayer(invoker)
        TestQ().setToPlayer(invoker)
        TestR().setToPlayer(invoker)
        TestW().setToPlayer(invoker)
    }
}