package me.wanttobee.lolcraft.base.abilities

import me.wanttobee.everythingitems.UniqueItemStack
import org.bukkit.Material

// -= Ability Item =-
// Only responsible for the visual bit of the ability, aka the icon/item
//       -=-
class AbilityItem(private var iconName: String ,title: String, lore: List<String>? ) : UniqueItemStack(Material.STICK, title, lore, 1) {

    // All attributes for the CMD
    private var stateRecast = false
    private var stateSilenced = false
    private var stateNoMana = false
    private var stateDisabled = false
    private var stateOnCoolDown = false
    private var currentUpgradeAvailable = false
    private var currentState = ""
    private var currentLevel = 0
    private var maxLevel = 0


    init{
        updateStringCMD(iconName, 0)
    }

    fun getIconName() : String{
        return itemMeta!!.customModelDataComponent.strings[0]
    }

    fun setIconName(newName : String){
        if(newName == iconName) return
        iconName = newName
        updateStringCMD(iconName, 0)
        pushUpdates()
    }

    fun setRecast(value: Boolean){
        if(stateRecast == value) return
        stateRecast = value
        updateStringCMD(if(stateRecast) "recast" else "", 2)
        pushUpdates()
    }

    fun setSilenced(value: Boolean) {
        stateSilenced = value
        resetCMDState()
    }
    fun setNoMana(value: Boolean) {
        stateNoMana = value
        resetCMDState()
    }
    fun setDisabled(value: Boolean) {
        stateDisabled = value
        resetCMDState()
    }
    fun setOnCoolDown(value: Boolean) {
        stateOnCoolDown = value
        resetCMDState()
    }
    private fun resetCMDState(){
        var newState = ""
        if(stateNoMana) newState = "no_mana"
        else if(stateSilenced) newState = "silenced"
        else if(stateOnCoolDown) newState = "on_cooldown"
        else if(stateDisabled) newState = "disabled"

        if(currentState == newState) return
        currentState = newState
        updateStringCMD(currentState, 1)
        pushUpdates()
    }

    fun setUpgradeAvailable(value: Boolean){
        if(currentUpgradeAvailable == value) return
        currentUpgradeAvailable = value
        updateStringCMD(if(currentUpgradeAvailable) "upgrade" else "", 4)
        pushUpdates()
    }

    fun setMaxLevel(level: Int){
        if(level == maxLevel) return
        if(level != 0 && level != 3 && level != 5)
            throw Error("`setMaxLevel can only receive a value of '0', '3' or '5'")
        maxLevel = level
        resetCMDLevel()
    }
    fun setCurrentLevel(level: Int){
        val clamped = level.coerceIn(0..maxLevel)
        if(clamped == currentLevel) return
        currentLevel = clamped
        resetCMDLevel()
    }
    private fun resetCMDLevel(){
        if(maxLevel == 0)
            updateStringCMD("",3)
        else
            updateStringCMD("levels${maxLevel}_$currentLevel",3)
        pushUpdates()
    }
}