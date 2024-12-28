package me.wanttobee.lolcraft.base.util

// An CCGroupState is just a boolean, but it sends out a signal if it changes
// Instead of having an initial value, this
class CCGroupState(val name: String,vararg ccStates: CCState) {
    private val observers : MutableList<(CCGroupState) -> Unit> = mutableListOf()
    private val appliedCCStates : MutableList<String> = mutableListOf()
    private val ccStates: List<CCState> = ccStates.toList()

    init{
        for (ccState in ccStates) {
            ccState.subscribe(::onCCStateChange)
        }
    }
    // We can safely set this to false, since all the CCStates should be false by default
    var value: Boolean = false
        private set(v) {
            if(v == field) return

            field = v
            updateObservers()
        }

    fun subscribe(observer: (CCGroupState) -> Unit){
        if(observers.contains(observer)) return
        observers.add(observer)
    }

    fun unSubscribe(observer: (CCGroupState) -> Unit): Boolean{
        return observers.remove(observer)
    }

    private fun onCCStateChange(state: CCState) {
        // This method only gets called if the current state changes, so we can be sure that if its false, that it was true before this call
        if(state.value && !appliedCCStates.contains(state.name))
            appliedCCStates.add(state.name)
        else if(!state.value)
            appliedCCStates.remove(state.name)

        value = appliedCCStates.isNotEmpty()
    }

    fun clear(){
        for (ccState in ccStates)
            ccState.unSubscribe(::onCCStateChange)
        observers.clear()
    }

    private fun updateObservers(){
        for (observer in observers) {
            observer.invoke(this)
        }
    }
}