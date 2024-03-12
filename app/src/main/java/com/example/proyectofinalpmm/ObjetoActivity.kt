package com.example.proyectofinalpmm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.proyectofinalpmm.base_de_datos.SQLiteHelper
import com.example.proyectofinalpmm.databinding.ActivityObjetoBinding
import com.google.firebase.auth.FirebaseAuth

class ObjetoActivity : BaseActivity() {
        private lateinit var binding: ActivityObjetoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityObjetoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        animateText(binding.textViewObjDesc,"Te adentras en una profunda mazmorra, tras númerosas trampas y enemigos, consigues llegar a una sala del tesoro. Recoge los objetos que consideres, pero recuerda que tienes un espacio límitado")

        val auth = FirebaseAuth.getInstance()
        val id = auth.currentUser?.uid
        val dbHelper = SQLiteHelper(this)
        val listaObjetos = dbHelper.obtenerListaArticulos()
        var objeto = listaObjetos.random()
        var peso = dbHelper.obtenerPesoTotalPersonaje(id, dbHelper.obtenerArticulosDePersonaje(id))

        val globalButton = findViewById<ImageView>(R.id.ajustesBoton)
        globalButton.setOnClickListener {
            val intentb = Intent(this,AjustesActivity::class.java)
            startActivity(intentb)
        }

        val imagen = binding.imgCiudad
        imagen.setImageResource(objeto.getUri())
        val btnRecoger = findViewById<Button>(R.id.btnRecoger)
        val btnContinuarObjeto = findViewById<Button>(R.id.btnContinuarObj)

        btnRecoger.setOnClickListener {
            val idObjeto = dbHelper.obtenerIdArticuloPorNombre(objeto.getNombre().toString())
            val cantidad : Int = dbHelper.obtenerCantidadArticuloPersonaje(id, idObjeto)
            val nextPeso = peso + objeto.getPeso()
            Log.i("ayuda", "$nextPeso peso actual")
            Log.i("ayuda", "${dbHelper.obtenerPesoMax()} peso total")
            if (nextPeso < dbHelper.obtenerPesoMax()){
                idObjeto?.let { it1 ->
                    if (id != null) {
                        dbHelper.actualizarArticuloPersonaje(id, it1, cantidad + 1 )
                    }
                }
                peso = nextPeso
                Toast.makeText(this, "${objeto.getNombre()} ha sido añadido. $nextPeso / ${dbHelper.obtenerPesoMax()} kg restantes", Toast.LENGTH_SHORT).show()
                objeto = listaObjetos.random()
                imagen.setImageResource(objeto.getUri())
            }else Toast.makeText(this, "Superas el peso máximo", Toast.LENGTH_SHORT).show()
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