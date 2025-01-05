package me.wanttobee.lolcraft.base.players.stats

import me.wanttobee.lolcraft.base.util.DamageSource
import me.wanttobee.lolcraft.base.util.RepeatingTasks
import kotlin.math.max
import kotlin.math.min

class ManaStat(protected val maxValue: DynamicStat, val regen: DynamicStat) {
    var current = maxValue.totalValue * 0.7
        private set

    private val taskId = RepeatingTasks.addTask05(::onRegen)

    private fun onRegen() {
        // total value is set per 5 seconds. however, this is called every 0.5 seconds
        change(regen.totalValue * 0.5 / 5.0)
    }
    fun reset(){
        RepeatingTasks.removeTask05(taskId)
    }

    private val onChangeSubscribers : MutableList<Pair<(Double) -> Double, Int>> = mutableListOf()
    fun subscribe(callback: (Double) -> Double, priority: Int = 10){ // the lower the prio, the earlier it gets called
        onChangeSubscribers.add(Pair(callback, priority))
        onChangeSubscribers.sortBy { it.second }
    }
    fun unsubscribe(callback: (Double) -> Double): Boolean {
        return onChangeSubscribers.removeIf {it.first == callback}
    }

    fun change(value: Double) {
        var currentValue = value

        for ((callback, _) in onChangeSubscribers) {
            currentValue = callback(currentValue)
            if (currentValue == 0.0)
                return
        }

        current = max(0.0, min(current + currentValue, maxValue.totalValue))
    }

}