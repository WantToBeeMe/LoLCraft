package me.wanttobee.lolcraft.base.abilities

import me.wanttobee.lolcraft.base.abilities.states.BaseAbilityState
import me.wanttobee.lolcraft.base.champions.ChampionState
import me.wanttobee.lolcraft.base.util.AbilitySlot

interface IAbility<T>  where T : ChampionState {
    val defaultSlot : AbilitySlot
    val defaultIconTexture : String
    val title : String
    val maxLevel : Int // 0 or 3 or 5 (0 if it has no levels, otherwise 3 or 5)

    fun initializeState(state: BaseAbilityState<T>)

    // -=- Invoke Ability -=-
    // Note that you can access player context by doing `state.owner`
    // Note that you can access ChampionState by doing `state.championState`

    // This is for any ability that contains a cooldown. This goes for pretty much all abilities in league, but also passives with cooldown
    // E.G. Anivia egg.
    // This also is the first cast for any recast ability
    fun invokeInitial(state: BaseAbilityState<T>) { }

    // This is for all the abilities that should be recast. So this is the off Toggle for toggle abilities (Anivia ult)
    // Or the recast for recast abilities, (Anivia stun, or Jhin's ult)
    // Or it's the Channel succeed call for channels
    fun invokeRecast(state: BaseAbilityState<T>, channelTime: Double, recastCount: Int) {}

    // This is for all teh abilities that should be recast. However, here goes the bit of code that actually cancels the recast
    // This should not only be called when you recast the ability, but also when you die, or when you are interrupted
    fun invokeAbilityCancel(state: BaseAbilityState<T>, channelTime: Double) { }

    // This is for code that should be invoked WITHOUT a cooldown
    // This is for things like Akshan passive, gnar passive, or any other passive derived from an ability
    fun invokePassive(state: BaseAbilityState<T>) { }


}