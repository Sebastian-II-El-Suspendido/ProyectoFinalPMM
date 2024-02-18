package com.example.proyectofinalpmm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.proyectofinalpmm.databinding.ActivityCiudadBinding

class CiudadActivity : BaseActivity() {
    private lateinit var binding: ActivityCiudadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCiudadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btnRecoger = findViewById<Button>(R.id.btnEntrar)
        val btnContinuarObjeto = findViewById<Button>(R.id.btnContinuarCiudad)
        binding.ScrollComprar.visibility = View.GONE
        binding.btnEntrar2.visibility= View.GONE


        animateText(binding.textView,"Entras a la ciudad (Espero que con los bolsillos bien cargados de oro) con la necesidad de adquirir una nueva propiedad donde poder descansar de tus aventuras. Se te presentan distintas propiedas que puedes comprar")

        btnRecoger.setOnClickListener {
            val intent = Intent(this,MercaderActivity::class.java)
            startActivity(intent)
        }

        btnContinuarObjeto.setOnClickListener {
            binding.textView.visibility=View.GONE
            binding.ScrollComprar.visibility=View.VISIBLE
            binding.btnEntrar.visibility=View.GONE
            binding.btnContinuarCiudad.visibility=View.GONE
        }

        binding.btnEntrar2.setOnClickListener {
            binding.textView.visibility=View.VISIBLE
            binding.ScrollComprar.visibility=View.GONE
            binding.btnEntrar.visibility=View.VISIBLE
            binding.btnContinuarCiudad.visibility=View.VISIBLE
            binding.btnEntrar2.visibility=View.GONE

        }
    }


    private fun returnMainActivity() {
        val intent = Intent(this, RandomActivity::class.java)
        startActivity(intent)
    }
}