package me.wanttobee.lolcraft.base.abilities.states

import me.wanttobee.lolcraft.MinecraftPlugin
import me.wanttobee.lolcraft.base.abilities.AbilityItem
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.champions.ChampionState
import org.bukkit.Bukkit

open class PassiveAbilityState<T>(championState: T, ability : IAbility<T>)  : BaseAbilityState<T>(championState, ability) where T : ChampionState {

    var maxCoolDown : Double = 1.0
    protected var currentCoolDown : Double = 0.0
    protected var coolDownTaskId: Int? = null

    open fun invokeInitial(){
        if(!initialized)
            throw IllegalStateException("Cannot invoke passive on an ability that has not been initialized yet. " +
                    "[ability: ${ability.title} - player: ${owner.player.name}]")

        if(!canCast())
            return

        ability.invokeInitial(this)
        startCoolDown()
    }

    protected fun canCast() : Boolean {
        val rightLevel = ability.maxLevel == 0 || level > 0
        return currentCoolDown <= 0.0 && !isDisrupted && rightLevel
    }

    protected fun startCoolDown(cooldownTime: Double = maxCoolDown){
        currentCoolDown = cooldownTime + 1 // We do + 1 since we do to subtract 1 in the task immediately

        // restarting when for some reason the coolDown already exists
        // this should actually never happen, but you never know
        coolDownTaskId?.let { Bukkit.getScheduler().cancelTask(it) }

        var useSmallTickRate = false

        // Schedule a repeating task to count down the coolDown
        coolDownTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftPlugin.instance, {
            if (currentCoolDown > 0) {
                currentCoolDown -= 1
                if(!useSmallTickRate && currentCoolDown <= AbilityItem.CooldownDecimalThreshold){
                    useSmallTickRate = true
                    coolDownTaskId?.let { Bukkit.getScheduler().cancelTask(it) }

                    coolDownTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftPlugin.instance, {
                        if (currentCoolDown > 0) {
                            currentCoolDown -= 0.2
                            item.setOnCoolDown(currentCoolDown)
                        } else {
                            coolDownTaskId?.let { Bukkit.getScheduler().cancelTask(it) }
                            coolDownTaskId = null
                        }
                    }, 4L, 4L)
                }

                item.setOnCoolDown(currentCoolDown)
            }
            else {
                coolDownTaskId?.let { Bukkit.getScheduler().cancelTask(it) }
                coolDownTaskId = null
            }
        }, 0L, 20L) // 20 ticks = 1 second
    }

    protected fun cancelCoolDown() {
        // Immediately stop the coolDown and reset the values
        coolDownTaskId?.let { Bukkit.getScheduler().cancelTask(it) }
        coolDownTaskId = null
        currentCoolDown = 0.0
        item.setOnCoolDown(currentCoolDown)
    }
}
