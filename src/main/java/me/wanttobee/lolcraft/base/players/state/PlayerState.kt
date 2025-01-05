package me.wanttobee.lolcraft.base.players.state

import me.wanttobee.everythingitems.interactiveitems.InteractiveHotBarItem
import me.wanttobee.lolcraft.base.players.IPlayerContextPart
import me.wanttobee.lolcraft.base.players.PlayerContext
import me.wanttobee.lolcraft.base.players.stats.DynamicStat

// TODO: Subject to change
// -=- Player State -=-
//  The player context part that is responsible for all the settings
//  are all the info that happens inGame
//         -=-
class PlayerState(override val context: PlayerContext) : IPlayerContextPart {

    private val levelUpListeners = mutableListOf<(Int) -> Unit>()
    var level = 1
        private set
    fun increaseLevel() {
        level++
        levelUpListeners.forEach { it(level) }
    }
    fun levelUpSubscribe(callback: (Int) -> Unit){
        levelUpListeners.add(callback)
    }
    fun levelUpUnsubscribe(callback: (Int) -> Unit) : Boolean{
        return levelUpListeners.remove(callback)
    }

    val abilities: Array<InteractiveHotBarItem?> = arrayOfNulls(9)

    fun reset(){
        level = 1
        levelUpListeners.clear()
        for(i in abilities.indices)
            abilities[i] = null
    }

    // -=- CC -=-
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

    // I know this is not really CC, but I don't want to create a whole separate class for just this one thing
    // and also its works well, so I can just put it in the CCGroups
    val isDead = CCState("is Dead")

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
}