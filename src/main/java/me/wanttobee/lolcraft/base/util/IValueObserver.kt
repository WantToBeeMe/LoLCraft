package me.wanttobee.lolcraft.base.util

interface IValueObserver {

    fun valueChanged(value: ObservableValue<*>)
    fun onClear(value: ObservableValue<*>) {}
}