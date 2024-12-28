package me.wanttobee.lolcraft.base.abilities

import me.wanttobee.everythingitems.UniqueItemStack
import org.bukkit.Material

// -= Ability Item =-
// Only responsible for the visual bit of the ability, aka the icon/item
//       -=-
class AbilityItem(private var iconName: String ,title: String, lore: List<String>? ) : UniqueItemStack(Material.STICK, title, lore, 1) {

    private enum class StickCMDIndex(val index: Int) {
        BASE(0),
        RESTRICTIONS(1), // disabled - no_mana - silenced - on_cooldown
        RECAST(2),
        LEVELS(3),
        UPGRADE(4),

        // "1th" = 1st is the first place of the 3-digit number, ake the 1 in 100
        // (yes I know, "1th" is incorrect and it should be 1st. But no one sees it,
        // and I am not planning to make the code more complicated just so its grammatically correct under the hood)
        ITEM_COUNT_1TH(5), // This is to display other numbers above 100
        ITEM_COUNT_2TH(6), // just 0, that way we can display the middle 0 of 100/200/300/...
        ITEM_COUNT_3TH(7) // just 1 & 0, that way we can actually show the last digit of 100/101/200/201/...
    }
    // All attributes for the CMD
    private var stateRecast = false
    private var stateSilenced = false
    private var stateNoMana = false
    private var stateDisabled = false
    private var stateOnCoolDown : Int = 0
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
    fun setOnCoolDown(value: Int) {
        stateOnCoolDown = value
        setCount(value, value != 0)
        val pushed = resetCMDState()
        if(!pushed) pushUpdates()
    }

    private fun resetCMDState() : Boolean{
        var newState = ""
        if(stateNoMana) newState = "no_mana"
        else if(stateSilenced) newState = "silenced"
        else if(stateOnCoolDown != 0) { newState = "on_cooldown" }
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
            updateStringCMD("", StickCMDIndex.LEVELS.index)
        else
            updateStringCMD("levels${maxLevel}_$currentLevel",StickCMDIndex.LEVELS.index)
        pushUpdates()
    }

    private fun setCount(value: Int, displayOne: Boolean, displayZero: Boolean = false){
        var displayNumber = value
        val displayCMD = arrayOf("","","") // (1th, 2th, 3th)

        if(value <= 0 && displayZero)
            displayCMD[2] = "3th_0"
        else if(value == 1 && displayOne)
            displayCMD[2] = "3th_1"
        else if(value >= 999 && displayOne){
            displayCMD[2] = "3th_9"
            displayNumber = 99
        }
        else if(value >= 100 && displayOne){
            displayNumber = value % 100
            val hundredNumber = (value / 100)
            displayCMD[0] = "1th_$hundredNumber"
            if (displayNumber < 10) // 0 through 9
                displayCMD[1] = "2th_0"
            if (displayNumber <= 1) // 0 or 1
                displayCMD[2] = "3th_$displayNumber"
        }

        updateStringCMD(displayCMD[0], StickCMDIndex.ITEM_COUNT_1TH.index)
        updateStringCMD(displayCMD[1], StickCMDIndex.ITEM_COUNT_2TH.index)
        updateStringCMD(displayCMD[2], StickCMDIndex.ITEM_COUNT_3TH.index)
        updateCount(displayNumber.coerceIn(1,99))
        // the 99 of the coerceIn should actually do nothing, if the value is above 100, then the cases before are incorrect and those should be fixed.
        // the 99 is only there to not actually break the game, since it crashses if this input is 100+
        // the 1 on the other hand should do something indeed, if you set the count to a real 0, then the item just vanishes. so instead
    }
}