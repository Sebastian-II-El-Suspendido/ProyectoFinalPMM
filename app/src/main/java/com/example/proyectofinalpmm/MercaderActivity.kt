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
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalpmm.base_de_datos.SQLiteHelper
import com.example.proyectofinalpmm.databinding.ActivityMercaderBinding
import com.google.firebase.auth.FirebaseAuth


class MercaderActivity : BaseActivity() {

    private lateinit var binding: ActivityMercaderBinding
    private lateinit var listaArticulos: List<Articulos>
    private var currentIndex = 0

    private lateinit var btnComerciar: Button
    private lateinit var btnContinuar: Button
    private lateinit var btnCancelar: Button
    private lateinit var btnComprar: Button
    private lateinit var btnVender: Button

    private lateinit var layoutC : LinearLayout
    private lateinit var layoutV : LinearLayout
    private lateinit var layoutM : LinearLayout
    private lateinit var layoutM2: LinearLayout

    private lateinit var btnComprarObjeto : Button
    private lateinit var btnVenderObjeto : Button
    private lateinit var btnAtrasC : Button
    private lateinit var btnAtrasV : Button

    private lateinit var imgV : ImageView
    private lateinit var imgC : ImageView

    private lateinit var nombreC : TextView
    private lateinit var nombreV : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMercaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val auth = FirebaseAuth.getInstance()
        val id = auth.currentUser?.uid
        val dbHelper = SQLiteHelper(this)
        var objetosMercader = id?.let { dbHelper.obtenerArticulosDeMercader(it) }
        var objetoMActual = 0
        var objetosPersonaje = id?.let { dbHelper.obtenerArticulosDePersonaje(it) }
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

        imgC = binding.imageView12
        imgV = binding.imageViewV2

        btnVenderObjeto = binding.button13V
        btnComprarObjeto = binding.button13

        nombreC = binding.textNombre
        nombreV = binding.textNombreV

        btnComerciar.visibility = View.VISIBLE
        btnContinuar.visibility = View.VISIBLE
        btnCancelar.visibility = View.GONE
        btnComprar.visibility = View.GONE
        btnVender.visibility = View.GONE
        layoutC.visibility = View.GONE
        layoutM.visibility= View.GONE
        layoutM2.visibility= View.GONE
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
            animateText(binding.textView15,"Espero que tengas preparado el oro para los excluisivos articulos que te voy a mostrar.")
            btnCancelar.visibility = View.GONE
            btnComprar.visibility = View.GONE
            btnVender.visibility = View.GONE
            objetosMercader?.let { setUpC(it) }
            Log.i("hola", objetosMercader.toString())
        }

        btnVender.setOnClickListener {
            layoutV.visibility = View.VISIBLE
            layoutM.visibility= View.VISIBLE
            animateText(binding.textView14,"Muestrame que tienes, pero no esperes que pague demasiado por los desechos que me traes que llamas articulos.")
            btnCancelar.visibility = View.GONE
            btnComprar.visibility = View.GONE
            btnVender.visibility = View.GONE
            objetosPersonaje?.let { setUpV(it) }
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
            layoutM.visibility= View.GONE
            btnCancelar.visibility = View.VISIBLE
            btnComprar.visibility = View.VISIBLE
            btnVender.visibility = View.VISIBLE
        }

        btnComprarObjeto.setOnClickListener {
            objetosMercader = id?.let { dbHelper.obtenerArticulosDeMercader(it) }
            objetosPersonaje = id?.let { dbHelper.obtenerArticulosDePersonaje(it) }
            val idArticulo = dbHelper.obtenerIdArticuloPorNombre(
                objetosMercader?.get(objetoMActual)?.getNombre().toString())
            val cantidad = id?.let { it1 -> idArticulo?.let { it2 ->
                dbHelper.obtenerCantidadArticuloMercader(it1,
                    it2
                )
            } }
            if (id != null) {
                if (cantidad != null) {
                    dbHelper.actualizarArticuloMercader(id, cantidad, cantidad - 1)
                    if (objetosPersonaje?.contains(objetosMercader?.get(objetoMActual)) == true)
                        idArticulo?.let { it1 ->
                            dbHelper.actualizarArticuloPersonaje(id,
                                it1, cantidad)
                        }
                    else idArticulo?.let { it1 -> dbHelper.agregarArticuloPersonaje(id, it1, 1) }
                    Toast.makeText(this, "articulo comprado - ${
                        objetosPersonaje?.get(objetoPActual)?.getPrecio()
                    } coins", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnVenderObjeto.setOnClickListener {
            objetosMercader = id?.let { dbHelper.obtenerArticulosDeMercader(it) }
            objetosPersonaje = id?.let { dbHelper.obtenerArticulosDePersonaje(it) }
            val idArticulo = dbHelper.obtenerIdArticuloPorNombre(
                objetosPersonaje?.get(objetoPActual)?.getNombre().toString())
            val cantidad = id?.let { it1 -> idArticulo?.let { it2 ->
                dbHelper.obtenerCantidadArticuloMercader(it1,
                    it2
                )
            } }
            if (id != null) {
                if (cantidad != null) {
                    if (idArticulo != null) {
                        dbHelper.actualizarArticuloPersonaje(id, idArticulo, cantidad - 1)
                    }
                    if (objetosMercader?.contains(objetosPersonaje?.get(objetoPActual)) == true)
                        idArticulo?.let { it1 ->
                            dbHelper.actualizarArticuloMercader(id,
                                it1, cantidad)
                        }
                    else idArticulo?.let { it1 -> dbHelper.agregarArticuloMercader(id, it1, 1) }
                    Toast.makeText(this, "articulo vendido + ${objetosMercader?.get(objetoMActual)
                        ?.getPrecio()} coins", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun setUpV(lista : List<Articulos>){
        if (lista.isEmpty()){

        }else {
            imgV.setImageResource(lista[0].getUri())
            nombreV.text = lista[0].getNombre().toString()
        }
    }

    fun setUpC(lista : List<Articulos>){
        if (lista.isEmpty()){

        }else {
            imgC.setImageResource(lista[0].getUri())
            nombreC.text = lista[0].getNombre().toString()
        }
    }
}