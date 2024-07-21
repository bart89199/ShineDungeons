package ru.batr.shinedungeons.configs

import ru.batr.shinedungeons.configs.containers.Container
import ru.batr.shinedungeons.mobs.CustomMob

class MobsConfig : Config("mobs", "") {
    val savedMobs by lazy {
        Container.DefaultContainer(
            this,
            "data.savedMobs",
            mapOf(),
            Container.toMap(CustomMob.toCustomMob),
            Container.saveMap()
        )
    }
    val mobs by lazy {
        Container.DefaultContainer(
            this,
            "data.mobs",
            mapOf(),
            Container.toMap(CustomMob.toCustomMob),
            Container.saveMap()
        )
    }
}