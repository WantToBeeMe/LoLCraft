package me.wanttobee.lolcraft.base.abilities

import me.wanttobee.lolcraft.base.champions.ChampionState
import org.bukkit.entity.Player

interface IAbility {
    // Note that you can access player context by doing `state.owner`
    // Note that you can access ChampionState by doing `state.championState`

    fun invokeInitial(state: AbilityState)
    
    // Starting from 1, most recast don't use it, yet Jhin's ult does
    fun invokeRecast(state: AbilityState, recastCount: Int)

    fun invokePassive(state: AbilityState)
}