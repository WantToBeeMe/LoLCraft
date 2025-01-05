package me.wanttobee.lolcraft.base.champions

// I would prefer if this class is only extended by Object classes.
abstract class ChampionStats{

    // Base Statistics
    protected abstract val baseHealth: Int
    protected abstract val baseHealthPerLevel : Int
    protected abstract val baseMana: Int
    protected abstract val baseManaPerLevel : Int
    protected abstract val baseHealthRegen: Float // Per 5 seconds
    protected abstract val baseHealthRegenPerLevel : Float
    protected abstract val baseManaRegen: Float // Per 5 seconds
    protected abstract val baseManaRegenPerLevel : Float

    protected abstract val baseArmor: Float
    protected abstract val baseArmorPerLevel : Float
    protected abstract val baseAttackDamage: Int
    protected abstract val baseAttackDamagePerLevel : Int
    protected abstract val baseMagicResist: Float
    protected abstract val baseMagicResistPerLevel : Float

    abstract val baseCritDamage: Double // As a multiplier. So 150% is 1.5
    abstract val baseMoveSpeed: Int
    abstract val baseAttackRange: Int

    //Attack Speed Statistics
    abstract val baseAttackSpeed: Double // Per second
    abstract val attackWindup : Double // Time taken to wind up an attack, As a fraction of a second (0.5 is half a second)
    // There is also a Bonus AS and a AS ratio, not sure what the formula is for that.
    // as specially the bonus, like, why does a champion already contain bonus?

    fun getHealth(level: Int) : Double {
        val ups = level - 1
        return baseHealth + baseHealthPerLevel *ups* (0.7025 + 0.0175 * ups)
    }
    fun getMana(level: Int) : Double {
        val ups = level - 1
        return baseMana + baseManaPerLevel *ups* (0.7025 + 0.0175 * ups)
    }
    fun getHealthRegen(level: Int) : Double {
        val ups = level - 1
        return baseHealthRegen + baseHealthRegenPerLevel *ups* (0.7025 + 0.0175 * ups)
    }
    fun getManaRegen(level: Int) : Double {
        val ups  = level - 1
        return baseManaRegen + baseManaRegenPerLevel *ups* (0.7025 + 0.0175 * ups)
    }
    fun getArmor(level: Int) : Double {
        val ups = level - 1
        return baseArmor + baseArmorPerLevel *ups* (0.7025 + 0.0175 * ups)
    }
    fun getAttackDamage(level: Int) : Double {
        val ups = level - 1
        return baseAttackDamage + baseAttackDamagePerLevel *ups* (0.7025 + 0.0175 * ups)
    }
    fun getMagicResist(level: Int) : Double {
        val ups = level - 1
        return baseMagicResist + baseMagicResistPerLevel *ups* (0.7025 + 0.0175 * ups)
    }
}
