package com.example.proyectofinalpmm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.proyectofinalpmm.databinding.ActivityPersonajeCreadoBinding

class PersonajeCreadoActivity : BaseActivity() {
    private lateinit var binding: ActivityPersonajeCreadoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonajeCreadoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button4.setOnClickListener {
            val intent = Intent(this, RandomActivity::class.java)
            startActivity(intent)
        }

        binding.button5.setOnClickListener {
            val intent = Intent(this, CreacionPersonajeActivity::class.java)
            startActivity(intent)
        }









    }
}