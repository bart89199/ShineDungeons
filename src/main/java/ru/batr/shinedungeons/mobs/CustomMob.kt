package ru.batr.shinedungeons.mobs

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Mob
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import ru.batr.shinedungeons.TextFormatter
import java.util.UUID

open class CustomMob(
    val name: String,
    val displayName: Component,
    val entityType: EntityType,
    val health: Double,
    val damage: Double,
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
    val isInvulnerable: Boolean = false,
    val isSilent: Boolean = false,
    val hasGravity: Boolean = true
) : ConfigurationSerializable {
    fun spawnMob(location: Location, randomizeData: Boolean = false): CustomLivingMob {
        val mob = toLivingMob(location.world.spawnEntity(location, entityType, randomizeData))
        mob.configure()
        return mob
    }

    fun findByUUID(uuid: UUID): CustomLivingMob? {
        return toLivingMob(Bukkit.getEntity(uuid) ?: return null)
    }

    fun toLivingMob(entity: Entity, defTarget: LivingEntity? = null) =
        if (entity.type == entityType) {
            if (entity is Mob) {
                CustomLivingMob(
                    name,
                    displayName,
                    entity,
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
                    hasGravity
                )
            } else {
                throw IllegalArgumentException("Provided incorrect entity(this type not a mob)")
            }
        } else {
            throw IllegalArgumentException("Provided incorrect entity type")
        }


    override fun serialize(): MutableMap<String, Any> {
        val map = HashMap<String, Any>()
        map["name"] = name
        map["displayName"] = TextFormatter.format(displayName)
        map["entityType"] = entityType.name
        map["health"] = health
        map["damage"] = damage
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
        map["isInvulnerable"] = isInvulnerable
        map["isSilent"] = isSilent
        map["hasGravity"] = hasGravity

        return map
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CustomMob) return false

        if (name != other.name) return false
        if (displayName != other.displayName) return false
        if (entityType != other.entityType) return false
        if (health != other.health) return false
        if (damage != other.damage) return false
        if (loot != other.loot) return false
        if (itemInMainHand != other.itemInMainHand) return false
        if (itemInOffHand != other.itemInOffHand) return false
        if (helmet != other.helmet) return false
        if (chestplate != other.chestplate) return false
        if (leggings != other.leggings) return false
        if (boots != other.boots) return false
        if (canPickUpItems != other.canPickUpItems) return false
        if (effects != other.effects) return false
        if (hasAI != other.hasAI) return false
        if (isNameVisible != other.isNameVisible) return false
        if (isInvulnerable != other.isInvulnerable) return false
        if (isSilent != other.isSilent) return false
        if (hasGravity != other.hasGravity) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + displayName.hashCode()
        result = 31 * result + entityType.hashCode()
        result = 31 * result + health.hashCode()
        result = 31 * result + damage.hashCode()
        result = 31 * result + loot.hashCode()
        result = 31 * result + itemInMainHand.hashCode()
        result = 31 * result + itemInOffHand.hashCode()
        result = 31 * result + helmet.hashCode()
        result = 31 * result + chestplate.hashCode()
        result = 31 * result + leggings.hashCode()
        result = 31 * result + boots.hashCode()
        result = 31 * result + canPickUpItems.hashCode()
        result = 31 * result + effects.hashCode()
        result = 31 * result + hasAI.hashCode()
        result = 31 * result + isNameVisible.hashCode()
        result = 31 * result + isInvulnerable.hashCode()
        result = 31 * result + isSilent.hashCode()
        result = 31 * result + hasGravity.hashCode()
        return result
    }

    override fun toString(): String {
        return "CustomMob(name='$name', displayName=$displayName, entityType=$entityType, health=$health, damage=$damage, loot=$loot, itemInMainHand=$itemInMainHand, itemInOffHand=$itemInOffHand, helmet=$helmet, chestplate=$chestplate, leggings=$leggings, boots=$boots, canPickUpItems=$canPickUpItems, effects=$effects, hasAI=$hasAI, isNameVisible=$isNameVisible, isInvulnerable=$isInvulnerable, isSilent=$isSilent, hasGravity=$hasGravity)"
    }

    companion object {
        val toCustomMob = { input: Any? -> if (input is CustomMob) input else null }

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
                TextFormatter.format(args["displayName"].toString()),
                EntityType.valueOf(args["entityType"].toString()),
                (args["health"] as Number).toDouble(),
                (args["damage"] as Number).toDouble(),
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
                args["isSilent"] as Boolean,
                args["hasGravity"] as Boolean,
            )
        }
    }
}