package ru.batr.shinedungeons.mobs

import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Mob
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.LootTables
import org.bukkit.potion.PotionEffect
import kotlin.collections.ArrayList

class CustomLivingMob(
    name: String,
    displayName: Component,
    val mob: Mob,
    health: Double,
    damage: Double,
    loot: List<ItemStack>,
    itemInMainHand: ItemStack = ItemStack(Material.AIR),
    itemInOffHand: ItemStack = ItemStack(Material.AIR),
    helmet: ItemStack = ItemStack(Material.AIR),
    chestplate: ItemStack = ItemStack(Material.AIR),
    leggings: ItemStack = ItemStack(Material.AIR),
    boots: ItemStack = ItemStack(Material.AIR),
    canPickUpItems: Boolean = false,
    effects: List<PotionEffect> = ArrayList(),
    hasAI: Boolean = true,
    isNameVisible: Boolean = true,
    isInvulnerable: Boolean = true,
    isSilent: Boolean = false,
    hasGravity: Boolean = true,
) :
    CustomMob(
        name,
        displayName,
        mob.type,
        health,
        damage,
        loot,
        itemInMainHand,
        itemInOffHand,
        helmet,
        chestplate,
        leggings,
        boots,
        canPickUpItems,
        effects,
        hasAI,
        isNameVisible,
        isInvulnerable,
        isSilent,
        hasGravity,
    ) {
    fun configure() {
        MobHandler.loadedCustomMobs[mob.uniqueId] = this
        mob.customName(displayName)
        mob.isCustomNameVisible = isNameVisible
        val maxHealth = mob.getAttribute(Attribute.GENERIC_MAX_HEALTH)
        maxHealth!!.baseValue = health
        mob.health = health
        mob.isInvulnerable = isInvulnerable
        mob.lootTable = LootTables.EMPTY.lootTable
        mob.setAI(hasAI)
        val equipment = mob.equipment
        equipment.setItemInMainHand(itemInMainHand)
        equipment.setItemInOffHand(itemInOffHand)
        equipment.helmet = helmet
        equipment.chestplate = chestplate
        equipment.leggings = leggings
        equipment.boots = boots
        mob.addPotionEffects(effects)
        mob.canPickupItems = canPickUpItems
        mob.isSilent = isSilent
        mob.setGravity(hasGravity)
        mob.removeWhenFarAway = false
    }

    fun toCustomMob() = CustomMob(
        name,
        displayName,
        entityType,
        health,
        damage,
        loot,
        itemInMainHand,
        itemInOffHand,
        helmet,
        chestplate,
        leggings,
        boots,
        canPickUpItems,
        effects,
        hasAI,
        isNameVisible,
        isInvulnerable,
        isSilent,
        hasGravity,
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CustomLivingMob) return false
        if (!super.equals(other)) return false

        if (mob != other.mob) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + mob.hashCode()
        return result
    }

    override fun toString(): String {
        return "CustomLivingMob(mob=$mob)"
    }


}