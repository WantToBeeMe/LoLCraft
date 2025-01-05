package me.wanttobee.lolcraft.base.players.stats

import me.wanttobee.lolcraft.base.players.PlayerContext

class StaticStat private constructor(name: String, context: PlayerContext, getBaseValue: ((Int) -> Double)?, baseValue: Double?) : IStat(name, context, getBaseValue, baseValue) {

    constructor(name: String, context: PlayerContext, baseValue: Double) : this(name, context, null, baseValue)
    constructor(name: String, context: PlayerContext, getBaseValue: ((Int) -> Double)) : this(name, context, getBaseValue, null)

    // BASE FLAG
    private var baseDirty = false
    // BONUS FLAGS
    private var additiveModifierDirty = false
    private var multiplicativeModifierDirty = false
    private var customModifierDirty = false
    private var bonusDirty = false


    override val baseValue: Double
        get() {
            if(baseDirty){
                recalculateBaseValue()
                baseDirty = false
            }
            return _baseValue
        }

    override val bonusValue: Double
        get() {
            if(additiveModifierDirty){
                recalculateAdditiveModifiers()
                additiveModifierDirty = false
            }
            if(multiplicativeModifierDirty){
                recalculateMultiplicativeModifiers()
                multiplicativeModifierDirty = false
            }
            if(customModifierDirty){
                recalculateCustomModifiers()
                customModifierDirty = false
            }

            if(bonusDirty){
                recalculateBonus()
                bonusDirty = false
            }

            return _bonusValue
        }

    override val totalValue: Double
        get() {
            // This is correct, since if the base value is dirty, then the bonus automatically becomes dirty
            // And so if you recalculate the total, it will call these getters and automatically notice they are out of date as-well
            if(bonusDirty){
                recalculateTotal()
                bonusDirty = false
            }
            return _totalValue
        }

    override fun onLevelUp() {
        if(getBaseValue == null) return
        // If base value does not change on level, then nothing happens anyway, and we don't have to set them dirty
        baseDirty = true
        bonusDirty = true
    }

    override fun onAdditiveModifierChange() {
        additiveModifierDirty = true
        bonusDirty = true
    }

    override fun onMultiplicativeModifierChange() {
        multiplicativeModifierDirty = true
        bonusDirty = true
    }

    override fun onCustomModifierChange() {
        customModifierDirty = true
        bonusDirty = true
    }
}