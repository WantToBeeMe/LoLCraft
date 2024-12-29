package me.wanttobee.lolcraft.base.abilities.states

import me.wanttobee.lolcraft.MinecraftPlugin
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.champions.ChampionState
import org.bukkit.Bukkit

open class PassiveAbilityState<T>(championState: T, ability : IAbility<T>)  : BaseAbilityState<T>(championState, ability) where T : ChampionState {

    var maxCoolDown : Int = 1
        set(value) { field = value.coerceIn(1, null) }
    protected var currentCoolDown : Int = 0
    protected var coolDownTaskId: Int? = null

    open fun invokeInitial(){
        if(!initialized)
            throw IllegalStateException("Cannot invoke passive on an ability that has not been initialized yet. " +
                    "[ability: ${ability.title} - player: ${owner.player.name}]")

        if(currentCoolDown != 0 || isDisrupted)
            return

        ability.invokeInitial(this)
        startCoolDown()
    }

    fun startCoolDown() {
        owner.player.sendMessage("Starting cooldown with value: $maxCoolDown") // Debug
        currentCoolDown = maxCoolDown
        item.setOnCoolDown(currentCoolDown)

        // restarting when for some reason the coolDown already exists
        // this should actually never happen, but you never know
        coolDownTaskId?.let { Bukkit.getScheduler().cancelTask(it) }

        // Schedule a repeating task to count down the coolDown
        coolDownTaskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftPlugin.instance, {
            if (currentCoolDown > 0) {
                currentCoolDown -= 1
                item.setOnCoolDown(currentCoolDown)
            }
            else {
                coolDownTaskId?.let { Bukkit.getScheduler().cancelTask(it) }
                coolDownTaskId = null
            }
        }, 20L, 20L) // 20 ticks = 1 second
    }

    protected fun cancelCoolDown() {
        // Immediately stop the coolDown and reset the values
        coolDownTaskId?.let { Bukkit.getScheduler().cancelTask(it) }
        coolDownTaskId = null
        currentCoolDown = 0
        item.setOnCoolDown(0)
    }
}
