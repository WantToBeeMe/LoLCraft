package me.wanttobee.lolcraft.base.players.ui

import me.wanttobee.lolcraft.base.players.IPlayerContextPart
import me.wanttobee.lolcraft.base.players.PlayerContext
import me.wanttobee.lolcraft.base.players.stats.DynamicStat
import me.wanttobee.lolcraft.base.players.stats.PlayerStats
import me.wanttobee.lolcraft.base.util.DamageSource

class PlayerUI(override val context: PlayerContext) : IPlayerContextPart {
    private val player
        get() = context.player

    private var playerStats: PlayerStats? = null
    fun setPlayerStats(playerStats: PlayerStats?) {
        if (this.playerStats != null) {
            this.playerStats!!.mana.unsubscribe(::onCurrentManaChange)
            this.playerStats!!.health.unsubscribe(::onCurrentHealthChange)
            this.playerStats!!.maxMana.unsubscribe(::onMaxManaChange)
            this.playerStats!!.maxHealth.unsubscribe(::onMaxHealthChange)
            this.playerStats = null
        }
        this.playerStats = playerStats
        if (this.playerStats != null) {
            this.playerStats!!.mana.subscribe(::onCurrentManaChange, 999) // we want these to be the last to be called, always
            this.playerStats!!.health.subscribe(::onCurrentHealthChange, 999)
            this.playerStats!!.maxMana.subscribe(::onMaxManaChange)
            this.playerStats!!.maxHealth.subscribe(::onMaxHealthChange)
        }
    }


    private fun onCurrentManaChange(manaChange: Double) : Double {
        updateManaBar()
        return manaChange
    }
    private fun onMaxManaChange(value: DynamicStat) {
        updateManaBar()
    }

    private fun updateManaBar() {
        val maxMana = playerStats?.maxMana?.totalValue ?: 1.0
        val currentMana = playerStats?.mana?.current ?: 0.0

        if (maxMana <= 0) {
            player.exp = 0f
        } else {
            player.exp = (currentMana / maxMana).toFloat()
        }
    }

    private fun onCurrentHealthChange(healthChange: Double, source: DamageSource) : Double {
        return healthChange
    }

    private fun onMaxHealthChange(value: DynamicStat) { }
}