package com.example.proyectofinalpmm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CiudadActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciudad)
        val btnRecoger = findViewById<Button>(R.id.btnEntrar)
        val btnContinuarObjeto = findViewById<Button>(R.id.btnContinuarCiudad)

        btnRecoger.setOnClickListener {
            val intent = Intent(this,TobeContinuedActivity::class.java)
            startActivity(intent)
        }

        btnContinuarObjeto.setOnClickListener {
            returnMainActivity()
        }
    }

    private fun returnMainActivity() {
        val intent = Intent(this, RandomActivity::class.java)
        startActivity(intent)
    }
}