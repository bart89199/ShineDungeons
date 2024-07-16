package ru.batr.sd.mobs

import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Mob
import org.bukkit.entity.Zombie
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason
import org.bukkit.inventory.EntityEquipment
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.LootTable
import org.bukkit.loot.LootTables
import org.bukkit.potion.PotionEffect

class CustomLivingMob(
    name: String,
    displayName: Component,
    val mob: Mob,
    health: Double,
    damage: Double,
    aggressive: Boolean,
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
    isAware: Boolean = true,
    defTarget: LivingEntity? = null,
) :
    CustomMob(
        name,
        displayName,
        mob.type,
        health,
        damage,
        aggressive,
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
        isAware
    ) {
    init {
        mob.customName(displayName)
        mob.isCustomNameVisible = isNameVisible
        mob.health = health
        mob.target = defTarget
        mob.isAggressive = aggressive
        mob.isAware = isAware
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


    }


}