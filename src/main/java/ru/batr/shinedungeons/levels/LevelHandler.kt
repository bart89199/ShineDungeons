package ru.batr.shinedungeons.levels

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import ru.batr.shinedungeons.mobs.CustomLivingMob
import ru.batr.shinedungeons.mobs.MobHandler

class LevelHandler : Listener {
    @EventHandler(priority = EventPriority.HIGH)
    fun onDead(event: EntityDeathEvent) {
        MobHandler.loadMob(event.entity)
        val mob = MobHandler.loadedCustomMobs[event.entity.uniqueId]
        if (mob != null) {
            val level = mobs[mob]
            if (level != null) {
                levelMobs[level]?.remove(mob)
                if (levelMobs[level]?.size == 0) {
                    levelMobs.remove(level)
                    level.finish()
                }
            }
        }
    }

    companion object {
        val levelMobs = HashMap<Level.MobsLevel, MutableList<CustomLivingMob>>()
        val mobs = HashMap<CustomLivingMob, Level.MobsLevel>()
    }
}