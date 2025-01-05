package me.wanttobee.lolcraft.base.players.stats

import me.wanttobee.lolcraft.base.players.PlayerContext

// We might need to introduce a T where T:Number
abstract class IStat(val name:String, val context: PlayerContext, protected val getBaseValue: ((Int) -> Double)? = null, baseValue: Double? = null){

    init{
        if(getBaseValue == null && baseValue == null) throw NullPointerException("Either getBaseValue or baseValue must be provided")
        if(getBaseValue != null && baseValue != null) throw Exception("Either getBaseValue or baseValue must be provided, not both at the same time")
    }

    protected var _baseValue : Double = baseValue ?: getBaseValue?.let { it(context.playerState.level) } ?: 0.0
    open val baseValue: Double
        get() = _baseValue

    protected var _bonusValue : Double = 0.0
    open val bonusValue : Double
        get() = _bonusValue

    protected var _totalValue : Double = _baseValue + _bonusValue
    open val totalValue : Double
        get() = _totalValue

    private var modifierId : Int = 0
    protected var additiveCache : Double = 0.0
    private val additiveModifiers : MutableMap<Int, Double> = mutableMapOf()
    protected var multiplicativeCache : Double = 1.0
    private val multiplicativeModifiers : MutableMap<Int, Double> = mutableMapOf()
    // TODO: It might just be that this customModifiers list should have an order/priority or something like that
    protected var customCache : (Double, Double) -> Double = {_,n->n} // (Base, CurrentBonus) -> NewBonus
    private val customModifiers : MutableMap<Int, Pair<(Double, Double) -> Double, Int>> = mutableMapOf()  // (Base, CurrentBonus) -> NewBonus

    fun addAdditiveModifier(value: Double) : Int{
        modifierId++
        additiveModifiers[modifierId] = value
        onAdditiveModifierChange()
        return modifierId
    }
    fun removeAdditiveModifier(id: Int){
        additiveModifiers.remove(id)
        onAdditiveModifierChange()
    }

    fun addMultiplicativeModifier(value: Double) : Int{
        modifierId++
        multiplicativeModifiers[modifierId] = value
        onMultiplicativeModifierChange()
        return modifierId
    }
    fun removeMultiplicativeModifier(id: Int){
        multiplicativeModifiers.remove(id)
        onMultiplicativeModifierChange()
    }

    fun addCustomModifier(value: (Double, Double) -> Double, priority: Int = 10) : Int{  // the lower the prio, the earlier it gets called
        modifierId++
        customModifiers[modifierId] = Pair(value, priority)
        onCustomModifierChange()
        return modifierId
    }
    fun removeCustomModifier(id: Int){
        customModifiers.remove(id)
        onCustomModifierChange()
    }

    abstract fun onLevelUp()
    protected abstract fun onAdditiveModifierChange()
    protected abstract fun onMultiplicativeModifierChange()
    protected abstract fun onCustomModifierChange()

    protected fun recalculateBaseValue(){
        _baseValue = getBaseValue?.let { it(context.playerState.level) }?.toDouble() ?: _baseValue
    }

    protected fun recalculateAdditiveModifiers(){
        additiveCache = additiveModifiers.values.sum()
    }

    protected fun recalculateMultiplicativeModifiers(){
        multiplicativeCache = multiplicativeModifiers.values.fold(1.0) { acc, value -> acc * value }
    }
    protected fun recalculateCustomModifiers(){
        val values = customModifiers.values.sortedBy { it.second }
        customCache = values.fold({_,n-> n}) { acc, value -> {base, newBonus -> value.first(base, acc(base, newBonus)) } }
    }

    protected fun recalculateBonus(){
        _bonusValue = customCache(baseValue, multiplicativeCache * additiveCache)
    }
    protected fun recalculateTotal(){
        _totalValue = baseValue + bonusValue
    }

}