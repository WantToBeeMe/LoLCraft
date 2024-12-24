package me.wanttobee.lolcraft.base.abilities

import me.wanttobee.everythingitems.UniqueItemStack
import me.wanttobee.everythingitems.interactiveitems.InteractiveHotBarItem
import org.bukkit.Material
import org.bukkit.entity.Player

abstract class Ability(private val defaultSlot : Slot){
    enum class Slot(val index: Int) {
        PASSIVE(8),
        Q_ABILITY(0),
        W_ABILITY(1),
        E_ABILITY(2),
        R_ABILITY(3),
        D_SPELL(4),
        F_SPELL(5),
        RECALL(6),
        WARD(7)
    }

    protected val item = UniqueItemStack(Material.STICK, "Unknown Ability", null, 1)

    abstract fun invoke(player: Player)

    fun setToPlayer(player: Player, slot : Slot = defaultSlot){
        val hotBarItem = InteractiveHotBarItem(item)
        hotBarItem.setSlot(slot.index).giveToPlayer(player)
        hotBarItem.setLeftClickEvent { p, _ -> invoke(p) }
    }
}