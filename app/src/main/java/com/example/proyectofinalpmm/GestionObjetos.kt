package com.example.proyectofinalpmm

//Activity Correpondiente a la MainActivity del Ejercicio3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyectofinalpmm.databinding.ActivityGestionObjetosBinding

class GestionObjetos : BaseActivity() {

    private lateinit var binding: ActivityGestionObjetosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGestionObjetosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mochila = Mochila(100)
        mochila.addArticulo(Articulos(Articulos.TipoArticulo.ORO, Articulos.Nombre.MONEDA, 0, 15, 0, 10))

        val dbHelper = ObjetosAleatorios(this)
        val listaArticulos = dbHelper.getArticulos()
        dbHelper.close()

        val imagenObjeto = binding.imagenObjeto
        var objeto = listaArticulos.random()
        imagenObjeto.setImageResource(objeto.getUri())

        binding.RecogerBtn.setOnClickListener {
            if (mochila.getPesoMochila() - objeto.getPeso() > 0){
                mochila.addArticulo(objeto)
                Toast.makeText(this, "${objeto.getNombre()} + 1\n Peso restante: ${mochila.getPesoMochila()}", Toast.LENGTH_SHORT).show()
                objeto = listaArticulos.random()
                imagenObjeto.setImageResource(objeto.getUri())
            }else{
                Toast.makeText(this, "Has superado el peso máximo de tu mochila", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ContinuarBtn.setOnClickListener {
            intent = Intent(this, MercaderActivity::class.java)
            intent.putExtra("mochila", mochila)
            startActivity(intent)
        }

    }
}