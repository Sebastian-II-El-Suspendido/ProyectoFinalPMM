package com.example.proyectofinalpmm

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.proyectofinalpmm.base_de_datos.SQLiteHelper
import com.example.proyectofinalpmm.databinding.ActivityMainBinding
import com.example.proyectofinalpmm.musica.Musica
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    // Inicio
    private lateinit var jugar: Button

    // Login
    private lateinit var conjunto: LinearLayout
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var logInButton: Button
    private lateinit var toSignUpButton: TextView

    // Registro
    private lateinit var conjunto2: LinearLayout
    private lateinit var emailFieldReg: EditText
    private lateinit var passwordFieldReg: EditText
    private lateinit var comfirmPasswordFieldReg: EditText
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val dbHelper = SQLiteHelper(this)
        //dbHelper.borrarBaseDeDatos(this)

        // Inicio
        jugar = binding.button
        // Login
        conjunto = binding.conjunto
        emailField = binding.editTextEmail
        passwordField = binding.editTextPassword
        logInButton = binding.buttonLogIn
        toSignUpButton = binding.btnToSignUp

        // Registro
        conjunto2 = binding.conjunto2
        emailFieldReg = binding.editTextEmailReg
        passwordFieldReg = binding.editTextPasswordReg
        comfirmPasswordFieldReg = binding.editTextConfirmPasswordReg
        signUpButton = binding.buttonSignUp

        conjunto.visibility = View.GONE
        conjunto2.visibility = View.GONE
        jugar.visibility = View.VISIBLE

        // Musica
        val musica = Intent(this@MainActivity, Musica::class.java)
        val serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder = service as Musica.MusicBinder
                val musicService = binder.getService()

                musicService.startMusic()
            }

            override fun onServiceDisconnected(name: ComponentName?) {
            }
        }

        bindService(musica, serviceConnection, BIND_AUTO_CREATE)

        jugar.setOnClickListener {
            if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
                login()
            else {
                startActivity(
                    Intent(
                        this@MainActivity,
                        CreacionPersonajeActivity::class.java
                    )
                )
            }
        }

        binding.button2.setOnClickListener {
            intent = Intent(this, CreacionPersonajeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        conjunto.visibility = View.VISIBLE
        jugar.visibility = View.GONE

        logInButton.setOnClickListener {
            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            if (isValidEmail(email) && isValidPassword(password))
                FirebaseAuth
                    .getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.i("aplicacion", "logeado correctamente")
                            Toast.makeText(this, "logueado correctamente", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    CreacionPersonajeActivity::class.java
                                )
                            )
                        } else {
                            Log.i("aplicacion", "logeado incorrectamente: ${it.exception}")
                            Toast.makeText(this, "error al loguearse", Toast.LENGTH_SHORT).show()
                        }
                    }
            else {
                if (isValidEmail(email)) Toast.makeText(this, "Email no valido", Toast.LENGTH_SHORT)
                    .show()
                else
                    if (isValidPassword(password)) Toast.makeText(
                        this,
                        "contraseña no valida",
                        Toast.LENGTH_SHORT
                    ).show()
                    else Toast.makeText(this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show()
            }
        }

        toSignUpButton.setOnClickListener {
            registro()
        }
    }

    private fun registro() {
        conjunto.visibility = View.GONE
        conjunto2.visibility = View.VISIBLE

        signUpButton.setOnClickListener {
            val email = emailFieldReg.text.toString()
            val password = passwordFieldReg.text.toString()
            val confirmPassword = comfirmPasswordFieldReg.text.toString()

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
                            Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    CreacionPersonajeActivity::class.java
                                )
                            )
                        } else {
                            Log.i("aplicacion", "error al registrar")
                            Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            else {
                if (isValidEmail(email)) Toast.makeText(this, "Email no valido", Toast.LENGTH_SHORT)
                    .show()
                else
                    if (isValidPassword(password)) Toast.makeText(
                        this,
                        "contraseña no valida",
                        Toast.LENGTH_SHORT
                    ).show()
                    else if (isSamePassword(password, confirmPassword)) Toast.makeText(
                        this,
                        "No pones la misma contraseña",
                        Toast.LENGTH_SHORT
                    ).show()
                    else Toast.makeText(this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidEmail(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(pass: String): Boolean = pass.length in 6..50
    private fun isSamePassword(pass: String, pass2: String): Boolean = pass == pass2
}