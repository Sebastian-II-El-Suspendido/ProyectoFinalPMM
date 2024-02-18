package com.example.proyectofinalpmm

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random
import kotlin.random.nextInt

class EnemigoActivity : BaseActivity() {
    private lateinit var exploision1: ImageView
    private lateinit var  exploision2: ImageView
    private lateinit var exploision3: ImageView
    private lateinit var exploision4: ImageView
    private lateinit var calavera: ImageView
    private lateinit var fondomuerto: ImageView
    private lateinit var imgEnemigo: ImageView
    private lateinit var fondovivo: ImageView
    private lateinit var imgvictorioso: ImageView
    private lateinit var img: ImageView
    private lateinit var textito: TextView
    private var enemigo: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enemigo)
        textito= findViewById(R.id.textView12)
         imgEnemigo = findViewById(R.id.imgEnemigo)
        img= findViewById(R.id.imgEnemigoDragon)
        fondovivo= findViewById(R.id.Ganado)
        imgvictorioso= findViewById(R.id.PersonajeVictorioso)

        val num = Random.nextInt(0..10)
        textito.visibility=View.GONE
        img.visibility= View.GONE
        imgEnemigo.visibility= View.GONE
        imgvictorioso.visibility = View.GONE
        fondovivo.visibility= View.GONE


         val enemigoArray = arrayOf(
            R.drawable.enemigo1,
            R.drawable.enemigo2,
            R.drawable.enemigo3,
             R.drawable.enemigo4,
             R.drawable.enemigo5,
             R.drawable.enemigo6,
             R.drawable.enemigo7,
             R.drawable.enemigo8,
             R.drawable.enemigo9,
             R.drawable.enemigo10,
             R.drawable.enemigodragon
        )



         enemigo = enemigoArray[num]

        imgEnemigo.setImageResource(enemigo!!)
        if (enemigo == R.drawable.enemigodragon) {
            img.visibility = View.VISIBLE
            imgEnemigo.visibility = View.GONE
        } else {
            img.visibility = View.GONE
            imgEnemigo.visibility = View.VISIBLE
        }



        exploision1 = findViewById(R.id.gifex1)
        exploision2= findViewById(R.id.gifex2)
        exploision3= findViewById(R.id.gifex3)
        exploision4= findViewById(R.id.gifex4)
        calavera= findViewById(R.id.gifCalavera)
        fondomuerto = findViewById(R.id.Perdido)

       calavera.visibility= View.GONE
        fondomuerto.visibility=View.GONE
        exploision1.visibility= View.GONE
        exploision2.visibility= View.GONE
        exploision3.visibility= View.GONE
        exploision4.visibility= View.GONE




        val btnRecoger = findViewById<Button>(R.id.btnLuchar)
        val btnContinuarObjeto = findViewById<Button>(R.id.btnHuir)


        val overlayView = findViewById<View>(R.id.blackOverlay)
        overlayView.visibility = View.VISIBLE // Haz visible la vista de superposición



        //Boton luchar
        btnRecoger.setOnClickListener {
            val intent = Intent(this, TobeContinuedActivity::class.java)
            animateFadeToBlackAndBack(overlayView)

        }



        //Boton Huir
        btnContinuarObjeto.setOnClickListener {
            val intent = Intent(this, RandomActivity::class.java)
            startActivity(intent)
        }
    }


    private fun animateFadeToBlackAndBack(overlay: View) {
        overlay.visibility = View.VISIBLE
        // Comienza el fundido a negro
        overlay.animate().alpha(1f).setDuration(2000)
            .withEndAction {
                exploision1.visibility = View.VISIBLE
                exploision2.visibility = View.VISIBLE
                exploision3.visibility = View.VISIBLE
                exploision4.visibility = View.VISIBLE

                // Delay para mantener las explosiones visibles por un tiempo
                Handler(Looper.getMainLooper()).postDelayed({
                    // Hace las imágenes de explosión invisibles
                    exploision1.visibility= View.INVISIBLE
                    exploision2.visibility= View.INVISIBLE
                    exploision3.visibility= View.INVISIBLE
                    exploision4.visibility= View.INVISIBLE
                    if (enemigo == R.drawable.enemigodragon) {
                        fondomuerto.visibility = View.VISIBLE
                        calavera.visibility = View.VISIBLE
                    }else{
                        fondovivo.visibility= View.VISIBLE
                        imgvictorioso.visibility= View.VISIBLE
                        textito.visibility= View.VISIBLE
                        animateText(textito,"La batalla ha terminado y sales victorioso")
                    }
                    //else{ GANASTE !?



                    // Inmediatamente después de hacer invisibles las explosiones, comienza el desvanecimiento
                    overlay.animate().alpha(0f).setDuration(2000)
                        .withEndAction {

                            overlay.visibility = View.GONE


                            // Inicia la nueva Activity aquí para asegurar que la animación haya concluido
                            val intent = Intent(this@EnemigoActivity, TobeContinuedActivity::class.java)
                           // startActivity(intent)
                        }.start() // Asegúrate de iniciar esta animación
                }, 5000) // Este delay determina cuánto tiempo después de la animación de fundido a negro se ejecutan las acciones
            }.start() // Inicia la animación de fundido a negro
    }

}





