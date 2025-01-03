package me.wanttobee.lolcraft.test

import me.wanttobee.lolcraft.base.abilities.states.*
import me.wanttobee.lolcraft.base.players.PlayerContextSystem
import me.wanttobee.lolcraft.base.util.AbilitySlot
import me.wanttobee.lolcraft.test.champion.*
import org.bukkit.entity.Player

object Test {
    private var championState : TestChampion? = null
    private var otherAbility : BaseAbilityState<*>? = null

    private var stunId : Int = -1
    private var silenceId: Int = -1

    fun setSilence(invoker: Player, value: Boolean){
        if(value)
            silenceId = PlayerContextSystem.getContext(invoker).playerState.silenced.addModifier()
        else
            PlayerContextSystem.getContext(invoker).playerState.silenced.removeModifier(silenceId)
    }

    fun setStun(invoker: Player, value: Boolean){
        if(value)
            stunId = PlayerContextSystem.getContext(invoker).playerState.stunned.addModifier()
        else
            PlayerContextSystem.getContext(invoker).playerState.stunned.removeModifier(stunId)
    }




    private fun getChampionState(invoker: Player): TestChampion{
        if(championState == null)
            championState = TestChampion(PlayerContextSystem.getContext(invoker))
        return championState!!
    }

    fun setAbilities(invoker: Player){
        val s = getChampionState(invoker)

        s.setAbility(CastAbilityState(s, TestAbilityE).postInitialize())
        s.setAbility(CastAbilityState(s, TestAbilityW).postInitialize())
        s.setAbility(ChannelAbilityState(s, TestRecastAbility).postInitialize())
        s.setAbility(ToggleAbilityState(s, TestToggleAbility).postInitialize())
        s.setAbility(PassiveAbilityState(s, TestPassiveAbility).postInitialize())

        otherAbility = CastAbilityState(s, TestAbilityW).postInitialize()
    }

    fun otherThing(invoker: Player){
        val s = getChampionState(invoker)

        s.swapAbility(otherAbility!!, AbilitySlot.E_ABILITY)
    }
}