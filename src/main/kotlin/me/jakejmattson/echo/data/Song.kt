package me.jakejmattson.echo.data

import dev.kord.common.entity.Snowflake
import dev.kord.x.lavalink.audio.player.Track

data class Song(val userId: Snowflake, val track: Track)

infix fun Track.by(userId: Snowflake) = Song(userId, this)
