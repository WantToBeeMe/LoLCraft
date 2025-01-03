package me.wanttobee.lolcraft.test.champion

import me.wanttobee.lolcraft.base.champions.ChampionStats

object TestChampionStats : ChampionStats() {
    override val baseHealth = 630
    override val baseHealthPerLevel = 107
    override val baseMana = 350
    override val baseManaPerLevel = 40

    override val baseHealthRegen = 3.75
    override val baseHealthRegenPerLevel = 0.65
    override val baseManaRegen = 8.2
    override val baseManaRegenPerLevel = 0.7

    override val baseArmor = 26.0
    override val baseArmorPerLevel = 4.7
    override val baseAttackDamage = 52
    override val baseAttackDamagePerLevel = 3
    override val baseMagicResist = 30.0
    override val baseMagicResistPerLevel = 1.3

    override val baseCritDamage = 1.75
    override val baseMoveSpeed = 330
    override val baseAttackRange = 500

    override val baseAttackSpeed = 0.638
    override val attackWindup = 1.333
}