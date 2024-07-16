package ru.batr.sd.mobs

import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Mob
import org.bukkit.inventory.ItemStack
import org.bukkit.loot.LootTable
import org.bukkit.potion.PotionEffect

open class CustomMob(
    val name: String,
    val displayName: Component,
    val entityType: EntityType,
    val health: Double,
    val damage: Double,
    val aggressive: Boolean,
    val loot: List<ItemStack>,
    val itemInMainHand: ItemStack = ItemStack(Material.AIR),
    val itemInOffHand: ItemStack = ItemStack(Material.AIR),
    val helmet: ItemStack = ItemStack(Material.AIR),
    val chestplate: ItemStack = ItemStack(Material.AIR),
    val leggings: ItemStack = ItemStack(Material.AIR),
    val boots: ItemStack = ItemStack(Material.AIR),
    val canPickUpItems: Boolean = false,
    val effects: List<PotionEffect> = ArrayList(),
    val hasAI: Boolean = true,
    val isNameVisible: Boolean = true,
    val isAware: Boolean = true,
) : ConfigurationSerializable {
    fun spawnMob(location: Location, defTarget: LivingEntity? = null, randomizeData: Boolean = false): CustomLivingMob {
        val entity = location.world.spawnEntity(location, entityType, randomizeData)
        if (entity is Mob) {
            return CustomLivingMob(
                name,
                displayName,
                entity,
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
                isAware,
                defTarget
            )
        } else {
            throw IllegalArgumentException("This entity is not a mob")
        }
    }

    override fun serialize(): MutableMap<String, Any> {
        val map = HashMap<String, Any>()
        map["name"] = name
        map["displayName"] = displayName
        map["entityType"] = entityType.name
        map["health"] = health
        map["damage"] = damage
        map["aggressive"] = aggressive
        map["loot"] = loot
        map["itemInMainHand"] = itemInMainHand
        map["itemInOffHand"] = itemInOffHand
        map["helmet"] = helmet
        map["chestplate"] = chestplate
        map["leggings"] = leggings
        map["boots"] = boots
        map["canPickUpItems"] = canPickUpItems
        map["effects"] = effects
        map["hasAI"] = hasAI
        map["isNameVisible"] = isNameVisible
        map["isAware"] = isAware

        return map
    }

    companion object {
        @JvmStatic
        fun deserialize(args: Map<String, Any>): CustomMob {
            val loot = ArrayList<ItemStack>()
            val effects = ArrayList<PotionEffect>()
            val loot1 = args["loot"]
            if (loot1 is List<*>) {
                for (i in loot1) {
                    if (i is ItemStack) loot.add(i)
                }
            }
            val effects1 = args["effects"]
            if (effects1 is List<*>) {
                for (i in effects1) {
                    if (i is PotionEffect) effects.add(i)
                }
            }
            return CustomMob(
                args["name"].toString(),
                args["displayName"] as Component,
                EntityType.valueOf(args["entityType"].toString()),
                (args["health"] as Number).toDouble(),
                (args["damage"] as Number).toDouble(),
                args["aggressive"] as Boolean,
                loot,
                args["itemInMainHand"] as ItemStack,
                args["itemInOffHand"] as ItemStack,
                args["helmet"] as ItemStack,
                args["chestplate"] as ItemStack,
                args["leggings"] as ItemStack,
                args["boots"] as ItemStack,
                args["canPickUpItems"] as Boolean,
                effects,
                args["hasAI"] as Boolean,
                args["isNameVisible"] as Boolean,
                args["isAware"] as Boolean,
                )
        }
    }
}