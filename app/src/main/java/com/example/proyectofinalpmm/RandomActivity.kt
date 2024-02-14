package com.example.proyectofinalpmm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

//ActivityCorrespondiente al MainActivity del ejercicio 2

class RandomActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDado = findViewById<Button>(R.id.btnDado)
        btnDado.setOnClickListener {
            RandomActivityFun()
        }
    }

    private fun RandomActivityFun() {
        val actividadAleatoria = Random.nextInt(4)
        val intent = when (actividadAleatoria) {
            0 -> Intent(this, ObjetoActivity::class.java)
            1 -> Intent(this, CiudadActivity::class.java)
            2 -> Intent(this, MercaderActivity::class.java)
            else -> Intent(this, EnemigoActivity::class.java)
        }
        startActivity(intent)
    }
}