package me.wanttobee.lolcraft.test.champion

import me.wanttobee.lolcraft.base.champions.ChampionStats

object TestChampionStats : ChampionStats() {
    override val baseHealth = 630
    override val baseHealthPerLevel = 107
    override val baseMana = 350
    override val baseManaPerLevel = 40

    override val baseHealthRegen = 3.75f
    override val baseHealthRegenPerLevel = 0.65f
    override val baseManaRegen = 8.2f
    override val baseManaRegenPerLevel = 0.7f

    override val baseArmor = 26.0f
    override val baseArmorPerLevel = 4.7f
    override val baseAttackDamage = 52
    override val baseAttackDamagePerLevel = 3
    override val baseMagicResist = 30.0f
    override val baseMagicResistPerLevel = 1.3f

    override val baseCritDamage = 1.75
    override val baseMoveSpeed = 330
    override val baseAttackRange = 500

    override val baseAttackSpeed = 0.638
    override val attackWindup = 1.333
}