package com.example.proyectofinalpmm

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.proyectofinalpmm.base_de_datos.SQLiteHelper
import com.example.proyectofinalpmm.databinding.ActivityAjustesBinding
import com.example.proyectofinalpmm.musica.Musica
import com.google.firebase.auth.FirebaseAuth

class AjustesActivity : BaseActivity() {
    private lateinit var binding: ActivityAjustesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAjustesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser?.uid
        val dbHelper = SQLiteHelper(this)

        binding.button6.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            auth.signOut()
            startActivity(intent)
        }

        binding.button7.setOnClickListener {
            val musica = user?.let { dbHelper.obtenerMusicaorID(it, this) }
            if (musica!=0){
                if (user != null) {
                    dbHelper.modificarMusicaPorID(user, 0)
                }
                val intent = Intent(this@AjustesActivity, Musica::class.java)
                intent.action = Musica.ACTION_START_MUSIC
                startService(intent)
            }else {
                dbHelper.modificarMusicaPorID(user, 1)
                val intent = Intent(this@AjustesActivity, Musica::class.java)
                intent.action = Musica.ACTION_STOP_MUSIC
                startService(intent)
            }
        }
    }
}