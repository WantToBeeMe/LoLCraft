package me.wanttobee.lolcraft

import me.wanttobee.lolcraft.base.abilities.AbilityState
import me.wanttobee.lolcraft.base.champions.ChampionState
import me.wanttobee.lolcraft.base.players.PlayerContextSystem
import me.wanttobee.lolcraft.base.util.AbilitySlot
import me.wanttobee.lolcraft.champions.test.*
import org.bukkit.entity.Player

object Test {
    private var championState : ChampionState? = null
    private var otherAbility : AbilityState? = null

    private var stunId : Int = -1
    private var silenceId: Int = -1

    fun setSilence(invoker: Player, value: Boolean){
        if(value)
            silenceId = PlayerContextSystem.getContext(invoker).state.silenced.addModifier()
        else
            PlayerContextSystem.getContext(invoker).state.silenced.removeModifier(silenceId)
    }

    fun setStun(invoker: Player, value: Boolean){
        if(value)
            stunId = PlayerContextSystem.getContext(invoker).state.stunned.addModifier()
        else
            PlayerContextSystem.getContext(invoker).state.stunned.removeModifier(stunId)
    }




    private fun getChampionState(invoker: Player): ChampionState{
        if(championState == null)
            championState = ChampionState(PlayerContextSystem.getContext(invoker))
        return championState!!
    }

    fun setAbilities(invoker: Player){
        val s = getChampionState(invoker)

        val e = TestE(s)
        e.maxCoolDown = 20
        s.setAbility(e)

        val q = TestQ(s)
        q.maxCoolDown = 70
        s.setAbility(q)

        val r = TestR(s)
        r.maxCoolDown = 360
        s.setAbility(r)

        s.setAbility(TestW(s))
        otherAbility = TestW(s)
    }

    fun otherThing(invoker: Player){
        val s = getChampionState(invoker)

        s.swapAbility(otherAbility!!, AbilitySlot.E_ABILITY)
    }
}