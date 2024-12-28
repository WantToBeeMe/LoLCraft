package me.wanttobee.lolcraft.base.champions

import me.wanttobee.lolcraft.base.abilities.states.AbilityState
import me.wanttobee.lolcraft.base.players.PlayerContext
import me.wanttobee.lolcraft.base.util.AbilitySlot

// -= Champion State =-
// Only responsible for managing its own state of the champion.
// This includes things like:
//  - level
//  - player
//  - swap ability

// It should be inherited so:
// - you can also keep track of stacks specific to the champion (Pantheon, gwe or, Jhin basic attacks)
// - multiple ability states, like hwei
//        -=-
class ChampionState(val owner: PlayerContext) {

    // Places the item at the slot entered, or otherwise at the default slot of the state
    fun setAbility(abilityState: AbilityState, slot : AbilitySlot? = null){
        // It should not matter if the hotBar item is recreated whenever you swap abilities (Hwei & Viego)
        // Since all the state in the ability state class. and so we can send out as many hotBar items out as we want, it does not break it
        val slotIndex = slot?.index ?: abilityState.ability.defaultSlot.index
        if(owner.state.abilities[slotIndex] != null)
            throw Error("Tried to give this an ability the player, even though it already has an player added." +
                    " Note this method should only be used for setting the initial abilities")

        setAbilityState(abilityState, slotIndex)
    }

    // Places the item at the slot entered, or otherwise at the default slot of the entered state
    fun swapAbility(abilityState: AbilityState, slot : AbilitySlot? = null){
        // TODO: Instead of always replacing the InteractiveHotBarItem, instead check if the ability state for that hotBar item is teh same, and if so, not replace it
        //   It is an extra optimization safe, however, in theory it should never swap if its already the right state
        val slotIndex = slot?.index ?: abilityState.ability.defaultSlot.index
        owner.state.abilities[slotIndex]?.clear()
        setAbilityState(abilityState, slotIndex)
    }

    private fun setAbilityState(abilityState: AbilityState, slot: Int){
        val currentHotBarItem = abilityState.createNewHotBarItem().setSlot(slot)
        owner.state.abilities[slot] = currentHotBarItem
        currentHotBarItem.giveToPlayer(owner.player)
    }
}