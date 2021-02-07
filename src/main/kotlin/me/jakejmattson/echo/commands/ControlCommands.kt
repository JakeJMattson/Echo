package me.jakejmattson.echo.commands

import me.jakejmattson.discordkt.api.arguments.EveryArg
import me.jakejmattson.discordkt.api.dsl.commands

fun control() = commands("Control") {
    guildCommand("Play") {
        description = "Play a song"
        execute(EveryArg) {

        }
    }

    guildCommand("Pause") {
        description = "Pause the currently playing song"
        execute {

        }
    }

    guildCommand("Skip", "Next") {
        description = "Skip the currently playing song"
        execute {

        }
    }

    guildCommand("List") {
        description = "List all queued songs"
        execute {

        }
    }
}