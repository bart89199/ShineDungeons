package ru.batr.sd

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

object TextFormatter {

    val DEFAULT_SERIALIZER: MiniMessage = MiniMessage.miniMessage()

    val DEFAULT_PLACEHOLDERS: Array<TagResolver> = arrayOf()

    fun format(
        input: String,
        serializer: MiniMessage = DEFAULT_SERIALIZER,
        vararg placeholders: TagResolver = DEFAULT_PLACEHOLDERS
    ) = serializer.deserialize(input, *placeholders)

    fun format(
        input: List<String>,
        serializer: MiniMessage = DEFAULT_SERIALIZER,
        vararg placeholders: TagResolver = DEFAULT_PLACEHOLDERS
    ): List<Component> {
        val output = ArrayList<Component>();

        for (i in input) {
            output.add(serializer.deserialize(i))
        }
        return output
    }

}