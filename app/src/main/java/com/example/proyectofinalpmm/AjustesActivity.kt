package com.example.proyectofinalpmm

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.proyectofinalpmm.databinding.ActivityAjustesBinding
import com.example.proyectofinalpmm.musica.Musica
import com.google.firebase.auth.FirebaseAuth

class AjustesActivity : BaseActivity() {
    private lateinit var binding: ActivityAjustesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAjustesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button6.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            startActivity(intent)
        }

        binding.button7.setOnClickListener {
            val musica = Intent(this, Musica::class.java)
            val serviceConnection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    val binder = service as Musica.MusicBinder
                    val musicService = binder.getService()

                    // Detener la música
                    musicService.stopMusic()
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                }
            }
        }
    }
}