package me.wanttobee.lolcraft.base.util

enum class DamageSource(isDamage: Boolean) {
    AD(true),
    AP(true),
    TRUE(true),
    REGENERATION(false),
    HEAL(false),
    SHIELD(false),
}