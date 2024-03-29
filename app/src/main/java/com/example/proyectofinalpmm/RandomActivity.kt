package com.example.proyectofinalpmm

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalpmm.databinding.ActivityRandomBinding
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.random.nextInt

//ActivityCorrespondiente al MainActivity del ejercicio 2

class RandomActivity : BaseActivity() {
    private lateinit var binding: ActivityRandomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateText(binding.txtDescripcion,"Comienza la aventura, pulsa el DADO para avanzar. CUIDADO puedes encontrarte enemigos")

        binding.chatBot.setOnClickListener {
            val intent = Intent(this, ChatBotActivity::class.java)
            startActivity(intent)
        }


        binding.enemigo.setOnClickListener {
            val intent = Intent(this, EnemigoActivity::class.java)
            startActivity(intent)
        }

        binding.button9.setOnClickListener {
            val intent = Intent(this, MercaderActivity::class.java)
            startActivity(intent)
        }
        binding.button10.setOnClickListener{
            val intent = Intent(this, CiudadActivity::class.java)
            startActivity(intent)
        }
        binding.button11.setOnClickListener {
            val intent = Intent(this, ObjetoActivity::class.java)
            startActivity(intent)
        }


        val globalButton = findViewById<ImageView>(R.id.ajustesBoton)
        globalButton.setOnClickListener {
            val intentb = Intent(this,AjustesActivity::class.java)
            startActivity(intentb)
        }

        val btnDado = findViewById<Button>(R.id.btnDado)
        btnDado.setOnClickListener {
            RandomActivityFun()
        }
    }

    private fun RandomActivityFun() {
        val actividadAleatoria = Random.nextInt(0..3)
        val animation = binding.animationView3
        animation.playAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            if (!animation.isAnimating){
                val intent = when (actividadAleatoria) {
                    0 -> Intent(this@RandomActivity, ObjetoActivity::class.java)
                    1 -> Intent(this@RandomActivity, CiudadActivity::class.java)
                    2 -> Intent(this@RandomActivity, MercaderActivity::class.java)
                    else -> Intent(this@RandomActivity, EnemigoActivity::class.java)
                }
                startActivity(intent)
            }
        }, 4000)



    }

}

/*
    val intent = when (actividadAleatoria) {
            0 -> Intent(this, ObjetoActivity::class.java)
            1 -> Intent(this, CiudadActivity::class.java)
            2 -> Intent(this, MercaderActivity::class.java)
            else -> Intent(this, EnemigoActivity::class.java)
        }



 */
fun animateText(textView: TextView, text: String) {
    val handler = Handler(Looper.getMainLooper())
    val runnable = object : Runnable {
        var index = 0
        override fun run() {
            textView.text = text.substring(0, index++)
            if (index <= text.length) {
                handler.postDelayed(this, 150)
            }
        }
    }
    handler.post(runnable)
}


/*






 */