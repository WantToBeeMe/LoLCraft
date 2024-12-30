package me.wanttobee.lolcraft.base.abilities.states

import me.wanttobee.lolcraft.MinecraftPlugin
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.champions.ChampionState
import org.bukkit.Bukkit

open class ChannelAbilityState<T>(championState: T, ability : IAbility<T>) : RecastAbilityState<T>(championState, ability) where T : ChampionState {

    init{
        // To prevent anything from breaking, we make sure its always 1
        maxRecastAmount = 1
    }

    override fun invokeRightClick(){
        if(!initialized)
            throw IllegalStateException("Cannot invoke passive on an/gi ability that has not been initialized yet. " +
                    "[ability: ${ability.title} - player: ${owner.player.name}]")

        if(!canCast())
            return

        if(owner.settings.channelAsRecast){
            super.invokeRightClick()
            return
        }

        if(longPressSafeTask == null)
            invokeInitial()

        longPressSafeTask?.let { Bukkit.getScheduler().cancelTask(it) }
        longPressSafeTask = Bukkit.getScheduler().scheduleSyncDelayedTask(MinecraftPlugin.instance, {
            invokeRecast()
            longPressSafeTask = null
        }, owner.settings.channelBuffer)//(note that delay is in ticks)
        // 5L For normal mode / 11L for Quick Cast mode (11 for the first!!, the rest can probably be lower)
        // Note that you can do +1 for both to ensure server does not lag and stuff. also note that this may also change depending on the server specs
    }
}