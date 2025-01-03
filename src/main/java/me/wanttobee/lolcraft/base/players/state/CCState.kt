package me.wanttobee.lolcraft.base.players.state

// An CCState is just a boolean, but it sends out a signal if it changes
// anny modifier you apply will make the boolean the inverse of what it currently is, if there are no modifiers, then it's the default value
class CCState(val name: String) {
    private val observers : MutableList<(CCState) -> Unit> = mutableListOf()
    private val modifiers : MutableList<Int> = mutableListOf()
    private var modifierRemovalKey = 0

    var value: Boolean = false
        private set(v) {
            if(v == field) return

            field = v
            updateObservers()
        }

    // We intentionally use removal keys instead adding and removing strings. We do this for multiple reasons
    // 1. strings is bad, since it leaves room for undetected typo's
    // 2. if there are 2 people adding the same string, then removing it may result in weird behavior
    // 3. we also don't really care what the source is, so who cares what the cause is of the silence, so we can just reference them by ID
    fun addModifier() : Int{
        modifierRemovalKey++
        modifiers.add(modifierRemovalKey)
        value = modifiers.isNotEmpty()
        return modifierRemovalKey
    }

    fun removeModifier(id: Int) : Boolean {
        val didRemove = modifiers.remove(id)
        value = modifiers.isNotEmpty()
        return didRemove
    }

    fun subscribe(observer: (CCState) -> Unit){
        if(observers.contains(observer)) return
        observers.add(observer)
    }

    fun unSubscribe(observer: (CCState) -> Unit): Boolean{
        return observers.remove(observer)
    }

    fun clear(){
        observers.clear()
    }

    private fun updateObservers(){
        for (observer in observers) {
            observer.invoke(this)
        }
    }
}