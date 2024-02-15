package com.example.proyectofinalpmm

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class SplashScreenActivity : BaseActivity() {

    private lateinit var animationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        animationView = findViewById(R.id.animationView)

        // Listener para cuando termine la animación
        animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Aquí puedes manejar si necesitas algo justo cuando inicie la animación
            }

            override fun onAnimationEnd(animation: Animator) {
                goToMainActivity()
            }

            override fun onAnimationCancel(animation: Animator) {
                // Manejar cancelación si es necesario
            }

            override fun onAnimationRepeat(animation: Animator) {
                // No necesitamos manejar repeticiones porque la animación no debe repetirse
            }
        })

        // Si quieres asegurarte de que la pantalla de bienvenida se muestre por un mínimo de tiempo, puedes usar un Handler
        Handler().postDelayed({

                goToMainActivity()

        }, 2000) // 3000ms es el tiempo mínimo que la splash screen estará visible
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
