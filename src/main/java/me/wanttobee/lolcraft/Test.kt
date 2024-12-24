package me.wanttobee.lolcraft

import me.wanttobee.lolcraft.champions.test.*
import org.bukkit.entity.Player

object Test {
    fun setAbilities(invoker: Player){
        TestE().setToPlayer(invoker)
        TestQ().setToPlayer(invoker)
        TestR().setToPlayer(invoker)
        TestW().setToPlayer(invoker)
    }
}