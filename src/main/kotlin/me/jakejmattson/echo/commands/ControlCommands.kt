package me.jakejmattson.echo.commands

import me.jakejmattson.discordkt.api.arguments.EveryArg
import me.jakejmattson.discordkt.api.dsl.commands
import me.jakejmattson.echo.services.AudioService

fun control(player: AudioService) = commands("Control") {
    guildCommand("Play") {
        description = "Play a song"
        execute(EveryArg) {
            player.play(getMember()!!, args.first)
        }
    }

    guildCommand("Pause") {
        description = "Pause the currently playing song"
        execute {
            player.pause(getMember()!!)
        }
    }

    guildCommand("Skip", "Next") {
        description = "Skip the currently playing song"
        execute {
            player.stop(getMember()!!)
        }
    }

    guildCommand("List") {
        description = "List all queued songs"
        execute {

        }
    }
}