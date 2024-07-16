package ru.batr.sd.mobs

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

class MobHandler : Listener {

    companion object {
        val map = HashMap<UUID, CustomLivingMob>()
    }

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        val customMob = map[event.damager.uniqueId]
        if (customMob != null) {
            event.damage = customMob.damage
        }
    }

    @EventHandler
    fun onDead(event: EntityDeathEvent) {
        val customMob = map[event.entity.uniqueId]
        if (customMob != null) {
            val drops = event.drops
            drops.clear()
            for (i in customMob.loot) {
                val drop = ItemStack(i)
                drop.amount = (drop.amount * ThreadLocalRandom.current().nextFloat()).toInt()
                if (drop.amount != 0) drops.add(drop)
            }
            map.remove(event.entity.uniqueId)
        }
    }
}