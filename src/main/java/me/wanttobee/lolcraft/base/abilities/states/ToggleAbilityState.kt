package me.wanttobee.lolcraft.base.abilities.states

import me.wanttobee.lolcraft.MinecraftPlugin
import me.wanttobee.lolcraft.base.abilities.IAbility
import me.wanttobee.lolcraft.base.champions.ChampionState
import org.bukkit.Bukkit

open class ToggleAbilityState<T>(championState: T, ability : IAbility<T>)  : CastAbilityState<T>(championState, ability) where T : ChampionState {
    protected var isToggled: Boolean = false
        set(value) {
            if (field == value)
                return
            field = value

            item.setRecast(value)
        }

    protected open val longPressSafetyDelay = 5L
    private var longPressSafeTask : Int? = null

    // this method gets called when you right-click the item
    override fun invokeRightClick(){
        if(!initialized)
            throw IllegalStateException("Cannot invoke passive on an/gi ability that has not been initialized yet. " +
                    "[ability: ${ability.title} - player: ${owner.player.name}]")

        if(currentCoolDown > 0 || isDisrupted)
            return

        if(longPressSafeTask == null){
            if (isToggled)
                invokeRecast()
            else
                invokeInitial()
        }

        longPressSafeTask?.let { Bukkit.getScheduler().cancelTask(it) }
        longPressSafeTask = Bukkit.getScheduler().scheduleSyncDelayedTask(MinecraftPlugin.instance, {
            // We can safely set it to null, since this task has now been finished
            longPressSafeTask = null
        }, longPressSafetyDelay) //(note that delay is in ticks), also note that this is not needed for Quick Cast mode
    }

    // This calls the actual implementation of the ability
    override fun invokeInitial() {
        isToggled = true
        ability.invokeInitial(this)
    }

    // This gets called when you are disrupted
    override fun onDisrupted() {
        if (isToggled)
            invokeAbilityCancel()
    }

    // we don't care at all what the channel time is here, since toggle abilities should work infinitely anyway
    // So that's why we set channel time to 0f here, since it's not worth actually counting it
    open fun invokeRecast(){
        ability.invokeRecast(this, 0.0 ,1)
        invokeAbilityCancel()
    }

    // This can be called from anywhere to cancel this ability. This gets also called in onDisrupted for instance
    open fun invokeAbilityCancel(){
        // In toggle abilities, if you are disrupted, you just turn oof the ability, and that's the same as just recasting it
        isToggled = false
        ability.invokeAbilityCancel(this, 0.0)
        startCoolDown()
    }
}