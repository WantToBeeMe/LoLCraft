package me.wanttobee.lolcraft.base.players.stats

import me.wanttobee.lolcraft.base.util.DamageSource
import me.wanttobee.lolcraft.base.util.RepeatingTasks
import kotlin.math.max
import kotlin.math.min

// This stat is mainly used for health and mana regeneration. (or energy, fury, etc. But those are not implemented yet)
class HPStat(protected val maxValue: DynamicStat, val regen: DynamicStat) {
    var current = maxValue.totalValue
        private set

    private val taskId = RepeatingTasks.addTask05(::onRegen)

    private fun onRegen() {
        // total value is set per 5 seconds. however, this is called every 0.5 seconds
        change(regen.totalValue * 0.5 / 5.0, DamageSource.REGENERATION)
    }
    fun reset(){
        RepeatingTasks.removeTask05(taskId)
    }

    private val onChangeSubscribers : MutableList<Pair<(Double, DamageSource) -> Double, Int>> = mutableListOf()
    fun subscribe(callback: (Double, DamageSource) -> Double, priority: Int = 10){ // the lower the prio, the earlier it gets called
        onChangeSubscribers.add(Pair(callback, priority))
        onChangeSubscribers.sortBy { it.second }
    }
    fun unsubscribe(callback: (Double, DamageSource) -> Double): Boolean {
        return onChangeSubscribers.removeIf {it.first == callback}
    }

    fun change(value: Double, source: DamageSource) {
        var currentValue = value

        for ((callback, _) in onChangeSubscribers) {
            currentValue = callback(currentValue, source)
            if (currentValue == 0.0)
                return
        }

        current = max(0.0, min(current + currentValue, maxValue.totalValue))
    }
}