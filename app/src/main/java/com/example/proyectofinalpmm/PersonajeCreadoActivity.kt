package com.example.proyectofinalpmm

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.proyectofinalpmm.base_de_datos.SQLiteHelper
import com.example.proyectofinalpmm.databinding.ActivityPersonajeCreadoBinding
import com.google.firebase.auth.FirebaseAuth

class PersonajeCreadoActivity : BaseActivity() {
    private lateinit var binding: ActivityPersonajeCreadoBinding

    private lateinit var razaPersonaje : TextView
    private lateinit var clasePersonaje : TextView
    private lateinit var estadoVitalPersonaje : TextView
    private lateinit var nombrePersonaje : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonajeCreadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        razaPersonaje = binding.RazaPersonaje
        clasePersonaje = binding.ClasePersonaje
        estadoVitalPersonaje = binding.EstadoVitalPersonaje
        nombrePersonaje = binding.NombrePersonaje

        val auth = FirebaseAuth.getInstance()
        val idUser = auth.currentUser?.uid
        val dbHelper = SQLiteHelper(this)
        val personaje = idUser?.let { dbHelper.obtenerPersonajePorID(it, this) }

        if (personaje != null) {
            razaPersonaje.text = personaje.getRaza().toString()
            clasePersonaje.text = personaje.getClase().toString()
            estadoVitalPersonaje.text = personaje.getEstadoVital().toString()
            nombrePersonaje.text = personaje.getNombre()
        }


        val globalButton = findViewById<ImageView>(R.id.ajustesBoton)
        globalButton.setOnClickListener {
            val intentb = Intent(this,AjustesActivity::class.java)
            startActivity(intentb)
        }

        binding.button4.setOnClickListener {
            val intent = Intent(this, RandomActivity::class.java)
            startActivity(intent)
        }

        binding.button5.setOnClickListener {
            val intent = Intent(this, CreacionPersonajeActivity::class.java)
            intent.putExtra("modificar", true)
            startActivity(intent)
        }

    }
}