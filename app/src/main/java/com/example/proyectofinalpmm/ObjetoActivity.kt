package com.example.proyectofinalpmm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.proyectofinalpmm.databinding.ActivityObjetoBinding

class ObjetoActivity : BaseActivity() {
        private lateinit var binding: ActivityObjetoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityObjetoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        animateText(binding.textViewObjDesc,"Te adentras en una profunda mazmorra, tras númerosas trampas y enemigos, consigues llegar a una sala del tesoro. Recoge los objetos que consideres, pero recuerda que tienes un espacio límitado")


        val globalButton = findViewById<ImageView>(R.id.ajustesBoton)
        globalButton.setOnClickListener {
            val intentb = Intent(this,AjustesActivity::class.java)
            startActivity(intentb)
        }

        val btnRecoger = findViewById<Button>(R.id.btnRecoger)
        val btnContinuarObjeto = findViewById<Button>(R.id.btnContinuarObj)

        btnRecoger.setOnClickListener {
            returnMainActivity()
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