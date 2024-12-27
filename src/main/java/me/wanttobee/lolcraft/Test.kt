package me.wanttobee.lolcraft

import me.wanttobee.lolcraft.base.abilities.AbilityState
import me.wanttobee.lolcraft.base.champions.ChampionState
import me.wanttobee.lolcraft.base.players.PlayerContext
import me.wanttobee.lolcraft.base.players.PlayerContextSystem
import me.wanttobee.lolcraft.base.util.AbilitySlot
import me.wanttobee.lolcraft.champions.test.*
import org.bukkit.entity.Player

object Test {

    private var championState : ChampionState? = null
    private var otherAbility : AbilityState? = null


    private fun getChampionState(invoker: Player): ChampionState{
        if(championState == null)
            championState = ChampionState(PlayerContextSystem.getContext(invoker))
        return championState!!
    }

    fun setAbilities(invoker: Player){
        val s = getChampionState(invoker)

        s.setAbility(TestE(s))
        s.setAbility(TestQ(s))
        s.setAbility(TestR(s))
        s.setAbility(TestW(s))
        otherAbility = TestW(s)
    }

    fun otherThing(invoker: Player){
        val s = getChampionState(invoker)

        s.swapAbility(otherAbility!!, AbilitySlot.E_ABILITY)
    }
}