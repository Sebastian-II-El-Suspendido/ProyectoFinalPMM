package com.example.proyectofinalpmm
import java.io.Serializable

class Personaje(
    private var nombre: String,
    private val raza: Raza,
    private var clase: Clase,
    private var estadoVital: EstadoVital
): Serializable {
    private var salud: Int = 0
    private var ataque: Int = 0
    private var experiencia: Int
    private var nivel: Int
    private var suerte: Int
    private var defensa: Int = 0

    // Enumeración para el tipo de raza y clase
    enum class Raza { Humano, Elfo, Enano, Maldito }
    enum class Clase { Brujo, Mago, Guerrero }
    enum class EstadoVital{Anciano, Joven, Adulto}

    // Inicialización de los atributos tras la construcción del objeto Personaje
    init {
        calcularSalud()
        calcularAtaque()
        calcularDefensa()
        experiencia = 0
        nivel = 1
        suerte = (0..10).random() // Asigna un valor de suerte aleatorio entre 0 y 10
    }

    fun getNombre(): String {
        return nombre
    }
    fun setNombre(nuevoNombre: String) {
        nombre = nuevoNombre
    }
    fun getRaza(): Raza {
        return raza
    }
    fun getSalud(): Int {
        return salud
    }
    fun setSalud(nuevaSalud: Int) {
        salud = nuevaSalud
    }
    fun getAtaque(): Int {
        return ataque
    }
    fun setAtaque(nuevoAtaque: Int) {
        ataque = nuevoAtaque
    }
    fun getClase(): Clase {
        return clase
    }
    fun setClase(nuevaClase: Clase) {
        clase = nuevaClase
    }
    fun getEstadoVital(): EstadoVital {
        return estadoVital
    }
    fun setEstadoVital(nuevoEstadoVital: EstadoVital) {
        estadoVital = nuevoEstadoVital
    }
    fun getExperiencia(): Int {
        return experiencia
    }
    fun setExperiencia(experienciaGanada: Int) {
        experiencia += experienciaGanada
        while (experiencia >= 1000) {
            subirNivel()
            experiencia -= 1000 // Reducir la experiencia en 1000 al subir de nivel
        }
    }
    fun getNivel(): Int {
        return nivel
    }
    fun subirNivel() {
        if (nivel < 10) { // Limitar el nivel a 10
            nivel++
            calcularSalud() // Calcular el nuevo valor de salud al subir de nivel
            calcularAtaque() // Calcular el nuevo valor de ataque al subir de nivel
            calcularDefensa()
        }
    }
    private fun calcularSalud() {
        salud = when (nivel) {
            1 -> 100
            2 -> 200
            3 -> 300
            4 -> 450
            5 -> 600
            6 -> 800
            7 -> 1000
            8 -> 1250
            9 -> 1500
            10 -> 2000
            else -> 100 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }

    private fun calcularAtaque() {
        ataque = when (nivel) {
            1 -> 10
            2 -> 20
            3 -> 25
            4 -> 30
            5 -> 40
            6 -> 100
            7 -> 200
            8 -> 350
            9 -> 400
            10 -> 450
            else -> 10 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }
    private fun calcularDefensa() {
        defensa = when (nivel) {
            1 -> 4
            2 -> 9
            3 -> 14
            4 -> 19
            5 -> 49
            6 -> 59
            7 -> 119
            8 -> 199
            9 -> 349
            10 -> 399
            else -> 4 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }

    fun habilidad() {
        when (clase) {
            Clase.Mago -> {
                calcularSalud() // Subir la salud al límite del nivel
                println("$nombre utiliza su habilidad de Mago para restaurar su salud.")
            }
            Clase.Brujo -> {
                ataque *= 2 // Duplicar el ataque
                println("$nombre utiliza su habilidad de Brujo para duplicar su ataque.")
            }
            Clase.Guerrero -> {
                suerte *= 2 // Duplicar la suerte
                println("$nombre utiliza su habilidad de Guerrero para duplicar su suerte.")
            }
        }
    }
    fun entrenar(tiempoDeEntrenamiento: Int) {
        val factorExperienciaPorHora = 5
        val experienciaGanada = tiempoDeEntrenamiento * factorExperienciaPorHora

        setExperiencia(experienciaGanada)

        println("$nombre ha entrenado durante $tiempoDeEntrenamiento horas y ha ganado $experienciaGanada de experiencia.")
    }

    override fun toString(): String {
        return "Personaje(nombre='$nombre', raza=$raza, clase=$clase, estadoVital=$estadoVital, salud=$salud, ataque=$ataque, experiencia=$experiencia, nivel=$nivel, suerte=$suerte, defensa=$defensa)"
    }


}