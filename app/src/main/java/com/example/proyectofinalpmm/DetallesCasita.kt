package com.example.proyectofinalpmm

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.proyectofinalpmm.databinding.ActivityDetallesCasitaBinding


class DetallesCasita :  BaseActivity() {
    private lateinit var binding: ActivityDetallesCasitaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesCasitaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imagenCasa = intent.getIntExtra("IMAGEN_CASA", 0)
        val nombreBarrio = intent.getStringExtra("NOMBRE_BARRIO") ?: ""
        val descBarrio = intent.getStringExtra("DESC_BARRIO") ?: ""
        val fondoCasa = intent.getIntExtra("FONDO_CASA", 0)



        if (fondoCasa != 0) {
            val layoutPrincipal = findViewById<View>(R.id.layoutPrincipal)
            layoutPrincipal.setBackgroundResource(fondoCasa)
            binding.imageViewCasa.setImageResource(imagenCasa)
            binding.textViewCasa.text= nombreBarrio
            animateText(binding.textView16,descBarrio)

        }



        binding.btnVolverCiudad.setOnClickListener{
            val intent2 = Intent(this, CiudadActivity::class.java)
            startActivity(intent2)
        }



    }
}