package ru.batr.sd.configs

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack
import ru.batr.sd.TextFormatter
import ru.batr.sd.configs.containers.Container
import ru.batr.sd.mobs.CustomMob

class MainConfig : Config("config", "") {
    val testMob = Container.CustomMobContainer(
        this,
        "test.mob",
        CustomMob(
            "test",
            TextFormatter.format("<red>Я есть ТЕСТ"),
            EntityType.ENDERMAN,
            300.0,
            400.0,
            true,
            listOf(ItemStack(Material.EGG), ItemStack(Material.ACACIA_BOAT, 45)),
            ItemStack(Material.BOW),
            ItemStack(Material.ARROW),
            ItemStack(Material.ACACIA_DOOR),
            )
    )
}
