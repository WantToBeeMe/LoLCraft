package me.wanttobee.lolcraft.base.players.stats

import me.wanttobee.lolcraft.base.players.PlayerContext

class DynamicStat private constructor(name: String, context: PlayerContext, getBaseValue: ((Int) -> Double)?, baseValue: Double?) : IStat(name, context, getBaseValue, baseValue) {

    constructor(name: String, context: PlayerContext, baseValue: Double) : this(name, context, null, baseValue)
    constructor(name: String, context: PlayerContext, getBaseValue: ((Int) -> Double)) : this(name, context, getBaseValue, null)


    private val onTotalChange = mutableListOf<(DynamicStat) -> Unit>()
    fun subscribe(callback: (DynamicStat) -> Unit){
        onTotalChange.add(callback)
    }
    fun unsubscribe(callback: (DynamicStat) -> Unit) : Boolean{
        return onTotalChange.remove(callback)
    }

    override fun onLevelUp() {
        if(getBaseValue == null) return
        // If base value does not change on level, then nothing happens anyway, and we don't have to set them dirty
        this.recalculateBaseValue()
        newTotal()
    }

    override fun onAdditiveModifierChange() {
        this.recalculateAdditiveModifiers()
        newTotal()
    }

    override fun onMultiplicativeModifierChange() {
        this.recalculateMultiplicativeModifiers()
        newTotal()
    }

    override fun onCustomModifierChange() {
        this.recalculateCustomModifiers()
        newTotal()
    }

    private fun newTotal(){
        this.recalculateBonus()
        this.recalculateTotal()
        onTotalChange.forEach { it(this) }
    }
}