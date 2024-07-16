package ru.batr.sd.configs

import com.google.common.base.Charsets
import org.bukkit.configuration.file.YamlConfiguration
import ru.batr.sd.SD
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.util.logging.Level

abstract class Config(val fileName: String, val path: String) {
    lateinit var config: YamlConfiguration
        protected set
    lateinit var file: File
        protected set
    val fullPath: String
            get() = if (path != "") "$path${File.separator}$fileName.yml" else "$fileName.yml"

    fun load() {
        file = File(fullPath)
        if (!file.exists()) {
            SD.instance.saveResource(fullPath, false)
        }
        try {
            config.load(file)
        } catch (e: Exception) {
            e.printStackTrace()
            SD.instance.logger.log(
                Level.SEVERE,
                "Problems with load $fileName please check updates or contact with author"
            )
        }
    }

    fun reload() {
        config = YamlConfiguration.loadConfiguration(file)

        config.setDefaults(
            YamlConfiguration.loadConfiguration(
                InputStreamReader(
                    SD.instance.getResource(fullPath) ?: return, Charsets.UTF_8
                )
            )
        )
    }

    fun save() {
        try {
            config.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
            SD.instance.logger.log(
                Level.SEVERE,
                "Problems with save $fileName.yml please check updates or contact with author"
            )
        }
    }
}