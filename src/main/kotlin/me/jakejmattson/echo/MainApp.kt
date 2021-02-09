package me.jakejmattson.echo

import dev.kord.common.annotation.KordPreview
import dev.kord.common.entity.Snowflake
import dev.kord.common.kColor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.jakejmattson.discordkt.api.dsl.bot
import me.jakejmattson.discordkt.api.extensions.addInlineField
import me.jakejmattson.discordkt.api.extensions.profileLink
import me.jakejmattson.echo.services.AudioService
import java.io.*
import java.util.*


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

        onStart {
            val lavalinkServer = File("server/Lavalink.jar")
            val command = "java -jar \"${lavalinkServer.absolutePath}\""
            val proc = Runtime.getRuntime().exec(command)
            val stdInput = Scanner(proc.inputStream)

            GlobalScope.launch {
                while (stdInput.hasNext()) {
                    val line = stdInput.nextLine()
                    println(line)
                }
            }
        }
    }
}