package me.wanttobee.lolcraft.base.util

import me.wanttobee.lolcraft.MinecraftPlugin
import org.bukkit.Bukkit

// This is A object that has just some game loops in it, so that we don't have to have multiple tasks running at the same time that have the exact same interval.

// TODO:
//  I tried doing this as-well for the ability cool-downs. however, i noticed that this will not work since you dont know what the delay is in the current task period
//  Its not that big of a problem, but if you are super lucky, you might lose a full second on your cooldown.
//  So here should be a better solution for or otherwise we should just keep it as it is for now where each ability creats its own task if it is on cooldown

object RepeatingTasks {
    private var taskId : Int = 0
    private var started = false
    // TODO: if one list becomes big, it might be good to split that list in to 2 with an offset between the 2

    // private val tasks02 = mutableMapOf<Int, () -> Unit>() // 0.2 second interval
    private val tasks05 = mutableMapOf<Int, () -> Unit>() // 0.5 second interval
    // private val tasks1 = mutableMapOf<Int, () -> Unit>() // 1 second interval

    fun start() {
        if (started) return
        // Note that the first delay is only there to make sure there is not a single tick where there are multiple task invoking at the same time
        // showcase of this: https://www.desmos.com/calculator/vbp7pb4xzj
        // taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftPlugin.instance, {
        //     for (task in tasks02.values) {
        //         task()
        //     }
        // }, 0, 4)
        Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftPlugin.instance, {
            for (task in tasks05.values) {
                task()
            }
        }, 1, 10)
        // Bukkit.getScheduler().scheduleSyncRepeatingTask(MinecraftPlugin.instance, {
        //     for (task in tasks1.values) {
        //         task()
        //     }
        // }, 2, 20)

        started = true
    }

    fun stop() {
        if (!started) return
        Bukkit.getScheduler().cancelTask(taskId)
        started = false
    }

    // fun addTask02(task: () -> Unit) : Int {
    //     val id = tasks02.size
    //     tasks02[id] = task
    //     return id
    // }
    // fun removeTask02(id: Int) {
    //     tasks02.remove(id)
    // }

    fun addTask05(task: () -> Unit) : Int {
        val id = tasks05.size
        tasks05[id] = task
        return id
    }
    fun removeTask05(id: Int) {
        tasks05.remove(id)
    }

    // fun addTask1(task: () -> Unit) : Int {
    //     val id = tasks1.size
    //     tasks1[id] = task
    //     return id
    // }
    // fun removeTask1(id: Int) {
    //     tasks1.remove(id)
    // }
}