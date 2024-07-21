package ru.batr.shinedungeons

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import ru.batr.shinedungeons.configs.DungeonsConfig
import ru.batr.shinedungeons.configs.MainConfig
import ru.batr.shinedungeons.configs.MobsConfig
import ru.batr.shinedungeons.levels.LevelHandler
import ru.batr.shinedungeons.mobs.CustomMob
import ru.batr.shinedungeons.mobs.MobHandler
import java.util.*
import kotlin.collections.HashMap

class ShineDungeons : JavaPlugin() {
    companion object {
        lateinit var instance: ShineDungeons
            private set
        lateinit var mainConfig: MainConfig
        lateinit var mobsConfig: MobsConfig
        lateinit var dungeonsConfig: DungeonsConfig
            private set
    }
    override fun onEnable() {
        instance = this
        logger.info("Enabling ShineMobs")
        ConfigurationSerialization.registerClass(CustomMob::class.java)
        mainConfig = MainConfig()
        mobsConfig = MobsConfig()
        dungeonsConfig = DungeonsConfig()
        server.pluginManager.registerEvents(MobHandler(), this)
        server.pluginManager.registerEvents(LevelHandler(), this)
        mainConfig.testMob.value.spawnMob(Location(Bukkit.getWorld("world"), 0.0, 0.0, 0.0))
        mainConfig.testMob1.value = CustomMob(
            "mob1",
            TextFormatter.format("<red>Mob1"),
            EntityType.BLAZE,
            2.0,
            500.0,
            listOf(ItemStack(Material.ARROW, 64)),
            effects = listOf(
                PotionEffect(PotionEffectType.GLOWING, 630_720_000, 0),
                PotionEffect(PotionEffectType.JUMP, 630_720_000, 3, false, true, false)
            )
        )

        mainConfig.testMob1.value.spawnMob(Location(Bukkit.getWorld("world"), 0.0, 0.0, 0.0))
      //  mainConfig.config.getConfigurationSection()
     //   val mp = dungeonsConfig.mapTest.value
    /*   dungeonsConfig.mapTest.value = mapOf(
            "one1" to mainConfig.testMob.value,
            "uuide4be998994a44542b4a8756fe92cc073" to mainConfig.testMob1.value,
        )*/
       // println(mobsConfig.config.get("savedMobs.uuid7f18e6151fe24d2aa2d071398554bfc1"))
    //    val mp = dungeonsConfig.mapTest.value
      //  val mp = dungeonsConfig.config.get("a.a") as Map<CustomMob, String>
   //     logger.info(mp.toString())

      //  mp.forEach { (t, u) -> logger.warning("${t} ${u.name}") }

        val savedMobs = mobsConfig.savedMobs.value
        for ((k, v) in savedMobs) {
            try {
                val key = UUID.fromString(k)
                MobHandler.unloadedCustomMobs[key] = v
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
 //       println(mobsConfig.config.get("data.uuids.uuid9b0c4ede2f9c43c4905fc866fa805375"))
    }

    override fun onDisable() {
        logger.info("Disabling ShineMobs")
        val mobsUUIDS = HashMap<String, CustomMob>()
        for ((k, v) in MobHandler.loadedCustomMobs) {
            mobsUUIDS[k.toString()] = v.toCustomMob()
        }
        for ((k, v) in MobHandler.unloadedCustomMobs) {
            mobsUUIDS[k.toString()] = v
        }
        mobsConfig.savedMobs.value = mobsUUIDS
    }

}