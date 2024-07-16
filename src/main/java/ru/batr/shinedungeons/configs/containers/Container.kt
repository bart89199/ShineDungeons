package ru.batr.sd.configs.containers

import org.bukkit.Location
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import ru.batr.sd.configs.Config
import ru.batr.sd.mobs.CustomMob

abstract class Container<T>(val config: Config, val path: String, val default: T) {

    var value: T
        get() = get()
        set(value) {
            config.config.set(path, value)
            config.save()
        }

    abstract fun get() : T

    class IntContainer(config: Config, path: String, default: Int) : Container<Int>(config, path, default) {
        override fun get(): Int {
            return config.config.getInt(path, default)
        }
    }
    class DoubleContainer(config: Config, path: String, default: Double) : Container<Double>(config, path, default) {
        override fun get(): Double {
            return config.config.getDouble(path, default)
        }
    }
    class BooleanContainer(config: Config, path: String, default: Boolean) : Container<Boolean>(config, path, default) {
        override fun get(): Boolean {
            return config.config.getBoolean(path, default)
        }
    }
    class StringContainer(config: Config, path: String, default: String) : Container<String>(config, path, default) {
        override fun get(): String {
            return config.config.getString(path, default) ?: default
        }
    }
    class ItemStackContainer(config: Config, path: String, default: ItemStack) : Container<ItemStack>(config, path, default) {
        override fun get(): ItemStack {
            return config.config.getSerializable(path, ItemStack::class.java, default) ?: default
        }
    }
    class LocationContainer(config: Config, path: String, default: Location) : Container<Location>(config, path, default) {
        override fun get(): Location {
            return config.config.getSerializable(path, Location::class.java, default) ?: default
        }
    }
    class OfflinePlayerContainer(config: Config, path: String, default: OfflinePlayer) : Container<OfflinePlayer>(config, path, default) {
        override fun get(): OfflinePlayer {
            return config.config.getSerializable(path, OfflinePlayer::class.java, default) ?: default
        }
    }
    class CustomMobContainer(config: Config, path: String, default: CustomMob) : Container<CustomMob>(config, path, default) {
        override fun get(): CustomMob {
            return config.config.getSerializable(path, CustomMob::class.java, default) ?: default
        }
    }
    abstract class ListContainer<T>(config: Config, path: String, default: List<T>) : Container<List<T>>(config, path, default) {
        override fun get(): List<T> {
            val got = config.config.getList(path) ?: default
            val ans = ArrayList<T>()
            for (i in got) {
                ans.add(toT(i ?: continue) ?: continue)
            }
            return ans
        }

        abstract fun toT(got: Any): T?
    }
    class IntListContainer(config: Config, path: String, default: List<Int>) : ListContainer<Int>(config, path, default) {
        override fun toT(got: Any) = if (got is Number) got.toInt() else null
    }
    class DoubleListContainer(config: Config, path: String, default: List<Double>) : ListContainer<Double>(config, path, default) {
        override fun toT(got: Any) = if (got is Number) got.toDouble() else null
    }
    class BooleanListContainer(config: Config, path: String, default: List<Boolean>) : ListContainer<Boolean>(config, path, default) {
        override fun toT(got: Any) = if (got is Boolean) got else null
    }
    class StringListContainer(config: Config, path: String, default: List<String>) : ListContainer<String>(config, path, default) {
        override fun toT(got: Any) = got.toString()
    }
    class ItemStackListContainer(config: Config, path: String, default: List<ItemStack>) : ListContainer<ItemStack>(config, path, default) {
        override fun toT(got: Any) = if (got is ItemStack) got else null
    }
    class LocationListContainer(config: Config, path: String, default: List<Location>) : ListContainer<Location>(config, path, default) {
        override fun toT(got: Any) = if (got is Location) got else null
    }
    class OfflinePlayerListContainer(config: Config, path: String, default: List<OfflinePlayer>) : ListContainer<OfflinePlayer>(config, path, default) {
        override fun toT(got: Any) = if (got is OfflinePlayer) got else null
    }
    class CustomMobListContainer(config: Config, path: String, default: List<CustomMob>) : ListContainer<CustomMob>(config, path, default) {
        override fun toT(got: Any) = if (got is CustomMob) got else null
    }
}