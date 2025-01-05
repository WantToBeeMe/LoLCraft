package me.wanttobee.lolcraft.base.players.stats

import me.wanttobee.lolcraft.base.champions.ChampionState
import me.wanttobee.lolcraft.base.players.IPlayerContextPart
import me.wanttobee.lolcraft.base.players.PlayerContext

class PlayerStats(override val context: PlayerContext, val championState: ChampionState) : IPlayerContextPart {

    init {
        context.playerState.levelUpSubscribe(this::onLevelUp)
    }

    fun reset(){
        health.reset()
        mana.reset()
    }

    private fun onLevelUp(newLevel: Int) {
        maxHealth.onLevelUp()
        healthRegen.onLevelUp()
        maxMana.onLevelUp()
        manaRegen.onLevelUp()

        armor.onLevelUp()
        magicResist.onLevelUp()
        attackDamage.onLevelUp()
        // I could subscribe the other stats on level up as-well, but they don't change so why would
    }

    // Dynamic stats send out signals whenever they change. If a change is made they will recalculate the new value immediately
    //     + lets every subscriber know that the value changed, the exact moment that it happened
    //     - It might just be that it recalculates the value, even though no one needs it. Its just wasted computation
    // Better for stats that
    //     * don't change that often
    //     * there are rarely moments that they are not being used

    // Static stats don't send signals, instead they don't change at all unless someone asks them
    // (by reading the value, then it will check if it's out of date)
    //     + only calculates the value when it's actually needed, more optimized than the dynamic stat
    //     - it's not able to send signals to other parts of the code, that the value changed
    // Better for stats that
    //     * change often
    //     * they will have moments that they are not being used

    // Note even though both stats work different, when you read the value, its always accurate

    val maxHealth = DynamicStat("Max Health", context) { level -> championState.stats.getHealth(level) }
    val healthRegen = DynamicStat("Health Regen", context) { level -> championState.stats.getHealthRegen(level) }
    val maxMana = DynamicStat("Max Mana", context) { level -> championState.stats.getMana(level) }
    val manaRegen = DynamicStat("Mana Regen", context) { level -> championState.stats.getManaRegen(level) }

    val health = HPStat(maxHealth, healthRegen)
    val mana = ManaStat(maxMana, manaRegen)

    val armor = StaticStat("Armor", context) { level -> championState.stats.getArmor(level) }
    val magicResist = StaticStat("Magic Resist", context) { level -> championState.stats.getMagicResist(level) }
    val attackDamage = StaticStat("Attack Damage", context) { level -> championState.stats.getAttackDamage(level) }
    val abilityPower = StaticStat("Ability Power", context, 0.0)
    val magicPenetration = StaticStat("Magic Penetration", context, 0.0)
    val armorPenetration = StaticStat("Armor Penetration", context, 0.0)

    val critDamage = StaticStat("Crit Damage", context, championState.stats.baseCritDamage)
    val critChance = StaticStat("Crit Chance", context, 0.0)
    val attackSpeed = DynamicStat("Attack Speed", context, championState.stats.baseAttackSpeed)
    val moveSpeed = DynamicStat("Move Speed", context, championState.stats.baseMoveSpeed.toDouble())
    val attackRange = DynamicStat("Attack Range", context, championState.stats.baseAttackRange.toDouble())

    val abilityHaste = StaticStat("Ability Haste", context, 0.0) // I wouldn't mind if this was a dynamic stat
}