package ru.batr.shinedungeons.configs

import ru.batr.shinedungeons.configs.containers.Container
import ru.batr.shinedungeons.levels.Level

class DungeonsConfig : Config("dungeons", "") {
    val levels by lazy {
        Container.DefaultContainer(
            this,
            "levels",
            mapOf(),
            Container.toMap(Level.toLevel),
            Container.saveMap()
        )
    }
}