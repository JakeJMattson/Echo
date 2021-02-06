package me.jakejmattson.echo

import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.Snowflake
import dev.kord.common.kColor
import me.jakejmattson.discordkt.api.dsl.bot
import me.jakejmattson.discordkt.api.extensions.addInlineField
import me.jakejmattson.discordkt.api.extensions.profileLink

@KordPreview
fun main(args: Array<String>) {
    val token = args.firstOrNull() ?: return

    bot(token) {
        prefix {
            ">"
        }

        mentionEmbed {
            val kord = it.discord.kord
            val prefix = it.prefix()

            title = "Echo"
            color = it.discord.configuration.theme?.kColor
            description = "\nCurrent prefix is `$prefix`" +
                "\nUse `${prefix}help` to see commands."

            author {
                val user = kord.getUser(Snowflake(254786431656919051))!!

                icon = user.avatar.url
                name = user.tag
                url = user.profileLink
            }

            thumbnail {
                url = kord.getSelf().avatar.url
            }

            addInlineField("", "[[Source]](https://github.com/JakeJMattson/Echo)")

            footer {
                val versions = it.discord.versions
                text = "1.0.0 - ${versions.library} - ${versions.kord}"
            }
        }
    }
}