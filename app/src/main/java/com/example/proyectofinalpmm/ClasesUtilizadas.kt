package com.example.proyectofinalpmm

import android.content.Context
import java.io.Serializable

class Mensajes(
    val content: String,
    val sender: String,
    val reciver: String
)

class Articulos(
    private var tipoArticulo: TipoArticulo,
    private var nombre: Nombre,
    private var peso: Int,
    private var precio: Int,
    private var uri: Int
) :
    Serializable {

    enum class TipoArticulo { ARMA, OBJETO, ORO }
    enum class Nombre { AMULETO, BASTON, DAGA, GRIMORIO, HUEVO, MAPA, PERGAMINOS, POCION, RUNAS, MONEDA }

    fun getPrecio(): Int {
        return precio
    }

    fun getPeso(): Int {
        return peso
    }

    fun getNombre(): Nombre {
        return nombre
    }

    fun getTipoArticulo(): TipoArticulo {
        return tipoArticulo
    }

    fun getUri(): Int {
        return uri
    }

    override fun toString(): String {
        return "[Tipo Artículo:$tipoArticulo, Nombre:$nombre, Peso:$peso]"
    }
}

class Mochila(private var pesoMochila: Int, context: Context) : Serializable {
    private var contenido = ArrayList<Articulos>()

    fun getPesoMochila(): Int {
        return pesoMochila
    }

    fun addArticulo(articulo: Articulos) {
        if (articulo.getPeso() <= pesoMochila) {
            contenido.add(articulo)
            this.pesoMochila -= articulo.getPeso()
        } else {
            println("El peso del artículo excede el límite de la mochila.")
        }
    }

    fun getContenido(): ArrayList<Articulos> {
        return contenido
    }

    fun findObjeto(nombre: Articulos.Nombre): Int {
        return contenido.indexOfFirst { it.getNombre() == nombre }
    }

    override fun toString(): String {
        return if (contenido.isEmpty()) {
            "Mochila vacía"
        } else {
            "Artículos en la mochila: ${contenido.joinToString("\n")}"
        }
    }
}

