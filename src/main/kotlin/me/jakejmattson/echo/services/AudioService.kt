package me.jakejmattson.echo.services

import dev.kord.core.entity.Member
import dev.kord.core.entity.channel.VoiceChannel
import dev.kord.x.lavalink.audio.Link
import dev.kord.x.lavalink.kord.connectAudio
import dev.kord.x.lavalink.kord.getLink
import dev.kord.x.lavalink.kord.lavakord
import dev.kord.x.lavalink.rest.TrackResponse
import dev.kord.x.lavalink.rest.loadItem
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import me.jakejmattson.discordkt.api.Discord
import me.jakejmattson.discordkt.api.annotations.Service
import java.net.ConnectException

@Service
class AudioService(discord: Discord) {
    private val lavaKord = discord.kord.lavakord()

    fun addServer() = lavaKord.addNode("ws://127.0.0.1:2333/", "password")

    suspend fun play(member: Member, query: String) {
        println("Playing $query from ${member.displayName}")

        val link = connect(member) ?: return
        val player = link.player
        val search = if (query.startsWith("http")) query else "ytsearch:$query"
        val response = link.loadItem(search)

        println(response.tracks.first().info)

        when (response.loadType) {
            TrackResponse.LoadType.TRACK_LOADED -> player.playTrack(response.tracks.first())
            TrackResponse.LoadType.PLAYLIST_LOADED -> player.playTrack(response.tracks.first())
            TrackResponse.LoadType.SEARCH_RESULT -> player.playTrack(response.tracks.first())
            TrackResponse.LoadType.NO_MATCHES -> println("No matches")
            TrackResponse.LoadType.LOAD_FAILED -> println(response.exception?.message ?: "Error")
        }
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