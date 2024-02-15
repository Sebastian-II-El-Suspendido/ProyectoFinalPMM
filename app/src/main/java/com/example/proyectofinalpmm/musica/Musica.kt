package com.example.proyectofinalpmm.musica

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.example.proyectofinalpmm.R

class Musica : Service() {

    private lateinit var mediaPlayer: MediaPlayer
    private val binder = MusicBinder()

    inner class MusicBinder : Binder() {
        fun getService(): Musica = this@Musica
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.relic_song)
        mediaPlayer.isLooping = true
    }

    fun startMusic() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    fun pauseMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    fun stopMusic() {
        mediaPlayer.stop()
        mediaPlayer.prepareAsync()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}