package me.wanttobee.lolcraft.base.champions

// I would prefer if this class is only extended by Object classes.
abstract class ChampionStats{

    // Base Statistics
    protected abstract val baseHealth: Int
    protected abstract val baseHealthPerLevel : Int
    protected abstract val baseMana: Int
    protected abstract val baseManaPerLevel : Int
    protected abstract val baseHealthRegen: Double // Per 5 seconds
    protected abstract val baseHealthRegenPerLevel : Double
    protected abstract val baseManaRegen: Double // Per 5 seconds
    protected abstract val baseManaRegenPerLevel : Double

    protected abstract val baseArmor: Double
    protected abstract val baseArmorPerLevel : Double
    protected abstract val baseAttackDamage: Int
    protected abstract val baseAttackDamagePerLevel : Int
    protected abstract val baseMagicResist: Double
    protected abstract val baseMagicResistPerLevel : Double

    protected abstract val baseCritDamage: Double // As a multiplier. So 150% is 1.5
    protected open val baseCritDamagePerLevel: Double = 0.0 // This can be overwritten if needed, but its pretty much always 0 for all champions
    protected abstract val baseMoveSpeed: Int
    protected open val baseMoveSpeedPerLevel: Int = 0 // This can be overwritten if needed, but its pretty much always 0 for all champions

    protected abstract val baseAttackRange: Int
    protected open val baseAttackRangePerLevel : Int = 0 // This can be overwritten if needed, but its pretty much always 0 for all champions

    //Attack Speed Statistics
    protected abstract val baseAttackSpeed: Double // Per second
    abstract val attackWindup : Double // Time taken to wind up an attack, As a fraction of a second (0.5 is half a second)
    // There is also a Bonus AS and a AS ratio, not sure what the formula is for that.
    // as specially the bonus, like, why does a champion already contain bonus?

    fun getHealth(level: Int) : Int {
        return baseHealth + baseHealthPerLevel * level
    }
    fun getMana(level: Int) : Int {
        return baseMana + baseManaPerLevel * level
    }
    fun getHealthRegen(level: Int) : Double {
        return baseHealthRegen + baseHealthRegenPerLevel * level
    }
    fun getManaRegen(level: Int) : Double {
        return baseManaRegen + baseManaRegenPerLevel * level
    }
    fun getArmor(level: Int) : Double {
        return baseArmor + baseArmorPerLevel * level
    }
    fun getAttackDamage(level: Int) : Int {
        return baseAttackDamage + baseAttackDamagePerLevel * level
    }
    fun getMagicResist(level: Int) : Double {
        return baseMagicResist + baseMagicResistPerLevel * level
    }
    fun getMoveSpeed(level: Int) : Int {
        return baseMoveSpeed + baseMoveSpeedPerLevel * level
    }
    fun getCritDamage(level: Int) : Double {
        return baseCritDamage + baseCritDamagePerLevel * level
    }
    fun getAttackRange(level: Int) : Int {
        return baseAttackRange + baseAttackRangePerLevel * level
    }
    fun getAttackSpeed(level: Int) : Double {
        return baseAttackSpeed
    }
}
