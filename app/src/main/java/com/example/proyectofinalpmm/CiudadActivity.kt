package com.example.proyectofinalpmm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalpmm.databinding.ActivityCiudadBinding

class CiudadActivity : BaseActivity() {
    private lateinit var binding: ActivityCiudadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCiudadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val btnContinuarCiudad = findViewById<Button>(R.id.btnContinuarCiudad)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerCity)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        val ciudadesList = listOf(
            R.drawable.casa1,
            R.drawable.casa2,
            R.drawable.casa3,
            R.drawable.casa4,
            R.drawable.casa5,
            R.drawable.casa6,
            R.drawable.casa7,
            R.drawable.casa8,
            R.drawable.casa9,
            R.drawable.casa10,
            R.drawable.casa11,
            R.drawable.casa12,
        )
        val zonasList = listOf(
            "Distrito de las Mil Fuentes",
            "Barrio de los Sueños Encantados",
            "Plaza del Dragón Dorado",
            "Callejón de las Sombras Veladas",
            "Sector de las Maravillas Arcanas",
            "Avenida de los Secretos Antiguos",
            "Zona de las Estrellas Errantes",
            "Pasaje de la Luna Plateada",
            "Barriada de Luna Plateada",
            "Calle de los Misterios Olvidados",
            "Sector de las Mariposas de Cristal",
            "Área de la Aurora Brillante"
        )
        val adapter = ImageAdapter(this, ciudadesList, zonasList)
        recyclerView.adapter = adapter

        val scrollComprar = findViewById<LinearLayout>(R.id.ScrollComprar)
        scrollComprar.visibility = View.GONE
        binding.btnEntrar2.visibility= View.GONE

        val globalButton = findViewById<ImageView>(R.id.ajustesBoton)
        globalButton.setOnClickListener {
            val intentb = Intent(this,AjustesActivity::class.java)
            startActivity(intentb)
        }


        Log.v("Casas",ciudadesList.size.toString())
        Log.v("Barrios",zonasList.size.toString())

        animateText(binding.textView,"Entras a la ciudad (Espero que con los bolsillos bien cargados de oro) con la necesidad de adquirir una nueva propiedad donde poder descansar de tus aventuras. Se te presentan distintas propiedas que puedes comprar")

        btnEntrar.setOnClickListener {
            val intent = Intent(this,MercaderActivity::class.java)
            startActivity(intent)
        }

        btnContinuarCiudad.setOnClickListener {
            binding.textView.visibility=View.GONE
            scrollComprar.visibility=View.VISIBLE
            binding.btnEntrar.visibility=View.GONE
            binding.btnContinuarCiudad.visibility=View.GONE
        }

        binding.btnEntrar2.setOnClickListener {
            binding.textView.visibility=View.VISIBLE
            scrollComprar.visibility=View.GONE
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