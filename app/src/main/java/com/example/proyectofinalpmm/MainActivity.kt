package com.example.proyectofinalpmm

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.proyectofinalpmm.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    private val auth: FirebaseAuth = Firebase.auth
    private val isLogin = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
    }
}

fun continuar(isLogin : Boolean){
    if (isLogin){
        login()
    }else {
        registro()
    }
}

fun login() {

    val email = ""
    val password = ""

    if (isValidEmail(email) && isValidPassword(password))
        FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("aplicacion", "logeado correctamente")
                } else {
                    Log.i("aplicacion", "logeado incorrectamente: ${it.exception}")
                }
            }
}

fun registro() {
    val email = ""
    val password = ""
    val confirmPassword = ""

    if (
        isValidEmail(email) &&
        isValidPassword(password) &&
        isSamePassword(
            password,
            confirmPassword
        )
    )
        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("aplicacion", "registrado correctamente")
                } else {
                    Log.i("aplicacion", "registrado incorrectamente: ${it.exception}")
                }
            }
}

fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
fun isValidPassword(pass: String): Boolean = pass.length in 6..50
fun isSamePassword(pass: String, pass2: String): Boolean = pass == pass2