package com.example.proyectofinalpmm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.proyectofinalpmm.base_de_datos.SQLiteHelper
import com.example.proyectofinalpmm.databinding.ActivityMercaderBinding
import com.google.firebase.auth.FirebaseAuth

class MercaderActivity : BaseActivity() {

    private lateinit var binding: ActivityMercaderBinding

    private lateinit var btnComerciar: Button
    private lateinit var btnContinuar: Button
    private lateinit var btnCancelar: Button
    private lateinit var btnComprar: Button
    private lateinit var btnVender: Button

    private lateinit var layoutC: LinearLayout
    private lateinit var layoutV: LinearLayout
    private lateinit var layoutM: LinearLayout
    private lateinit var layoutM2: LinearLayout

    private lateinit var btnComprarObjeto: Button
    private lateinit var btnVenderObjeto: Button
    private lateinit var btnAtrasC: Button
    private lateinit var btnAtrasV: Button

    private lateinit var imgV: ImageView
    private lateinit var imgC: ImageView

    private lateinit var nombreC: TextView
    private lateinit var pesoC: TextView
    private lateinit var precioC: TextView
    private lateinit var nombreV: TextView
    private lateinit var pesoV: TextView
    private lateinit var precioV: TextView

    private lateinit var btnAdelanteItemC: ImageView
    private lateinit var btnAtrasItemC: ImageView

    private lateinit var btnAdelanteItemV: ImageView
    private lateinit var btnAtrasItemV: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMercaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val globalButton = findViewById<ImageView>(R.id.ajustesBoton)
        globalButton.setOnClickListener {
            val intentb = Intent(this, AjustesActivity::class.java)
            startActivity(intentb)
        }

        val auth = FirebaseAuth.getInstance()
        val id = auth.currentUser?.uid
        val dbHelper = SQLiteHelper(this)

        var objetosMercader = id?.let { dbHelper.obtenerArticulosDeMercader(it) }
        Log.i("ayuda", objetosMercader.toString())
        var objetoMActual = 0
        var objetosPersonaje = id?.let { dbHelper.obtenerArticulosDePersonaje(it) }
        Log.i("ayuda", objetosPersonaje.toString())
        var objetoPActual = 0

        btnComerciar = binding.ComerciarBtn
        btnContinuar = binding.ContinuarBtn
        btnCancelar = binding.CancelarBtn
        btnComprar = binding.ComprarBtn
        btnVender = binding.VenderBtn

        layoutC = binding.linearLayoutComprar
        layoutV = binding.linearLayoutVender
        layoutM = binding.mercader
        layoutM2 = binding.mercader2

        btnAtrasC = binding.button14V
        btnAtrasV = binding.button14

        imgC = binding.imageViewV2
        imgV = binding.imageView12

        btnVenderObjeto = binding.button13
        btnComprarObjeto = binding.button13V

        nombreV = binding.textNombre
        pesoV = binding.textPeso
        precioV = binding.textTipo
        nombreC = binding.textNombreV
        pesoC = binding.textPesoV
        precioC = binding.textTipoV

        btnAdelanteItemC = binding.buttonadelanteV
        btnAtrasItemC = binding.buttonatrasV

        btnAdelanteItemV = binding.buttonadelante
        btnAtrasItemV = binding.buttonatras

        btnComerciar.visibility = View.VISIBLE
        btnContinuar.visibility = View.VISIBLE
        btnCancelar.visibility = View.GONE
        btnComprar.visibility = View.GONE
        btnVender.visibility = View.GONE
        layoutC.visibility = View.GONE
        layoutM.visibility = View.GONE
        layoutM2.visibility = View.GONE
        layoutV.visibility = View.GONE

        btnComerciar.setOnClickListener {
            btnComerciar.visibility = View.GONE
            btnContinuar.visibility = View.GONE
            btnCancelar.visibility = View.VISIBLE
            btnComprar.visibility = View.VISIBLE
            btnVender.visibility = View.VISIBLE
        }

        btnContinuar.setOnClickListener {
            startActivity(Intent(this@MercaderActivity, RandomActivity::class.java))
        }

