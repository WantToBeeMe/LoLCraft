package me.wanttobee.lolcraft.base.abilities.states

import me.wanttobee.lolcraft.MinecraftPlugin
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.champions.ChampionState
import org.bukkit.Bukkit

open class RecastAbilityState<T>(championState: T, ability : IAbility<T>) : ToggleAbilityState<T>(championState, ability) where T : ChampionState {

    var maxChannelTime : Double = 1.0 // the seconds that it takes before this ability cancels itself
    var maxRecastAmount : Int = 1 // the amount of times this ability can be recast before it goes on coolDown (given that it's done within the channel time)
    var recastCoolDown : Double = 0.5 // the little coolDown that this ability has in between each recast's

    protected var currentRecastCount = 0
    protected var channelTime = 0.0
    protected var channelTask: Int? = null
    override val longPressSafetyDelay: Long = 3L

    override fun invokeInitial() {
        if(channelTask != null){
            cancelTask()
            owner.player.sendMessage("Ability invoke interrupted, It seems one was already running in the background. Both have been cancelled.")
            return
        }

        isToggled = true

        subtractMana()
        ability.invokeInitial(this)
        startCoolDown(recastCoolDown)

        channelTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftPlugin.instance, {
            channelTime += 0.1

            if(channelTime >= maxChannelTime)
                invokeAbilityCancel()
        }, 2L, 2L)
    }

    override fun invokeRecast(){
        currentRecastCount++
        ability.invokeRecast(this, channelTime, currentRecastCount)
        if(currentRecastCount >= maxRecastAmount){
            invokeAbilityCancel()
            return
        }

        channelTime = 0.0
        startCoolDown(recastCoolDown)
    }

    override fun invokeAbilityCancel(){
        isToggled = false
        currentRecastCount = 0
        cancelTask()
        ability.invokeAbilityCancel(this, channelTime)
        channelTime = 0.0
        startCoolDown()
    }

    private fun cancelTask(){
        channelTask?.let { Bukkit.getScheduler().cancelTask(it) }
        channelTask = null
    }
}