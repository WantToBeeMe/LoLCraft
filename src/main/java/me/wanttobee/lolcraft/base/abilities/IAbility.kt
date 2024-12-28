package me.wanttobee.lolcraft.base.abilities

import me.wanttobee.lolcraft.base.abilities.states.AbilityState
import me.wanttobee.lolcraft.base.util.AbilitySlot

interface IAbility {
    val defaultSlot : AbilitySlot
    val iconTextureName : String
    val title : String


    // -=- Invoke Ability -=-
    // Note that you can access player context by doing `state.owner`
    // Note that you can access ChampionState by doing `state.championState`

    // This is for any ability that contains a cooldown. This goes for pretty much all abilities in league, but also passives with cooldown
    // E.G. Anivia egg.
    // This also is the first cast for any recast ability
    fun invokeInitial(state: AbilityState)

    // This is for all the abilities that should be recast. So this is the off Toggle for toggle abilities (Anivia ult)
    // Or the recast for recast abilities, (Anivia stun, or Jhin's ult)
    // Or it's the Channel succeed call for channels
    fun invokeRecast(state: AbilityState, recastCount: Int)

    // This is for code that should be invoked WITHOUT a cooldown
    // This is for things like Akshan passive, gnar passive, or any other passive derived from an ability
    fun invokePassive(state: AbilityState)
}