        btnCancelar.setOnClickListener {
            btnComerciar.visibility = View.VISIBLE
            btnContinuar.visibility = View.VISIBLE
            btnCancelar.visibility = View.GONE
            btnComprar.visibility = View.GONE
            btnVender.visibility = View.GONE
        }

        btnComprar.setOnClickListener {
            layoutC.visibility = View.VISIBLE
            layoutM2.visibility = View.VISIBLE
            animateText(
                binding.textView15,
                "Espero que tengas preparado el oro para los excluisivos articulos que te voy a mostrar."
            )
            btnCancelar.visibility = View.GONE
            btnComprar.visibility = View.GONE
            btnVender.visibility = View.GONE
            objetosMercader?.let { setUpC(it, objetoMActual) }
            Log.i("hola", objetosMercader.toString())
        }

        btnVender.setOnClickListener {
            layoutV.visibility = View.VISIBLE
            layoutM.visibility = View.VISIBLE
            animateText(
                binding.textView14,
                "Muestrame que tienes, pero no esperes que pague demasiado por los desechos que me traes que llamas articulos."
            )
            btnCancelar.visibility = View.GONE
            btnComprar.visibility = View.GONE
            btnVender.visibility = View.GONE
            objetosPersonaje?.let { setUpV(it, objetoPActual) }
        }

        btnAtrasC.setOnClickListener {
            layoutC.visibility = View.GONE
            layoutM2.visibility = View.GONE
            btnCancelar.visibility = View.VISIBLE
            btnComprar.visibility = View.VISIBLE
            btnVender.visibility = View.VISIBLE
        }

        btnAtrasV.setOnClickListener {
            layoutV.visibility = View.GONE
            layoutM.visibility = View.GONE
            btnCancelar.visibility = View.VISIBLE
            btnComprar.visibility = View.VISIBLE
            btnVender.visibility = View.VISIBLE
        }

