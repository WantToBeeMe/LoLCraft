package me.wanttobee.lolcraft.base.players

import me.wanttobee.everythingitems.interactiveitems.InteractiveHotBarItem
import me.wanttobee.lolcraft.base.abilities.AbilityState
import me.wanttobee.lolcraft.base.util.ObservableValue

// TODO: Subject to change
// -=- Player State -=-
//  The player context part that is responsible for all the settings
//  are all the info that happens inGame
//         -=-
class PlayerState(override val context: PlayerContext) : IPlayerContextPart {

    // TODO: This system should be changed
    //  E.g. if you get stunned it should also silence you
    //   now if that happens you can still turn silence off, but that should not be possible
    //   if you then turn silenced off, it should not send out an signal, instead it should send out the signal when the stun also wears off

    // TODO: Its even wors then i though, with this we cant even seperate 2 different bool values from eachother
    val isSilenced = ObservableValue(false)


    val abilities: Array<InteractiveHotBarItem?> = arrayOfNulls(9)
}