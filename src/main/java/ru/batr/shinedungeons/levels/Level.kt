package ru.batr.shinedungeons.levels

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.serialization.ConfigurationSerializable
import ru.batr.shinedungeons.ShineDungeons
import ru.batr.shinedungeons.configs.containers.Container
import ru.batr.shinedungeons.mobs.CustomMob

interface Level : ConfigurationSerializable {
    val name: String
    val delayBeforeStart: Long
    val next: Level?

    fun start() {
        Bukkit.getScheduler().runTaskLater(
            ShineDungeons.instance,
            Runnable {
                onStart()
            },
            delayBeforeStart * 20
        )
    }

    fun finish() {
        onFinish()
        next?.start()
    }

    fun onStart()
    fun onFinish()

    class MobsLevel(
        override val name: String,
        override val next: Level?,
        val mobsToLocs: Map<CustomMob, Location>,
        override val delayBeforeStart: Long
    ) : Level {
        override fun onStart() {
            for ((mob, location) in mobsToLocs) {
                mob.spawnMob(location)
            }
        }

        override fun onFinish() {

        }

        override fun serialize(): MutableMap<String, Any> {
            val output = HashMap<String, Any>()
            output["name"] = name
            output["next"] = next?.name ?: "null"
            output["delayBeforeStart"] = delayBeforeStart
            output["mobs"] = mobsToLocs.map { it.key.name to it.value }.toMap()
            return output
        }

        companion object {
            @JvmStatic
            fun deserialize(args: Map<String, Any>): MobsLevel {
                val mobsInput = args["mobs"]
                val next = args["next"].toString()
                if (mobsInput !is Map<*, *>) throw IllegalArgumentException("Can't cast mobs map during deserializing MobsLevel")
                val mobs: Map<CustomMob, Location> = Container.toMap(Container.toLocation).toT(mobsInput)
                    ?.map { (ShineDungeons.mobsConfig.mobs.value[it.key] ?: throw IllegalArgumentException("Can't find mob \'${it.key}\' in \'${args["name"]}\' level during deserializing MobsLevel")) to it.value }?.toMap() ?: HashMap()
                return MobsLevel(
                    args["name"] as String,
                    if (next == "null") null else ShineDungeons.dungeonsConfig.levels.value[next],
                    mobs,
                    args["delayBeforeStart"] as Long,
                )
            }
        }

    }

    companion object {
        val toLevel = { input: Any? -> if (input is Level) input else null }
    }
}