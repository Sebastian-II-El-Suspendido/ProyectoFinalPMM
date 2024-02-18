package com.example.proyectofinalpmm

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectofinalpmm.databinding.ActivityRandomBinding
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlin.random.Random

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


        val btnDado = findViewById<Button>(R.id.btnDado)
        btnDado.setOnClickListener {
            RandomActivityFun()
        }
    }




    private fun RandomActivityFun() {
        val actividadAleatoria = Random.nextInt(4)
        val animation = binding.animationView3
        animation.playAnimation()

        Handler(Looper.getMainLooper()).postDelayed({
            if (!animation.isAnimating){
                val intent = when (actividadAleatoria) {
                    0 -> Intent(this, ObjetoActivity::class.java)
                    1 -> Intent(this, CiudadActivity::class.java)
                    2 -> Intent(this, MercaderActivity::class.java)
                    else -> Intent(this, EnemigoActivity::class.java)
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
            // Agrega el siguiente carácter al TextView
            textView.text = text.substring(0, index++)

            if (index <= text.length) {
                // Posterga la ejecución del mismo bloque con un delay para el siguiente carácter
                handler.postDelayed(this, 150) // 150ms de delay para cada carácter
            }
        }
    }

    // Inicia el proceso
    handler.post(runnable)
}


/*






 */