package me.jakejmattson.echo.data

import dev.kord.core.entity.User
import dev.kord.x.lavalink.audio.player.Track

data class Song(val user: User, val track: Track)
