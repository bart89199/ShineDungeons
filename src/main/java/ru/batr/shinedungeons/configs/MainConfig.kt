package ru.batr.shinedungeons.configs

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack
import ru.batr.shinedungeons.TextFormatter
import ru.batr.shinedungeons.configs.containers.Container
import ru.batr.shinedungeons.mobs.CustomMob

class MainConfig : Config("config", "") {
    val testMob by lazy {
        Container.DefaultContainer(
            this,
            "test.mob",
            CustomMob(
                "config example",
                TextFormatter.format("<red>Я есть ТЕСТ"),
                EntityType.ENDERMAN,
                300.0,
                400.0,
                listOf(ItemStack(Material.EGG), ItemStack(Material.ACACIA_BOAT, 45)),
                ItemStack(Material.BOW),
                ItemStack(Material.ARROW),
                ItemStack(Material.ACACIA_DOOR),
            ),
            CustomMob.toCustomMob
        )
    }

    val testMob1 by lazy {
        Container.DefaultContainer(
            this,
            "test.mob1",
            CustomMob(
                "config example 1",
                TextFormatter.format("<red>Я есть ТЕСТ32423"),
                EntityType.ENDERMAN,
                300.0,
                400.0,
                listOf(ItemStack(Material.EGG), ItemStack(Material.ACACIA_BOAT, 45)),
                ItemStack(Material.BOW),
                ItemStack(Material.ARROW),
                ItemStack(Material.ACACIA_DOOR),
            ),
            CustomMob.toCustomMob
        )
    }
}
