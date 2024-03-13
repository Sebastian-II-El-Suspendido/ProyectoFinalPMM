package com.example.proyectofinalpmm

//ActivityCorrespondiente a MainActivity del ejercicio 1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import com.example.proyectofinalpmm.base_de_datos.SQLiteHelper
import com.example.proyectofinalpmm.databinding.ActivityCreacionPersonajeBinding
import com.example.proyectofinalpmm.musica.Musica
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class CreacionPersonajeActivity : BaseActivity() {
    private lateinit var boton: Button
    private lateinit var spinnerRaza: Spinner
    private lateinit var spinnerClase: Spinner
    private lateinit var spinnerEstadoVital: Spinner
    private lateinit var editTextNombre: EditText
    private lateinit var imagen: ImageView
    private lateinit var binding: ActivityCreacionPersonajeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreacionPersonajeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupSpinner()

        val modificar = intent.getBooleanExtra("modificar", false)

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser?.uid
        val dbHelper = SQLiteHelper(this)
        val personaje = user?.let { dbHelper.obtenerPersonajePorID(it, this) }
        val mercader = user?.let { dbHelper.obtenerMercaderPorId(it) }
        Log.i("ayuda", mercader.toString())
        if (personaje != null) {
            if (!modificar) {
                val musica = user.let { dbHelper.obtenerMusicaorID(it, this) }
                if (musica != 0) {
                    val intent = Intent(this@CreacionPersonajeActivity, Musica::class.java)
                    intent.action = Musica.ACTION_START_MUSIC
                    startService(intent)
                } else {
                    val intent = Intent(this@CreacionPersonajeActivity, Musica::class.java)
                    intent.action = Musica.ACTION_STOP_MUSIC
                    startService(intent)
                }
                startActivity(
                    Intent(
                        this@CreacionPersonajeActivity,
                        PersonajeCreadoActivity::class.java
                    )
                )
            }
        } else {
            dbHelper.iniciarArticulos()
            dbHelper.iniciarPersonajes(this)
            if (mercader == false) {
                dbHelper.insertarMercader(user)
                dbHelper.meterArticulosMercader(user)
            }
        }

        val globalButton = findViewById<ImageView>(R.id.ajustesBoton)
        globalButton.setOnClickListener {
            val intentb = Intent(this, AjustesActivity::class.java)
            startActivity(intentb)
        }

        editTextNombre = findViewById(R.id.nombreEditText)
        spinnerRaza = findViewById(R.id.spinnerRaza)
        spinnerClase = findViewById(R.id.spinnerClase)
        spinnerEstadoVital = findViewById(R.id.spinnerEstadoVital)
        imagen = findViewById(R.id.imagen)

        boton = findViewById(R.id.botonCrearPersonaje)
        boton.setOnClickListener {
            val valido = (
                    editTextNombre.text.isNotEmpty() &&
                            spinnerRaza.selectedItemId.toInt() != 0 &&
                            spinnerClase.selectedItemId.toInt() != 0 &&
                            spinnerEstadoVital.selectedItemId.toInt() != 0)

            if (valido) {
                val raza = when (spinnerRaza.selectedItemId.toInt()) {
                    1 -> PersonajeP.Raza.Humano
             //       2 -> PersonajeP.Raza.
                    3 -> PersonajeP.Raza.Elfo
                    4 -> PersonajeP.Raza.Enano
                    5 -> PersonajeP.Raza.Maldito
                    else -> PersonajeP.Raza.Humano
                }

                val clase = when (spinnerClase.selectedItemId.toInt()) {
                    1 -> PersonajeP.Clase.Guerrero
                    else -> PersonajeP.Clase.Brujo
                }

                val estadoVital = when (spinnerEstadoVital.selectedItemId.toInt()) {
                    1 -> PersonajeP.EstadoVital.Anciano
                    2 -> PersonajeP.EstadoVital.Adulto
                    3 -> PersonajeP.EstadoVital.Joven
                    else -> PersonajeP.EstadoVital.Anciano
                }

                val personajeP =
                    PersonajeP(editTextNombre.text.toString(), raza, clase, estadoVital, this)
                if (!modificar)
                    dbHelper.insertarPersonaje(personajeP, auth.currentUser?.uid ?: "")
                else dbHelper.modificarPersonaje(personajeP, auth.currentUser?.uid ?: "")
                intent = Intent(this@CreacionPersonajeActivity, PersonajeCreadoActivity::class.java)
                startActivity(intent)
            } else {
                Snackbar.make(
                    findViewById(R.id.mainXML),
                    R.string.enviarMensaje,
                    Snackbar.LENGTH_LONG
                ).show()
            }

            binding.button3.setOnClickListener {
                val intent2 = Intent(this, PersonajeCreadoActivity::class.java)
                startActivity(intent2)
            }

        }

        spinnerRaza.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                cambiarImagen()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerClase.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                cambiarImagen()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerEstadoVital.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                cambiarImagen()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.spinnerClase,
            R.layout.customspinner
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.customspinner)
            // Aplicar el adapter al spinner
        }
    }

    fun cambiarImagen() {

        val raza = spinnerRaza.selectedItem.toString().lowercase()
        val clase = spinnerClase.selectedItem.toString().lowercase()
        val estadoVital = spinnerEstadoVital.selectedItem.toString().lowercase()
        val sentencia = "${clase}_${raza}_${estadoVital}"
        val imagend = when (sentencia){
            /*
            "brujo_humano_anciano" -> R.drawable.brujo_humano_anciano
            "brujo_humano_adulto" -> R.drawable.brujo_humano_adulto
            "brujo_humano_joven" -> R.drawable.brujo_humano_joven
            "brujo_elfo_anciano" -> R.drawable.brujo_elfo_anciano
            "brujo_elfo_adulto" -> R.drawable.brujo_elfo_adulto
            "brujo_elfo_joven" -> R.drawable.brujo_elfo_joven
            "brujo_enano_anciano" -> R.drawable.brujo_enano_anciano
            "brujo_enano_adulto" -> R.drawable.brujo_enano_adulto
            "brujo_enano_joven" -> R.drawable.brujo_enano_joven
            "brujo_madito_anciano" -> R.drawable.brujo_maldito_anciano
            "brujo_madito_adulto" -> R.drawable.brujo_maldito_adulto
            "brujo_madito_joven" -> R.drawable.brujo_maldito_joven
            "mago_humano_anciano" -> R.drawable.mago_humano_anciano
            "mago_humano_adulto" -> R.drawable.mago_humano_adulto
            "mago_humano_joven" -> R.drawable.mago_humano_joven
            "mago_elfo_anciano" -> R.drawable.mago_elfo_anciano
            "mago_elfo_adulto" -> R.drawable.mago_elfo_adulto
            "mago_elfo_joven" -> R.drawable.mago_elfo_joven
            "mago_enano_anciano" -> R.drawable.mago_enano_anciano
            "mago_enano_adulto" -> R.drawable.mago_enano_adulto
            "mago_enano_joven" -> R.drawable.mago_enano_joven
            "mago_madito_anciano" -> R.drawable.mago_maldito_anciano
            "mago_madito_adulto" -> R.drawable.mago_maldito_adulto
            "mago_madito_joven" -> R.drawable.mago_maldito_joven
             */
            "guerrero_humano_anciano" -> R.drawable.guerrero_humano_anciano
            "guerrero_humano_adulto" -> R.drawable.guerrero_humano_adulto
            "guerrero_humano_joven" -> R.drawable.guerrero_humano_joven
            "guerrero_elfo_anciano" -> R.drawable.guerrero_elfo_anciano
            "guerrero_elfo_adulto" -> R.drawable.guerrero_elfo_adulto
            "guerrero_elfo_joven" -> R.drawable.guerrero_elfo_joven
            "guerrero_enano_anciano" -> R.drawable.guerrero_enano_anciano
            "guerrero_enano_adulto" -> R.drawable.guerrero_enano_adulto
            "guerrero_enano_joven" -> R.drawable.guerrero_enano_joven
            "guerrero_maldito_anciano" -> R.drawable.guerrero_maldito_anciano
            "guerrero_maldito_adulto" -> R.drawable.guerrero_maldito_adulto
            "guerrero_maldito_joven" -> R.drawable.guerrero_maldito_joven
            "guerrero_orco_anciano" -> R.drawable.guerrero_orco_anciano
            "guerrero_orco_adulto" -> R.drawable.guerrero_orco_adulto
            "guerrero_orco_joven" -> R.drawable.guerrero_orco_joven
            else -> {R.drawable.personajesinselect}
        }
        imagen.setImageResource(imagend)


    }
}