package me.wanttobee.lolcraft.base.players

import me.wanttobee.everythingitems.interactiveitems.InteractiveHotBarItem
import me.wanttobee.lolcraft.base.util.CCGroupState
import me.wanttobee.lolcraft.base.util.CCState

// TODO: Subject to change
// -=- Player State -=-
//  The player context part that is responsible for all the settings
//  are all the info that happens inGame
//         -=-
class PlayerState(override val context: PlayerContext) : IPlayerContextPart {
    // I know this is not really CC, but I don't want to create a whole separate class for just this one thing
    // and also its works well, so I can just put it in the CCGroups
    val isDead = CCState("is Dead")

    val airborne = CCState("Airborne")
    val blinded = CCState("Blind")
    val crippled = CCState("Cripple")
    val disarmed = CCState("Disarm")
    val disrupted = CCState("Disrupt")
    val drowsy = CCState("Drowsy")
    val sleep = CCState("Sleep")
    val forceAction = CCState("Forced Action") // Charm - Flee - Taunt - Berserk  (these might need to be split, but not sure yet)
    val grounded = CCState("Ground")
    val kinematics = CCState("Kinematics")
    val knockdown = CCState("Knockdown")
    val nearsighted = CCState("Nearsighted")
    val rooted = CCState("Root")
    val silenced = CCState("Silence")
    val slow = CCState("Slow")
    val stasis = CCState("Stasis")
    val stunned = CCState("Stun")
    val suppression = CCState("Suppression")

    val totalCC = CCGroupState("Total CC",
        airborne, forceAction, sleep, stasis, stunned, suppression
    )
    val disrupts = CCGroupState("Disrupts",
        airborne, forceAction, silenced, sleep, stasis, stunned, suppression, isDead
    )
    val immobilizing = CCGroupState("Immobilizing",
        airborne, forceAction, rooted, sleep, stasis, stunned, suppression
    )
    val disarms = CCGroupState("Disarms",
        airborne, forceAction, disarmed, sleep, stasis, stunned, suppression
    )
    val impairsMovement = CCGroupState("Impairs movement",
        airborne, drowsy, forceAction, grounded, rooted, sleep, slow, stasis, stunned, suppression
    )
    val impairsActions = CCGroupState("Impairs actions",
        airborne, crippled, drowsy, disarmed ,forceAction, grounded, silenced, sleep, slow, rooted ,stasis, stunned, suppression
    )

    val abilities: Array<InteractiveHotBarItem?> = arrayOfNulls(9)
}