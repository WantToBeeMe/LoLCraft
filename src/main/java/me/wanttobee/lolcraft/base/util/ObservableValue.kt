package me.wanttobee.lolcraft.base.util

class ObservableValue<T>(defaultValue: T) {
    private val observers : MutableList<IValueObserver> = mutableListOf()

    private var internalValue : T= defaultValue
    var value: T
        get() = internalValue
        set(value) {
            val update = internalValue != value
            internalValue = value
            if(update) updateObservers()
        }

    fun subscribe(observer: IValueObserver){
        if(observers.contains(observer)) return
        observers.add(observer)
    }

    fun unSubscribe(observer: IValueObserver): Boolean{
        return observers.remove(observer)
    }

    fun clear(){
        observers.forEach { it.onClear(this) }
        observers.clear()
    }

    private fun updateObservers(){
        for (observer in observers) {
            observer.valueChanged(this)
        }
    }
}