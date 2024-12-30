package me.wanttobee.lolcraft.base.abilities

import me.wanttobee.everythingitems.UniqueItemStack
import org.bukkit.Material

// -= Ability Item =-
// Only responsible for the visual bit of the ability, aka the icon/item
//       -=-
class AbilityItem(private var iconName: String ,title: String, lore: List<String>? ) : UniqueItemStack(Material.STICK, title, lore, 1) {
    companion object{
        const val CooldownDecimalThreshold = 3.0 // must  be lower than 10
    }

    private enum class StickCMDIndex(val index: Int) {
        BASE(0),
        RESTRICTIONS(1), // disabled - no_mana - silenced - on_cooldown
        RECAST(2),
        LEVELS(3),
        UPGRADE(4),

        COOLDOWN_NUMBER_1TH(5), // This is to display other numbers above 100
        COOLDOWN_NUMBER_2TH(6), // just 0, that way we can display the middle 0 of 100/200/300/...
        COOLDOWN_NUMBER_3TH(7), // just 1 & 0, that way we can actually show the last digit of 100/101/200/201/...
        MANA_NUMBER_1TH(8),
        MANA_NUMBER_2TH(9),
        MANA_NUMBER_3TH(10)
    }
    // All attributes for the CMD
    private var stateRecast = false
    private var stateSilenced = false
    private var stateNoMana = false
    private var stateDisabled = false
    private var notUpgraded = false
    private var stateOnCoolDown : Double = 0.0
    private var currentUpgradeAvailable = false
    private var currentState = ""
    private var currentLevel = 0
    private var maxLevel = 0


    init{
        updateStringCMD(iconName, StickCMDIndex.BASE.index)
    }

    fun getIconName() : String{
        return itemMeta!!.customModelDataComponent.strings[StickCMDIndex.BASE.index]
    }

    fun setIconName(newName : String){
        if(newName == iconName) return
        iconName = newName
        updateStringCMD(iconName, StickCMDIndex.BASE.index)
        pushUpdates()
    }

    fun setRecast(value: Boolean){
        if(stateRecast == value) return
        stateRecast = value
        updateStringCMD(if(stateRecast) "recast" else "", StickCMDIndex.RECAST.index)
        pushUpdates()
    }

    fun setDisrupted(value: Boolean) {
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
    fun setOnCoolDown(value: Double) {
        stateOnCoolDown = value
        setCooldownCount(stateOnCoolDown)
        val pushed = resetCMDState()
        if(!pushed) pushUpdates()
    }

    private fun resetCMDState() : Boolean{
        var newState = ""
        if(notUpgraded) newState = "disabled"
        else if(stateNoMana) newState = "no_mana"
        else if(stateSilenced) newState = "silenced"
        else if(stateOnCoolDown > 0.0) { newState = "on_cooldown" }
        else if(stateDisabled) newState = "disabled"

        if(currentState == newState) return false
        currentState = newState
        updateStringCMD(currentState, StickCMDIndex.RESTRICTIONS.index)
        pushUpdates()
        return true
    }

    fun setUpgradeAvailable(value: Boolean){
        if(currentUpgradeAvailable == value) return
        currentUpgradeAvailable = value
        updateStringCMD(if(currentUpgradeAvailable) "upgrade" else "",  StickCMDIndex.UPGRADE.index)
        pushUpdates()
    }

    fun setMaxLevel(level: Int){
        if(level == maxLevel) return
        if(level != 0 && level != 3 && level != 5)
            throw Error("`setMaxLevel can only receive a value of '0', '3' or '5'")
        maxLevel = level
        if(maxLevel != 0)
            notUpgraded = true
        resetCMDLevel()
    }
    fun setCurrentLevel(level: Int){
        val clamped = level.coerceIn(0..maxLevel)
        if(clamped == currentLevel) return
        currentLevel = clamped
        if(currentLevel > 0)
            notUpgraded = false
        resetCMDLevel()
    }
    private fun resetCMDLevel(){
        if(maxLevel == 0){
            updateStringCMD("", StickCMDIndex.LEVELS.index)
            pushUpdates()
            return
        }

        updateStringCMD("levels${maxLevel}_$currentLevel",StickCMDIndex.LEVELS.index)
        if(resetCMDState())
            pushUpdates()
    }

    private fun setCooldownCount(count: Double){
        val displayCMD = arrayOf("","","") // (1th, 2th, 3th)
        val cooldownInt = count.toInt()

        if(count <= 0.0){
            displayCMD[0] = ""
            displayCMD[1] = ""
            displayCMD[2] = ""
        }
        else if(count < CooldownDecimalThreshold){
            displayCMD[0] = "number_${cooldownInt}xx"
            displayCMD[1] = "number_xsx"
            val decimalNumber = (count - cooldownInt) * 10
            displayCMD[2] = "number_xx${ decimalNumber.toInt() % 10}"
        }
        else if(count < 100 && count >= 10){
            displayCMD[0] = "number_${(cooldownInt / 10) % 10}y"
            displayCMD[1] = ""
            displayCMD[2] = "number_y${cooldownInt % 10}"
        }
        else if(count < 10){
            displayCMD[0] = ""
            displayCMD[1] = "number_x${cooldownInt % 10}x"
            displayCMD[2] = ""
        }
        else{ // 100+
            displayCMD[0] = "number_${(cooldownInt / 100) % 10}xx"
            displayCMD[1] = "number_x${(cooldownInt / 10) % 10}x"
            displayCMD[2] = "number_xx${cooldownInt % 10}"
        }

        updateStringCMD(displayCMD[0], StickCMDIndex.COOLDOWN_NUMBER_1TH.index)
        updateStringCMD(displayCMD[1], StickCMDIndex.COOLDOWN_NUMBER_2TH.index)
        updateStringCMD(displayCMD[2], StickCMDIndex.COOLDOWN_NUMBER_3TH.index)
    }
}