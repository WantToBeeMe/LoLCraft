package me.wanttobee.lolcraft.base.abilities

import me.wanttobee.everythingitems.interactiveitems.InteractiveHotBarItem
import me.wanttobee.lolcraft.MinecraftPlugin
import me.wanttobee.lolcraft.base.champions.ChampionState
import me.wanttobee.lolcraft.base.players.PlayerContext
import me.wanttobee.lolcraft.base.util.AbilitySlot
import me.wanttobee.lolcraft.base.util.IValueObserver
import me.wanttobee.lolcraft.base.util.ObservableValue
import org.bukkit.Bukkit
import org.bukkit.entity.Player

// -= Ability State =-
// Only responsible for managing its own state of the ability, but the generic stuff
// This includes things like:
//  - cool down
//  - level
//  - player
// But not things like:
//  - the actual function implementation
//  - should not have effect if it's in a hand yes or no  (Hwei & Viego)
//        -=-
open class AbilityState(val championState: ChampionState, val ablity : IAbility, val defaultSlot : AbilitySlot, val item : AbilityItem) : IValueObserver {

    val owner: PlayerContext
        get() = championState.owner

    init {
        owner.state.isSilenced.subscribe(this)
    }

    override fun valueChanged(value: ObservableValue<*>) {
        val isSilenced = value as? ObservableValue<Boolean> ?: return
        item.setSilenced(isSilenced.value)
    }

     fun invokePassive(){
         ablity.invokePassive(this)
     }

    fun invoke(count: Int){
        owner.player.sendMessage("temporary invoke $count")
    }

     // TODO: temporary placement
     //    - Instead we should have some overrides, so that we can chose between different states for abilities
     //    - Passive abilities (any ability should be able to contain a passive bit. passives can also have cooldowns)
     //    - Normal cast abilities
     //    - Toggle abilities (extend them from above, just have an on and off state)
     //    - Recast abilities
     //       -  (extend them from above OR just extend from normal cast)
     //       - Since in a sense its just a toggle with a timer
     //       - But it should not only rely on a timer, since Ani-via Q (orb fly stun thing) if its blocked by yasuo, then it should stop early with the recast
     //       - so yea, its should probably extend toggle, with a timer (derived from the same timer as that the ability has), but with an extra dependency
     //    - Channeling abilities (Note that there should be an option in settings to turn them to recast, so maybe even extend them from them)
     private var chargeCounters = 0
     private var chargeTasks : Int? = null

     fun chargeAbility() {
         chargeCounters += 1
         chargeTasks?.let { Bukkit.getScheduler().cancelTask(it) }

         val taskId = Bukkit.getScheduler().scheduleSyncDelayedTask(MinecraftPlugin.instance, {
            invoke(chargeCounters)
            chargeCounters = 0
            chargeTasks = null
         }, 5L) //(note that delay is in ticks)
         // 5L For normal mode / 11L for Quick Cast mode
         // Note that you can do +1 for both to ensure server does not lag and stuff. also note that this may also change depending on the server specs

         chargeTasks = taskId
    }
}
