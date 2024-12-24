package me.wanttobee.lolcraft

import me.wanttobee.everythingitems.UniqueItemStack
import me.wanttobee.everythingitems.interactiveitems.InteractiveHotBarItem
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

object Abilities {

    object Slots {
        const val PASSIVE = 0
        const val Q_ABILITY = 1
        const val W_ABILITY = 2
        const val E_ABILITY = 3
        const val R_ABILITY = 4
        const val D_SPELL = 5
        const val F_SPELL = 6
        const val RECALL = 7
        const val WARD = 8
    }

    fun setAbilities(invoker: Player){

        val abilityQ = InteractiveHotBarItem(UniqueItemStack(Material.STICK, "Q", null, 1))
        abilityQ.setSlot(Slots.Q_ABILITY).giveToPlayer(invoker)
        abilityQ.setRightClickEvent { p, _ -> p.sendMessage("Q") }

        val abilityW = InteractiveHotBarItem(UniqueItemStack(Material.STICK, "W", null, 1))
        abilityW.setSlot(Slots.W_ABILITY).giveToPlayer(invoker)
        abilityW.setRightClickEvent { p, _ -> p.sendMessage("W") }

        val abilityE = InteractiveHotBarItem(UniqueItemStack(Material.STICK, "E", null, 1))
        abilityE.setSlot(Slots.E_ABILITY).giveToPlayer(invoker)
        abilityE.setRightClickEvent { p, _ -> p.sendMessage("E") }

        val abilityT = InteractiveHotBarItem(UniqueItemStack(Material.STICK, "R", null, 1))
        abilityT.setSlot(Slots.R_ABILITY).giveToPlayer(invoker)
        abilityT.setRightClickEvent { p, _ -> p.sendMessage("R") }
    }
}