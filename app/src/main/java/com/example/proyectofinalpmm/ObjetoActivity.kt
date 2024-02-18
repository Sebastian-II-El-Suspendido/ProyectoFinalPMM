package com.example.proyectofinalpmm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.proyectofinalpmm.databinding.ActivityObjetoBinding

class ObjetoActivity : BaseActivity() {
        private lateinit var binding: ActivityObjetoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objeto)


        animateText(binding.textView14,"Te adentras en una profunda mazmorra, tras númerosas trampas y enemigos, consigues llegar a una sala del tesoro. Recoge los objetos que consideres, pero recuerda que tienes un espacio límitado")


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