class PersonajeP(
    private var nombre: String,
    private val raza: Raza,
    private var clase: Clase,
    private var estadoVital: EstadoVital,
    private var context: Context
) {
    private var salud: Int = 0
    private var ataque: Int = 0
    private var experiencia: Int
    private var nivel: Int
    private var suerte: Int
    private var defensa: Int = 0

    // Enumeración para el tipo de raza y clase
    enum class Raza { Humano, Elfo, Enano, Maldito }
    enum class Clase { Brujo, Mago, Guerrero }
    enum class EstadoVital { Anciano, Joven, Adulto }

    private val mochila = Mochila(10, context) // Ejemplo de peso máximo de la mochila

    // Atributos para el equipo del personaje
    private var arma: Articulo? = null
    private var proteccion: Articulo? = null

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

    fun pelea(monstruo: Monstruo) {
        var vidaMonstruo = monstruo.getSalud()
        var expGanada =
            monstruo.getSalud() // La experiencia ganada es igual a la salud inicial del monstruo
        var vidaPersonaje = salud
        var contador = false
        println("¡Un ${monstruo.getNombre()} se acerca!")



        while (vidaMonstruo > 0 && vidaPersonaje > 0) {
            // Preguntar al usuario si desea activar la habilidad
            println("¿Deseas activar la habilidad del personaje? (Sí/No)")
            val respuesta = readLine()?.toLowerCase()

            if ((respuesta == "si" || respuesta == "sí") && contador == false) {
                habilidad() // Activa la habilidad del personaje
                contador = true
            }
            val evasion = suerte >= 10
            val ataqueMonstruo = if (evasion) 0 else monstruo.getAtaque()

            // Aplicar la defensa del personaje
            val defensaPersonaje = defensa * suerte / 100
            val danoMonstruo = if (evasion) 0 else ataqueMonstruo - defensaPersonaje

            if (!evasion) {
                vidaPersonaje -= danoMonstruo
            }

            println("${nombre} tiene una suerte de ${suerte}% y una defensa de ${defensaPersonaje}.")
            println("${nombre} ha recibido ${danoMonstruo} de daño. Salud de ${nombre}: ${vidaPersonaje}")

            if (vidaMonstruo > 0) {
                // Personaje ataca al monstruo
                vidaMonstruo -= ataque
                println("${nombre} ataca al ${monstruo.getNombre()}. Salud del ${monstruo.getNombre()}: ${vidaMonstruo}")
                if (vidaMonstruo <= 0) {
                    setExperiencia(expGanada)  // El personaje gana experiencia igual a la salud inicial del monstruo
                    println("${nombre} ha derrotado al ${monstruo.getNombre()} y gana ${expGanada} de experiencia.")
                    break
                }

                // Monstruo ataca al personaje
                vidaPersonaje -= ataqueMonstruo
                println("${monstruo.getNombre()} ataca a ${nombre}. Salud de ${nombre}: ${vidaPersonaje}")
            }
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

    fun realizarMision(tipoMision: String, dificultad: String) {
        val probabilidadExito = when (dificultad) {
            "Fácil" -> if (nivel >= 5) 8 else 6
            "Normal" -> if (nivel >= 3) 6 else 4
            "Difícil" -> if (nivel >= 7) 4 else 2
            else -> 0 // En caso de dificultad no reconocida
        }

        val resultado = (1..10).random() // Valor aleatorio entre 1 y 10

        if (resultado <= probabilidadExito) {
            val experienciaGanada = when (tipoMision) {
                "Caza" -> 1000
                "Búsqueda" -> 500
                "Asedio" -> 2000
                "Destrucción" -> 5000
                else -> 0 // En caso de tipo de misión no reconocido
            }

            val multiplicadorExperiencia = when (dificultad) {
                "Fácil" -> 0.5
                "Normal" -> 1.0
                "Difícil" -> 2.0
                else -> 0.0 // En caso de dificultad no reconocida
            }

            val experienciaFinal = (experienciaGanada * multiplicadorExperiencia).toInt()
            setExperiencia(experienciaFinal)
            println("$nombre ha completado la misión de $tipoMision ($dificultad) con éxito y gana $experienciaFinal de experiencia.")
        } else {
            println("$nombre ha fracasado en la misión de $tipoMision ($dificultad) y no recibe ninguna recompensa.")
        }
    }

    fun cifrado(mensaje: String, ROT: Int): String {
        val abecedario: ArrayList<Char> = "abcdefghijklmnñopqrstuvwxyz".toList() as ArrayList<Char>
        var stringInv = ""
        var indice = 0

        for (i in mensaje.lowercase().toList() as ArrayList<Char>) {
            if (!i.isLetter() || i.isWhitespace()) {
                stringInv += i
            } else {
                indice = abecedario.indexOf(i) + ROT
                if (indice >= abecedario.size) {
                    indice -= abecedario.size
                    stringInv += abecedario[indice]
                } else
                    stringInv += abecedario[indice]
            }
        }
        return stringInv.filter { !it.isWhitespace() && it.isLetter() }
    }

    fun comunicacion(mensaje: String): String {
        var respuesta = ""
        when (estadoVital) {
            EstadoVital.Adulto -> {
                if (mensaje.equals("¿Como estas?"))
                    respuesta = "En la flor de la vida, pero me empieza a doler la espalda"
                else
                    if ((mensaje.contains('?') || mensaje.contains('¿')) && mensaje == mensaje.uppercase())
                        respuesta = "Estoy buscando la mejor solución"
                    else
                        if (mensaje == mensaje.uppercase())
                            respuesta = "No me levantes la voz mequetrefe"
                        else
                            if (mensaje == nombre)
                                respuesta = "¿Necesitas algo?"
                            else
                                if (mensaje == "Tienes algo equipado?") {
                                    if (arma != null || proteccion != null) {
                                        val equipamiento = mutableListOf<String>()
                                        if (arma != null) {
                                            equipamiento.add(arma!!.getNombre().name)
                                        }
                                        if (proteccion != null) {
                                            equipamiento.add(proteccion!!.getNombre().name)
                                        }
                                        println("Tengo equipado: ${equipamiento.joinToString(", ")}")
                                    } else {
                                        println("Ligero como una pluma.")
                                    }
                                } else
                                    respuesta = "No sé de qué me estás hablando"
            }

            EstadoVital.Joven -> {
                if (mensaje.equals("¿Como estas?"))
                    respuesta = "De lujo"
                else
                    if ((mensaje.contains('?') || mensaje.contains('¿')) && mensaje == mensaje.uppercase())
                        respuesta = "Tranqui se lo que hago"
                    else
                        if (mensaje == mensaje.uppercase())
                            respuesta = "Eh relájate"
                        else
                            if (mensaje == nombre)
                                respuesta = "Qué pasa?"
                            else
                                if (mensaje == "Tienes algo equipado?") {
                                    if (arma != null || proteccion != null) {
                                        println("No llevo nada, agente, se lo juro.")
                                    } else {
                                        println("Regístrame si quieres.")
                                    }
                                } else
                                    respuesta = "Yo que se"

            }

            EstadoVital.Anciano -> {
                if (mensaje.equals("¿Como estas?"))
                    respuesta = "No me puedo mover"
                else
                    if ((mensaje.contains('?') || mensaje.contains('¿')) && mensaje == mensaje.uppercase())
                        respuesta = "Que no te escucho!"
                    else
                        if (mensaje == mensaje.uppercase())
                            respuesta = "Háblame más alto que no te escucho"
                        else
                            if (mensaje == nombre)
                                respuesta = "Las 5 de la tarde"
                            else
                                if (mensaje == "Tienes algo equipado?") {
                                    println("A ti que te importa nini!")
                                } else
                                    respuesta = "En mis tiempos esto no pasaba"
            }
        }
        return when (raza) {
            Raza.Elfo -> cifrado(respuesta, 1)
            Raza.Enano -> respuesta.uppercase()
            Raza.Maldito -> cifrado(respuesta, 1)
            else -> respuesta
        }
    }

    /*
    fun equipar(articulo: Articulo) {
        when (articulo.getTipoArticulo()) {
            Articulo.TipoArticulo.ARMA -> {
                if (articulo.getNombre() in Articulo.Nombre.BASTON..Articulo.Nombre.GARRAS) {
                    arma = articulo
                    // Aumentar el ataque del personaje según el nombre del arma
                    ataque += articulo.getAumentoAtaque()
                    println("Has equipado el arma: $articulo")
                    mochila.getContenido().remove(articulo)
                } else {
                    println("No se puede equipar el artículo. Tipo de arma no válido.")
                }
            }
            Articulo.TipoArticulo.PROTECCION -> {
                when (articulo.getNombre()) {
                    Articulo.Nombre.ESCUDO, Articulo.Nombre.ARMADURA -> {
                        proteccion = articulo
                        // Aumentar la defensa del personaje solo si la protección es un escudo o una armadura
                        defensa += articulo.getAumentoDefensa()
                        println("Has equipado la protección: $articulo")
                        mochila.getContenido().remove(articulo)
                    }
                    else -> {
                        println("No se puede equipar el artículo. Tipo de protección no válido.")
                    }
                }
            }
            else -> {
                println("No se puede equipar el artículo. Tipo de artículo no válido.")
            }
        }
    }
    fun usarObjeto(articulo: Articulo) {
        when (articulo.getTipoArticulo()) {
            Articulo.TipoArticulo.OBJETO -> {
                when (articulo.getNombre()) {
                    Articulo.Nombre.POCION -> {
                        // Aumentar la vida del personaje al usar una poción
                        salud += articulo.getAumentoVida()
                        println("Has usado la poción y aumentado tu vida. Vida actual: $salud")
                        mochila.getContenido().remove(articulo)
                    }
                    Articulo.Nombre.IRA -> {
                        // Aumentar el ataque del personaje al usar un objeto de ira
                        ataque += articulo.getAumentoAtaque()
                        println("Has canalizado tu ira y aumentado tu ataque. Ataque actual: $ataque")
                        mochila.getContenido().remove(articulo)
                    }
                    else -> {
                        println("No se puede usar el objeto. Tipo de objeto no válido.")
                    }
                }
            }
            else -> {
                println("No se puede usar el artículo. Tipo de artículo no válido.")
            }
        }
    }
    fun getMochila(): Mochila {
        return this.mochila
    }

     */
    override fun toString(): String {
        return "Personaje: Nombre: $nombre, Nivel: $nivel, Salud: $salud, Ataque: $ataque, Defensa: $defensa, Suerte: $suerte, Raza: $raza, Clase: $clase, Estado Vital: $estadoVital Mochila: $mochila"
    }

}

/***********************************************************************************************************************
 *  CLASE: Mochila
 *  CONSTRUCTOR:
 *      pesoMochila      - > Peso máximo que puede soportar la mochila (Int)
 *
 *  METODOS
 *      getPesoMochila()        - > Devuelve el peso máximo como Int
 *      addArticulo()           - > Añade un artículo (clase Articulo) a la mochila, comprobando que el peso del
 *                                  artículo sumado al peso total del resto de artículos de la Mochila no supere el
 *                                  límite (pesoMochila)
 *      getContenido()          - > Devuelve el ArrayList de artículos que contiene la mochila
 *      findObjeto(nombre)      - > Devuelve la posición del artículo que cuyo nombre (Articulo.Nombre) pasamos como
 *                                  entrada o -1 si no lo encuentra
 *
 **********************************************************************************************************************/

class Articulo(
    private var tipoArticulo: TipoArticulo,
    private var nombre: Nombre,
    private var peso: Int
) {

    enum class TipoArticulo { ARMA, OBJETO, PROTECCION }
    enum class Nombre { BASTON, ESPADA, DAGA, MARTILLO, GARRAS, POCION, IRA, ESCUDO, ARMADURA }

    fun getPeso(): Int {
        return peso
    }

    fun getNombre(): Nombre {
        return nombre
    }

    fun getTipoArticulo(): TipoArticulo {
        return tipoArticulo
    }

    fun getAumentoAtaque(): Int {
        return when (nombre) {
            Nombre.BASTON -> 10
            Nombre.ESPADA -> 20
            Nombre.DAGA -> 15
            Nombre.MARTILLO -> 25
            Nombre.GARRAS -> 30
            Nombre.IRA -> 80
            else -> 0 // Para otros tipos de armas no especificados
        }
    }

    fun getAumentoDefensa(): Int {
        return when (nombre) {
            Nombre.ESCUDO -> 10
            Nombre.ARMADURA -> 20
            else -> 0 // Para otros tipos de protecciones no especificados
        }
    }

    fun getAumentoVida(): Int {
        return when (nombre) {
            Nombre.POCION -> 100
            else -> 0 // Para otros tipos de objetos no especificados
        }
    }

    override fun toString(): String {
        return "[Tipo Artículo:$tipoArticulo, Nombre:$nombre, Peso:$peso]"
    }
}

class Monstruo(
    private var nombre: String,
    private var nivel: Int
) {
    private var salud: Int = 0
    private var ataque: Int = 0

    init {
        calcularSalud() // Inicializar la salud según el nivel al nivel 1
        calcularAtaque() // Inicializar el ataque según el nivel al nivel 1
    }

    fun getNombre(): String {
        return nombre
    }

    fun setNombre(nuevoNombre: String) {
        nombre = nuevoNombre
    }

    fun getNivel(): Int {
        return nivel
    }

    fun setNivel(nuevoNivel: Int) {
        nivel = nuevoNivel
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

    private fun calcularSalud() {
        salud = when (nivel) {
            1 -> 100
            2 -> 125
            3 -> 150
            4 -> 200
            5 -> 250
            6 -> 350
            7 -> 400
            8 -> 600
            9 -> 800
            10 -> 1000
            else -> 100 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }

    private fun calcularAtaque() {
        ataque = when (nivel) {
            1 -> 5
            2 -> 10
            3 -> 15
            4 -> 20
            5 -> 50
            6 -> 60
            7 -> 120
            8 -> 200
            9 -> 350
            10 -> 400
            else -> 5 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }

    override fun toString(): String {
        return "Monstruo: Nombre: $nombre, Nivel: $nivel, Salud: $salud, Ataque: $ataque"
    }

}