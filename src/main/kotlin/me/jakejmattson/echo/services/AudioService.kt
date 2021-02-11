package me.jakejmattson.echo.services

import dev.kord.common.entity.Snowflake
import dev.kord.core.entity.Member
import dev.kord.core.entity.channel.VoiceChannel
import dev.kord.x.lavalink.audio.Link
import dev.kord.x.lavalink.kord.connectAudio
import dev.kord.x.lavalink.kord.getLink
import dev.kord.x.lavalink.kord.lavakord
import dev.kord.x.lavalink.rest.TrackResponse
import dev.kord.x.lavalink.rest.loadItem
import kotlinx.coroutines.flow.firstOrNull
import me.jakejmattson.discordkt.api.Discord
import me.jakejmattson.discordkt.api.annotations.Service
import me.jakejmattson.echo.data.Song
import me.jakejmattson.echo.data.by

@Service
class AudioService(discord: Discord) {
    private val lavaKord = discord.kord.lavakord()
    private val songMap = mapOf<Snowflake, MutableList<Song>>()

    init {
        lavaKord.addNode("ws://127.0.0.1:2333/", "password")
    }

    suspend fun play(member: Member, query: String) {
        println("Playing $query from ${member.displayName}")

        val link = connect(member) ?: return
        val search = if (query.startsWith("http")) query else "ytsearch:$query"
        val response = link.loadItem(search)

        val songs = when (response.loadType) {
            TrackResponse.LoadType.TRACK_LOADED -> listOf(response.track.toTrack() by member.id)
            TrackResponse.LoadType.SEARCH_RESULT -> listOf(response.track.toTrack() by member.id)
            TrackResponse.LoadType.PLAYLIST_LOADED -> response.tracks.map { it.toTrack() by member.id }
            TrackResponse.LoadType.NO_MATCHES -> null
            TrackResponse.LoadType.LOAD_FAILED -> null
        } ?: return

        songMap[member.guildId]?.addAll(songs)
    }

    suspend fun pause(member: Member) {
        val link = connect(member) ?: return
        link.player.pause()
    }

    suspend fun stop(member: Member) {
        val link = connect(member) ?: return
        link.player.stopTrack()
    }

    private suspend fun connect(member: Member): Link? {
        val player = member.getPlayer()
        val state = member.getVoiceStateOrNull()
        val channel = state?.channelId ?: member.guild.channels.firstOrNull { it is VoiceChannel }?.id ?: return null

        player.connectAudio(channel)

        return player
    }

    private fun Member.getPlayer() = guild.getLink(lavaKord)
}