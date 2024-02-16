package com.example.proyectofinalpmm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//ActivityCorrespondiente a MainActivity del ejercicio 1
import android.content.Intent

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar

class CreacionPersonajeActivity : BaseActivity() {
    private lateinit var boton : Button
    private lateinit var spinnerRaza : Spinner
    private lateinit var spinnerClase : Spinner
    private lateinit var spinnerEstadoVital : Spinner
    private lateinit var editTextNombre : EditText
    private lateinit var imagen : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creacion_personaje)
        setupSpinner()

        editTextNombre = findViewById(R.id.nombreEditText)
        spinnerRaza = findViewById(R.id.spinnerRaza)
        spinnerClase = findViewById(R.id.spinnerClase)
        spinnerEstadoVital = findViewById(R.id.spinnerEstadoVital)
        imagen = findViewById(R.id.imagen)

        boton = findViewById(R.id.botonCrearPersonaje)
        boton.setOnClickListener{
            val valido = (editTextNombre.text.length >= 3 && editTextNombre.text.isNotEmpty()) &&
                    spinnerRaza.selectedItemId.toInt() != 0 &&
                    spinnerClase.selectedItemId.toInt() != 0 &&
                    spinnerEstadoVital.selectedItemId.toInt() != 0

            if (valido){
                val raza = when(spinnerRaza.selectedItemId.toInt()){
                    1 -> Personaje.Raza.Humano
                    2 -> Personaje.Raza.Elfo
                    3 -> Personaje.Raza.Enano
                    4 -> Personaje.Raza.Maldito
                    else -> Personaje.Raza.Humano
                }

                val clase = when(spinnerClase.selectedItemId.toInt()){
                    1 -> Personaje.Clase.Brujo
                    2 -> Personaje.Clase.Mago
                    3 -> Personaje.Clase.Guerrero
                    else -> Personaje.Clase.Brujo
                }

                val estadoVital = when(spinnerEstadoVital.selectedItemId.toInt()){
                    1 -> Personaje.EstadoVital.Anciano
                    2 -> Personaje.EstadoVital.Adulto
                    3 -> Personaje.EstadoVital.Joven
                    else -> Personaje.EstadoVital.Anciano
                }

                val p = Personaje(editTextNombre.text.toString(), raza, clase, estadoVital)
                intent = Intent(this@CreacionPersonajeActivity, PersonajeCreadoActivity::class.java)
                intent.putExtra("personaje", p)
                startActivity(intent)
            }else {
                Snackbar.make(
                    findViewById(R.id.mainXML),
                    R.string.enviarMensaje,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        spinnerRaza.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                cambiarImagen()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerClase.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                cambiarImagen()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerEstadoVital.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                cambiarImagen()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


    private fun setupSpinner() {
        // Crear un ArrayAdapter usando el array de strings y un spinner layout predeterminado
        ArrayAdapter.createFromResource(
            this,
            R.array.spinnerClase,
            R.layout.customspinner
        ).also { adapter ->
            // Especificar el layout a usar cuando la lista de opciones aparece
            adapter.setDropDownViewResource(R.layout.customspinner)
            // Aplicar el adapter al spinner

        }
    }

    fun cambiarImagen(){
        /*
        val raza = spinnerRaza.selectedItem.toString().lowercase()
        val clase = spinnerClase.selectedItem.toString().lowercase()
        val estadoVital = spinnerEstadoVital.selectedItem.toString().lowercase()
        val sentencia = "${clase}_${raza}_${estadoVital}"
        val imagend = when (sentencia){
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
            "guerrero_humano_anciano" -> R.drawable.guerrero_humano_anciano
            "guerrero_humano_adulto" -> R.drawable.guerrero_humano_adulto
            "guerrero_humano_joven" -> R.drawable.guerrero_humano_joven
            "guerrero_elfo_anciano" -> R.drawable.guerrero_elfo_anciano
            "guerrero_elfo_adulto" -> R.drawable.guerrero_elfo_adulto
            "guerrero_elfo_joven" -> R.drawable.guerrero_elfo_joven
            "guerrero_enano_anciano" -> R.drawable.guerrero_enano_anciano
            "guerrero_enano_adulto" -> R.drawable.guerrero_enano_adulto
            "guerrero_enano_joven" -> R.drawable.guerrero_enano_joven
            "guerrero_madito_anciano" -> R.drawable.guerrero_maldito_anciano
            "guerrero_madito_adulto" -> R.drawable.guerrero_maldito_adulto
            "guerrero_madito_joven" -> R.drawable.guerrero_maldito_joven
            else -> {R.drawable.default_humano_adulto}
        }
        imagen.setImageResource(imagend)

         */
    }
}