        btnComprarObjeto.setOnClickListener {
            objetosMercader = id?.let { dbHelper.obtenerArticulosDeMercader(it) }
            objetosPersonaje = id?.let { dbHelper.obtenerArticulosDePersonaje(it) }
            val idArticulo = dbHelper.obtenerIdArticuloPorNombre(
                objetosMercader?.get(objetoMActual)?.getNombre().toString()
            )
            val cantidadP = dbHelper.obtenerCantidadArticuloPersonaje(id, idArticulo)
            val cantidadM = dbHelper.obtenerCantidadArticuloMercader(id, idArticulo)
            val peso = dbHelper.obtenerPesoTotalPersonaje(id, objetosPersonaje)
            val monedas = dbHelper.obtenerMonedasTotales(id)

            if (cantidadM > 0) {
                if (peso < dbHelper.obtenerPesoMax()) {
                    val monedasNext =
                        monedas + (objetosMercader?.get(objetoMActual)?.getPrecio() ?: 0)
                    if (monedasNext <= 0) Toast.makeText(
                        this,
                        "No tienes suficientes monedas",
                        Toast.LENGTH_SHORT
                    ).show()
                    else {
                        dbHelper.actualizarArticuloMercader(id, idArticulo, cantidadM - 1)
                        dbHelper.actualizarArticuloPersonaje(
                            id,
                            dbHelper.obtenerIdArticuloPorNombre(Articulos.Nombre.MONEDA.toString()),
                            monedas + (objetosMercader?.get(objetoMActual)
                                ?.getPrecio()
                                ?: 0)
                        )
                        Toast.makeText(
                            this,
                            "- ${
                                objetosMercader?.get(objetoMActual)?.getPrecio()
                            } monedas, ${cantidadM - 1} items restantes",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else Toast.makeText(this, "Superas el peso maximo", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(this, "No hay suficientes items para comprar", Toast.LENGTH_SHORT)
                .show()

            dbHelper.actualizarArticuloPersonaje(id, idArticulo, cantidadP + 1)
        }

        btnVenderObjeto.setOnClickListener {
            objetosMercader = id?.let { dbHelper.obtenerArticulosDeMercader(it) }
            objetosPersonaje = id?.let { dbHelper.obtenerArticulosDePersonaje(it) }
            val idArticulo = dbHelper.obtenerIdArticuloPorNombre(
                objetosPersonaje?.get(objetoPActual)?.getNombre().toString()
            )
            val cantidadP = dbHelper.obtenerCantidadArticuloPersonaje(id, idArticulo)
            val cantidadM = dbHelper.obtenerCantidadArticuloMercader(id, idArticulo)
            val monedas = dbHelper.obtenerMonedasTotales(id)

            if (cantidadP > 0) {
                dbHelper.actualizarArticuloPersonaje(id, idArticulo, cantidadP - 1)
                dbHelper.actualizarArticuloPersonaje(
                    id,
                    dbHelper.obtenerIdArticuloPorNombre(Articulos.Nombre.MONEDA.toString()),
                    monedas + (objetosPersonaje?.get(objetoPActual)
                        ?.getPrecio()
                        ?: 0)
                )
                Toast.makeText(
                    this,
                    "+ ${
                        objetosPersonaje?.get(objetoPActual)?.getPrecio()
                    } monedas añadidas, ${cantidadP - 1} items restantes",
                    Toast.LENGTH_SHORT
                ).show()
            } else Toast.makeText(
                this,
                "No tienes suficientes items para vender",
                Toast.LENGTH_SHORT
            ).show()

            dbHelper.actualizarArticuloMercader(id, idArticulo, cantidadM + 1)
        }

        btnAdelanteItemC.setOnClickListener {
            if (objetosMercader?.isNotEmpty() == true) {
                if (objetoMActual == (objetosMercader?.size?.minus(2) ?: 0)) {
                    objetoMActual = 0
                } else {
                    objetoMActual += 1
                }
                Log.i("ayuda", "$objetoMActual")
                setUpC(objetosMercader!!, objetoMActual)
            }
        }

        btnAtrasItemC.setOnClickListener {
            if (objetosMercader?.isNotEmpty() == true) {
                if (objetoMActual == 0) {
                    objetoMActual = (objetosMercader?.size ?: 0) - 2
                } else {
                    objetoMActual -= 1
                }
                Log.i("ayuda", "$objetoMActual")
                setUpC(objetosMercader!!, objetoMActual)
            }
        }

        btnAdelanteItemV.setOnClickListener {
            if (objetosPersonaje?.isNotEmpty() == true) {
                if (objetosPersonaje?.size!! > 1) {
                    if (objetoPActual == (objetosPersonaje?.size?.minus(2) ?: 0)) {
                        objetoPActual = 0
                    } else {
                        objetoPActual += 1
                    }
                }
                Log.i("ayuda", "$objetoPActual")
                objetosPersonaje?.get(objetoPActual)
                    ?.let { it1 -> imgV.setImageResource(it1.getUri()) }
            }
        }

        btnAtrasItemV.setOnClickListener {
            if (objetosPersonaje?.isNotEmpty() == true) {
                if (objetosPersonaje?.size!! > 1) {
                    if (objetoPActual == 0) {
                        objetoPActual = (objetosPersonaje?.size ?: 0) - 2
                    } else {
                        objetoPActual -= 1
                    }
                } else objetoPActual = 0
                Log.i("ayuda", "$objetoPActual")
                objetosPersonaje?.get(objetoPActual)
                    ?.let { it1 -> imgV.setImageResource(it1.getUri()) }
            }
        }

    }

    private fun setUpV(lista: List<Articulos>, objeto: Int) {
        if (lista.isNotEmpty()) {
            imgV.setImageResource(lista[objeto].getUri())
            nombreV.text = lista[objeto].getNombre().toString()
            val peso = "${lista[objeto].getPeso()} Kg"
            pesoV.text = peso
            val precio = "${lista[objeto].getPeso()} €"
            precioV.text = precio
        }
    }

    private fun setUpC(lista: List<Articulos>, objeto: Int) {
        if (lista.isNotEmpty()) {
            imgC.setImageResource(lista[objeto].getUri())
            nombreC.text = lista[objeto].getNombre().toString()
            val peso = "${lista[objeto].getPeso()} Kg"
            pesoC.text = peso
            val precio = "${lista[objeto].getPeso()} €"
            precioC.text = precio
        }
    }
}