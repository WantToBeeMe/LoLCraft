package me.wanttobee.lolcraft.base.util

// IMPORTANT: note that these numbers can only be from 0 to 8
//   This is really important, since these numbers are also used as a slot to go back to as selected item
//   And they are also being used as list indexes
enum class AbilitySlot(val index: Int) {
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