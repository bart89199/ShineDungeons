package ru.batr.shinedungeons.mobs

import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.world.EntitiesLoadEvent
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class MobHandler : Listener {

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        loadMob(event.entity)
        val customMob = loadedCustomMobs[event.damager.uniqueId]
        if (customMob != null) {
            event.damage = customMob.damage
        }
    }

    @EventHandler
    fun onDead(event: EntityDeathEvent) {
        loadMob(event.entity)
        val customMob = loadedCustomMobs[event.entity.uniqueId]
        if (customMob != null) {
            val drops = event.drops
            drops.clear()
            for (i in customMob.loot) {
                val drop = ItemStack(i)
                drop.amount = (drop.amount * ThreadLocalRandom.current().nextFloat()).toInt()
                if (drop.amount != 0) drops.add(drop)
            }
            loadedCustomMobs.remove(event.entity.uniqueId)
        }
    }

    @EventHandler
    fun onLoad(event: EntitiesLoadEvent) {
        for (entity in event.entities) {
            loadMob(entity)
        }
    }

    companion object {
        val loadedCustomMobs = HashMap<UUID, CustomLivingMob>()
        val unloadedCustomMobs = HashMap<UUID, CustomMob>()

        fun loadMob(entity: Entity) {
            val customMob = unloadedCustomMobs[entity.uniqueId]
            if (customMob != null) {
                unloadedCustomMobs.remove(entity.uniqueId)
                loadedCustomMobs[entity.uniqueId] = customMob.toLivingMob(entity)
            }
        }
    }
}
