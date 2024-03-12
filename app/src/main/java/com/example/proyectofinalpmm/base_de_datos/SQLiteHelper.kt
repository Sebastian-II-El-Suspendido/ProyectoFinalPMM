package com.example.proyectofinalpmm.base_de_datos

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.proyectofinalpmm.Articulos
import com.example.proyectofinalpmm.PersonajeP
import com.example.proyectofinalpmm.R
import java.util.UUID

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "SQLite_bd"

        //CREAR TABLA OBJETOS
        private const val TABLA_OBJETOS = "objetos"
        private const val COLUMN_ID_OBJETO = "_id"
        private const val COLUMN_NOMBRE_ARTICULO = "nombre"
        private const val COLUMN_PESO = "peso"
        private const val COLUMN_PRECIO = "precio"
        private const val COLUMN_TIPO_ARTICULO = "tipo_objeto"
        private const val COLUMN_ID_IMAGEN = "imagen"

        // CREAR TABLA PERSONAJE
        private const val TABLA_PERSONAJE = "personaje"
        private const val COLUMN_ID_USER = "_id"
        private const val COLUMN_NOMBRE_PERSONAJE = "nombre"
        private const val COLUMN_RAZA = "raza"
        private const val COLUMN_CLASE = "clase"
        private const val COLUMN_ESTADOVITAL = "estado_vital"
        private const val COLUMN_MONEDERO = "monedero"
        private const val COLUMN_MUSICA = "musica"

        // CREAR TABLA MERCADER
        private const val TABLA_MERCADER = "mercader"
        private const val COLUMN_ID_MERCADER = "_id"

        // CREAR TABLA RELACION PERSONAJE-ARTICULOS (mochila - personaje)
        private const val TABLA_PERSONAJE_ARTICULOS = "mochila_personaje"
        private const val COLUMN_ID_RELACION_PERSONAJE_OBJETOS = "_id"
        private const val COLUMN_ID_PERSONAJE_REL = "id_personaje"
        private const val COLUMN_ID_OBJETO_REL_OP = "id_objeto"
        private const val COLUMN_CANTIDAD_OP = "cantidad"

        //CREAR TABLA RELACION MERCADER-ARTICULOS (mochila - mercader)
        private const val TABLA_MERCADER_ARTICULOS = "mochila_mercader"
        private const val COLUMN_ID_RELACION_MERCADER_OBJETOS = "_id"
        private const val COLUMN_ID_MERCADER_REL = "id_mercader"
        private const val COLUMN_ID_OBJETO_REL_OM = "id_objeto"
        private const val COLUMN_CANTIDAD_OM = "cantidad"

        // CREAR TABLA CHAT
        private const val TABLA_CHAT = "chat"
        private const val COLUMN_ID_CHAT = "_id"
        private const val COLUMN_ID_PERSONAJE = "id_personaje"
        private const val COLUMN_NOMBRE_RESPUESTA = "nombre"
        private const val COLUMN_CONTENIDO = "mensaje"

        private const val PESOMAX = 120
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableObjetos = (
                "CREATE TABLE IF NOT EXISTS $TABLA_OBJETOS (" +
                        "$COLUMN_ID_OBJETO INTEGER PRIMARY KEY," +
                        "$COLUMN_NOMBRE_ARTICULO TEXT," +
                        "$COLUMN_PESO INTEGER," +
                        "$COLUMN_PRECIO INTEGER," +
                        "$COLUMN_TIPO_ARTICULO TEXT," +
                        "$COLUMN_ID_IMAGEN INTEGER)"
                )

        val createTablePersonaje = (
                "CREATE TABLE IF NOT EXISTS $TABLA_PERSONAJE (" +
                        "$COLUMN_ID_USER TEXT PRIMARY KEY," +
                        "$COLUMN_NOMBRE_PERSONAJE TEXT," +
                        "$COLUMN_RAZA TEXT," +
                        "$COLUMN_CLASE TEXT," +
                        "$COLUMN_ESTADOVITAL TEXT," +
                        "$COLUMN_MONEDERO INTEGER," +
                        "$COLUMN_MUSICA INTEGER)"
                )

        val createTableMercader = (
                "CREATE TABLE IF NOT EXISTS $TABLA_MERCADER (" +
                        "$COLUMN_ID_MERCADER TEXT PRIMARY KEY)"
                )

        val createTableRelacionPersonajeObjetos = (
                "CREATE TABLE IF NOT EXISTS $TABLA_PERSONAJE_ARTICULOS (" +
                        "$COLUMN_ID_RELACION_PERSONAJE_OBJETOS TEXT PRIMARY KEY," +
                        "$COLUMN_ID_PERSONAJE_REL TEXT," +
                        "$COLUMN_ID_OBJETO_REL_OP INTEGER," +
                        "$COLUMN_CANTIDAD_OP INTEGER," +
                        "FOREIGN KEY($COLUMN_ID_PERSONAJE_REL) REFERENCES $TABLA_PERSONAJE($COLUMN_ID_USER)," +
                        "FOREIGN KEY($COLUMN_ID_OBJETO_REL_OP) REFERENCES $TABLA_OBJETOS($COLUMN_ID_OBJETO))"
                )

        val createTableRelacionMercaderObjetos = (
                "CREATE TABLE IF NOT EXISTS $TABLA_MERCADER_ARTICULOS (" +
                        "$COLUMN_ID_RELACION_MERCADER_OBJETOS TEXT PRIMARY KEY," +
                        "$COLUMN_ID_MERCADER_REL TEXT," +
                        "$COLUMN_ID_OBJETO_REL_OM INTEGER," +
                        "$COLUMN_CANTIDAD_OM INTEGER," +
                        "FOREIGN KEY($COLUMN_ID_MERCADER_REL) REFERENCES $TABLA_MERCADER($COLUMN_ID_MERCADER)," +
                        "FOREIGN KEY($COLUMN_ID_OBJETO_REL_OM) REFERENCES $TABLA_OBJETOS($COLUMN_ID_OBJETO))"
                )

        val createTableChat = (
                "CREATE TABLE IF NOT EXISTS $TABLA_CHAT (" +
                        "$COLUMN_ID_CHAT TEXT PRIMARY KEY," +
                        "$COLUMN_ID_PERSONAJE TEXT," +
                        "$COLUMN_NOMBRE_RESPUESTA TEXT," +
                        "$COLUMN_CONTENIDO TEXT," +
                        "FOREIGN KEY($COLUMN_ID_PERSONAJE) REFERENCES $TABLA_PERSONAJE($COLUMN_ID_USER)," +
                        "FOREIGN KEY($COLUMN_NOMBRE_RESPUESTA) REFERENCES $TABLA_PERSONAJE($COLUMN_ID_USER))"
                )

        crearTablas(
            arrayOf(
                createTableObjetos,
                createTablePersonaje,
                createTableMercader,
                createTableRelacionPersonajeObjetos,
                createTableRelacionMercaderObjetos,
                createTableChat
            ), db
        )
    }

    private fun crearTablas(tablas: Array<String>, db: SQLiteDatabase?) {
        tablas.forEach {
            db?.execSQL(it)
        }
    }

    fun borrarBaseDeDatos(context: Context) {
        context.deleteDatabase(DATABASE)
    }

    fun iniciarArticulos() {
        val db = this.readableDatabase
        val query = "SELECT COUNT(*) FROM $TABLA_OBJETOS"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()

        if (count == 0) {
            val listaArticulos = arrayListOf(
                Articulos(
                    Articulos.TipoArticulo.OBJETO,
                    Articulos.Nombre.AMULETO,
                    5,
                    15,
                    R.drawable.amuleto
                ),
                Articulos(
                    Articulos.TipoArticulo.ARMA,
                    Articulos.Nombre.BASTON,
                    10,
                    15,
                    R.drawable.baston
                ),
                Articulos(
                    Articulos.TipoArticulo.ARMA,
                    Articulos.Nombre.DAGA,
                    15,
                    20,
                    R.drawable.daga
                ),
                Articulos(
                    Articulos.TipoArticulo.OBJETO,
                    Articulos.Nombre.GRIMORIO,
                    10,
                    10,
                    R.drawable.grimorio
                ),
                Articulos(
                    Articulos.TipoArticulo.OBJETO,
                    Articulos.Nombre.HUEVO,
                    15,
                    20,
                    R.drawable.huevo
                ),
                Articulos(
                    Articulos.TipoArticulo.OBJETO,
                    Articulos.Nombre.MAPA,
                    5,
                    10,
                    R.drawable.mapa
                ),
                Articulos(
                    Articulos.TipoArticulo.OBJETO,
                    Articulos.Nombre.PERGAMINOS,
                    5,
                    5,
                    R.drawable.pergaminos
                ),
                Articulos(
                    Articulos.TipoArticulo.OBJETO,
                    Articulos.Nombre.POCION,
                    5,
                    15,
                    R.drawable.pocion
                ),
                Articulos(
                    Articulos.TipoArticulo.OBJETO,
                    Articulos.Nombre.RUNAS,
                    5,
                    15,
                    R.drawable.runas
                ),
                Articulos(
                    Articulos.TipoArticulo.ORO,
                    Articulos.Nombre.MONEDA,
                    0,
                    5,
                    R.drawable.moneda
                )
            )
            listaArticulos.forEach { insertarArticulo(it) }
        }
    }

    fun meterArticulosMercader(idMercader: String) {
        val listaArticulos = arrayListOf(
            Articulos(
                Articulos.TipoArticulo.OBJETO,
                Articulos.Nombre.AMULETO,
                5,
                15,
                R.drawable.amuleto
            ),
            Articulos(
                Articulos.TipoArticulo.ARMA,
                Articulos.Nombre.BASTON,
                10,
                15,
                R.drawable.baston
            ),
            Articulos(Articulos.TipoArticulo.ARMA, Articulos.Nombre.DAGA, 15, 20, R.drawable.daga),
            Articulos(
                Articulos.TipoArticulo.OBJETO,
                Articulos.Nombre.GRIMORIO,
                10,
                10,
                R.drawable.grimorio
            ),
            Articulos(
                Articulos.TipoArticulo.OBJETO,
                Articulos.Nombre.HUEVO,
                15,
                20,
                R.drawable.huevo
            ),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.MAPA, 5, 10, R.drawable.mapa),
            Articulos(
                Articulos.TipoArticulo.OBJETO,
                Articulos.Nombre.PERGAMINOS,
                5,
                5,
                R.drawable.pergaminos
            ),
            Articulos(
                Articulos.TipoArticulo.OBJETO,
                Articulos.Nombre.POCION,
                5,
                15,
                R.drawable.pocion
            ),
            Articulos(
                Articulos.TipoArticulo.OBJETO,
                Articulos.Nombre.RUNAS,
                5,
                15,
                R.drawable.runas
            ),
            Articulos(Articulos.TipoArticulo.ORO, Articulos.Nombre.MONEDA, 0, 5, R.drawable.moneda)
        )
        listaArticulos.forEach {
            obtenerIdArticuloPorNombre(it.getNombre().toString())?.let { it1 ->
                agregarArticuloMercader(
                    idMercader,
                    it1, (0..20).random()
                )
            }
        }
    }

    fun iniciarPersonajes(context: Context) {
        val dbHelper = SQLiteHelper(context)
        val db = dbHelper.readableDatabase

        val nombreHumano = "Pepe el humano"
        val nombreElfo = "Francisca la elfa"
        val nombreEnano = "Paco el enano"
        val nombreMaldito = "Lucifer el maldito"

        if (!isPersonajeCreado(db, nombreHumano)) {
            val personajeHumano = PersonajeP(
                nombreHumano,
                PersonajeP.Raza.Humano,
                PersonajeP.Clase.entries.random(),
                PersonajeP.EstadoVital.entries.random(),
                context
            )
            insertarPersonaje(personajeHumano, nombreHumano)
        }

        if (!isPersonajeCreado(db, nombreElfo)) {
            val personajeElfo = PersonajeP(
                nombreElfo,
                PersonajeP.Raza.Elfo,
                PersonajeP.Clase.entries.random(),
                PersonajeP.EstadoVital.entries.random(),
                context
            )
            insertarPersonaje(personajeElfo, nombreElfo)
        }

        if (!isPersonajeCreado(db, nombreEnano)) {
            val personajeEnano = PersonajeP(
                nombreEnano,
                PersonajeP.Raza.Enano,
                PersonajeP.Clase.entries.random(),
                PersonajeP.EstadoVital.entries.random(),
                context
            )
            insertarPersonaje(personajeEnano, nombreEnano)
        }

        if (!isPersonajeCreado(db, nombreMaldito)) {
            val personajeMaldito = PersonajeP(
                nombreMaldito,
                PersonajeP.Raza.Maldito,
                PersonajeP.Clase.entries.random(),
                PersonajeP.EstadoVital.entries.random(),
                context
            )
            insertarPersonaje(personajeMaldito, nombreMaldito)
        }
    }

    private fun isPersonajeCreado(db: SQLiteDatabase, nombrePersonaje: String): Boolean {
        val selectQuery = "SELECT COUNT(*) FROM $TABLA_PERSONAJE WHERE $COLUMN_NOMBRE_PERSONAJE = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(nombrePersonaje))
        return cursor.use { cursor ->
            if (cursor.moveToFirst()) {
                val count = cursor.getInt(0)
                count > 0
            } else {
                false
            }
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.let {
            it.execSQL("DROP TABLE IF EXISTS $TABLA_OBJETOS")
            it.execSQL("DROP TABLE IF EXISTS $TABLA_PERSONAJE")
            it.execSQL("DROP TABLE IF EXISTS $TABLA_MERCADER")
            it.execSQL("DROP TABLE IF EXISTS $TABLA_PERSONAJE_ARTICULOS")
            it.execSQL("DROP TABLE IF EXISTS $TABLA_MERCADER_ARTICULOS")
            it.execSQL("DROP TABLE IF EXISTS $TABLA_CHAT")
            onCreate(it)
        }
    }

    fun insertarPersonaje(personajeP: PersonajeP, id: String): Long {
        val db = this.writableDatabase
        val nombre = personajeP.getNombre()
        val raza = personajeP.getRaza().toString()
        val clase = personajeP.getClase().toString()
        val estadoVital = personajeP.getEstadoVital().toString()

        val values = ContentValues().apply {
            put(COLUMN_ID_USER, id)
            put(COLUMN_NOMBRE_PERSONAJE, nombre)
            put(COLUMN_RAZA, raza)
            put(COLUMN_CLASE, clase)
            put(COLUMN_ESTADOVITAL, estadoVital)
            put(COLUMN_MUSICA, 1)
        }
        val idR = db.insert(TABLA_PERSONAJE, null, values)
        db.close()
        iniciacionDePersonaje(id)
        return idR
    }

    private fun iniciacionDePersonaje(idPersonaje: String) {
        val articulo =
            Articulos(Articulos.TipoArticulo.ORO, Articulos.Nombre.MONEDA, 0, 5, R.drawable.moneda)
        val listaArticulos = arrayListOf(
            Articulos(
                Articulos.TipoArticulo.OBJETO,
                Articulos.Nombre.AMULETO,
                5,
                15,
                R.drawable.amuleto
            ),
            Articulos(
                Articulos.TipoArticulo.ARMA,
                Articulos.Nombre.BASTON,
                10,
                15,
                R.drawable.baston
            ),
            Articulos(Articulos.TipoArticulo.ARMA, Articulos.Nombre.DAGA, 15, 20, R.drawable.daga),
            Articulos(
                Articulos.TipoArticulo.OBJETO,
                Articulos.Nombre.GRIMORIO,
                10,
                10,
                R.drawable.grimorio
            ),
            Articulos(
                Articulos.TipoArticulo.OBJETO,
                Articulos.Nombre.HUEVO,
                15,
                20,
                R.drawable.huevo
            ),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.MAPA, 5, 10, R.drawable.mapa),
            Articulos(
                Articulos.TipoArticulo.OBJETO,
                Articulos.Nombre.PERGAMINOS,
                5,
                5,
                R.drawable.pergaminos
            ),
            Articulos(
                Articulos.TipoArticulo.OBJETO,
                Articulos.Nombre.POCION,
                5,
                15,
                R.drawable.pocion
            ),
            Articulos(
                Articulos.TipoArticulo.OBJETO,
                Articulos.Nombre.RUNAS,
                5,
                15,
                R.drawable.runas
            ),
        )
        obtenerIdArticuloPorNombre(articulo.getNombre().toString())?.let {
            agregarArticuloPersonaje(
                idPersonaje,
                it, 10
            )
        }
        listaArticulos.forEach {
            obtenerIdArticuloPorNombre(it.getNombre().toString())?.let { it1 ->
                agregarArticuloPersonaje(
                    idPersonaje,
                    it1, 0
                )
            }
        }
    }

    fun modificarPersonaje(personajeP: PersonajeP, id: String): Int {
        val db = this.writableDatabase
        val nombre = personajeP.getNombre()
        val raza = personajeP.getRaza().toString()
        val clase = personajeP.getClase().toString()
        val estadoVital = personajeP.getEstadoVital().toString()

        val values = ContentValues().apply {
            put(COLUMN_NOMBRE_PERSONAJE, nombre)
            put(COLUMN_RAZA, raza)
            put(COLUMN_CLASE, clase)
            put(COLUMN_ESTADOVITAL, estadoVital)
        }

        val whereClause = "$COLUMN_ID_USER = ?"
        val whereArgs = arrayOf(id)

        val rowCount = db.update(TABLA_PERSONAJE, values, whereClause, whereArgs)
        db.close()

        return rowCount
    }

    @SuppressLint("Range")
    fun obtenerPersonajePorID(id: String, context: Context): PersonajeP? {
        val selectQuery = "SELECT * FROM $TABLA_PERSONAJE WHERE $COLUMN_ID_USER = ?"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(id))

        var personaje: PersonajeP? = null

        if (cursor.moveToFirst()) {
            val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE_PERSONAJE))
            val razaString = cursor.getString(cursor.getColumnIndex(COLUMN_RAZA))
            val claseString = cursor.getString(cursor.getColumnIndex(COLUMN_CLASE))
            val estadoVitalString = cursor.getString(cursor.getColumnIndex(COLUMN_ESTADOVITAL))

            val raza = when (razaString) {
                "Humano" -> PersonajeP.Raza.Humano
                "Elfo" -> PersonajeP.Raza.Elfo
                "Enano" -> PersonajeP.Raza.Enano
                "Maldito" -> PersonajeP.Raza.Maldito
                else -> PersonajeP.Raza.Humano
            }

            val clase = when (claseString) {
                "Brujo" -> PersonajeP.Clase.Brujo
                "Mago" -> PersonajeP.Clase.Mago
                "Guerrero" -> PersonajeP.Clase.Guerrero
                else -> PersonajeP.Clase.Brujo
            }

            val estadoVital = when (estadoVitalString) {
                "Anciano" -> PersonajeP.EstadoVital.Anciano
                "Joven" -> PersonajeP.EstadoVital.Joven
                "Adulto" -> PersonajeP.EstadoVital.Adulto
                else -> PersonajeP.EstadoVital.Anciano
            }

            personaje = PersonajeP(nombre, raza, clase, estadoVital, context)
        }
        cursor.close()
        db.close()

        return personaje
    }

    @SuppressLint("Range")
    fun obtenerMusicaorID(id: String, context: Context): Int {
        val selectQuery = "SELECT * FROM $TABLA_PERSONAJE WHERE $COLUMN_ID_USER = ?"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(id))
        var musica = 1

        if (cursor.moveToFirst()) {
            musica = cursor.getString(cursor.getColumnIndex(COLUMN_MUSICA)).toInt()
        }
        cursor.close()
        db.close()

        return musica
    }

    fun modificarMusicaPorID(id: String, nuevaMusica: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_MUSICA, nuevaMusica)
        }
        val whereClause = "$COLUMN_ID_USER = ?"
        val whereArgs = arrayOf(id)
        db.update(TABLA_PERSONAJE, contentValues, whereClause, whereArgs)
        db.close()
    }

    fun agregarArticuloPersonaje(idPersonaje: String, idObjeto: Int, cantidad: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_ID_PERSONAJE_REL, idPersonaje)
            put(COLUMN_ID_OBJETO_REL_OP, idObjeto)
            put(COLUMN_CANTIDAD_OP, cantidad)
        }
        db?.insert(TABLA_PERSONAJE_ARTICULOS, null, contentValues)
    }

    @SuppressLint("Range")
    fun obtenerArticulosDePersonaje(idPersonaje: String?): List<Articulos> {
        val listaArticulos = mutableListOf<Articulos>()

        val selectQuery =
            "SELECT * FROM $TABLA_PERSONAJE_ARTICULOS WHERE $COLUMN_ID_PERSONAJE_REL = ?"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(idPersonaje))

        while (cursor.moveToNext()) {
            val idArticulo = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_OBJETO_REL_OP))
            val articulo = buscarArticuloPorId(idArticulo)
            articulo?.let {
                listaArticulos.add(it)
            }
        }

        cursor.close()
        db.close()

        return listaArticulos
    }

    fun actualizarArticuloPersonaje(idPersonaje: String?, idObjeto: Int?, nuevaCantidad: Int) {
        val db = this.writableDatabase
        val whereClause = "$COLUMN_ID_PERSONAJE_REL = ? AND $COLUMN_ID_OBJETO_REL_OP = ?"
        val whereArgs = arrayOf(idPersonaje, idObjeto.toString())
        val contentValues = ContentValues().apply {
            put(COLUMN_CANTIDAD_OP, nuevaCantidad)
        }
        db.update(TABLA_PERSONAJE_ARTICULOS, contentValues, whereClause, whereArgs)
    }

    @SuppressLint("Range")
    fun obtenerCantidadArticuloPersonaje(idPersonaje: String?, idObjeto: Int?): Int {
        val db = this.readableDatabase
        val selectQuery =
            "SELECT $COLUMN_CANTIDAD_OP FROM $TABLA_PERSONAJE_ARTICULOS WHERE $COLUMN_ID_PERSONAJE_REL = ? AND $COLUMN_ID_OBJETO_REL_OP = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(idPersonaje, idObjeto.toString()))

        var cantidad = 0
        if (cursor.moveToFirst()) {
            cantidad = cursor.getInt(cursor.getColumnIndex(COLUMN_CANTIDAD_OP))
        }
        cursor.close()
        db.close()

        return cantidad
    }

    fun obtenerPesoTotalPersonaje(idPersonaje: String?, lista: List<Articulos>?) : Int{
        var peso = 0
        if (lista != null) {
            if (lista.isNotEmpty()){
                lista.forEach {
                    val idObjeto = obtenerIdArticuloPorNombre(it.getNombre().toString())
                    val cantidad = obtenerCantidadArticuloPersonaje(idPersonaje, idObjeto)

                    peso += (cantidad * it.getPeso())
                }
            }
        }
        return peso
    }

    fun obtenerPesoMax() : Int {
        return PESOMAX
    }

    fun obtenerMonedasTotales(idPersonaje: String?) : Int {
        var monedas = 0
        monedas += (obtenerCantidadArticuloPersonaje(idPersonaje, obtenerIdArticuloPorNombre(Articulos.Nombre.MONEDA.toString())) * 5)
        return monedas
    }

    @SuppressLint("Range")
    fun obtenerCantidadArticuloMercader(idMercader: String?, idObjeto: Int?): Int {
        val db = this.readableDatabase
        val selectQuery =
            "SELECT $COLUMN_CANTIDAD_OM FROM $TABLA_MERCADER_ARTICULOS WHERE $COLUMN_ID_MERCADER_REL = ? AND $COLUMN_ID_OBJETO_REL_OM = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(idMercader, idObjeto.toString()))

        var cantidad = 0
        if (cursor.moveToFirst()) {
            cantidad = cursor.getInt(cursor.getColumnIndex(COLUMN_CANTIDAD_OM))
        }
        cursor.close()
        db.close()

        return cantidad
    }

    fun insertarMercader(id: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID_MERCADER, id)
        }
        val i = db.insert(TABLA_MERCADER, null, values)
        db.close()
        return i
    }

    @SuppressLint("Range")
    fun obtenerMercaderPorId(idMercader: String): Boolean {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLA_MERCADER WHERE $COLUMN_ID_MERCADER = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(idMercader))
        var mercaderEncontrado = false

        cursor.use {
            if (cursor.moveToFirst()) {
                mercaderEncontrado = true
            }
        }
        return mercaderEncontrado
    }

    fun agregarArticuloMercader(idMercader: String, idObjeto: Int, cantidad: Int) {
        val db = this.writableDatabase
        Log.i("ayuda", "Insertar articulo en mercader $idObjeto")
        val contentValues = ContentValues().apply {
            put(COLUMN_ID_MERCADER_REL, idMercader)
            put(COLUMN_ID_OBJETO_REL_OM, idObjeto)
            put(COLUMN_CANTIDAD_OM, cantidad)
        }
        db?.insert(TABLA_MERCADER_ARTICULOS, null, contentValues)
    }

    @SuppressLint("Range")
    fun obtenerArticulosDeMercader(idMercader: String): List<Articulos> {
        val listaArticulos = mutableListOf<Articulos>()

        val selectQuery =
            "SELECT * FROM $TABLA_MERCADER_ARTICULOS WHERE $COLUMN_ID_MERCADER_REL = ?"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(idMercader))
        while (cursor.moveToNext()) {
            val idArticulo = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_OBJETO_REL_OM))
            val articulo = buscarArticuloPorId(idArticulo)
            if (articulo != null) {
                listaArticulos.add(articulo)
            }
        }

        cursor.close()
        db.close()

        return listaArticulos
    }

    fun actualizarArticuloMercader(idMercader: String?, idObjeto: Int?, nuevaCantidad: Int) {
        val db = this.writableDatabase
        val whereClause = "$COLUMN_ID_MERCADER_REL = ? AND $COLUMN_ID_OBJETO_REL_OM = ?"
        val whereArgs = arrayOf(idMercader, idObjeto.toString())
        val contentValues = ContentValues().apply {
            put(COLUMN_CANTIDAD_OM, nuevaCantidad)
        }
        db.update(TABLA_MERCADER_ARTICULOS, contentValues, whereClause, whereArgs)
    }

    private fun insertarArticulo(articulo: Articulos): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE_ARTICULO, articulo.getNombre().toString())
            put(COLUMN_PESO, articulo.getPeso())
            put(COLUMN_PRECIO, articulo.getPrecio())
            put(COLUMN_TIPO_ARTICULO, articulo.getTipoArticulo().toString())
            put(COLUMN_ID_IMAGEN, articulo.getUri())
        }
        val id = db.insert(TABLA_OBJETOS, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun obtenerIdArticuloPorNombre(nombreArticulo: String): Int? {
        val db = this.readableDatabase
        val selectQuery =
            "SELECT $COLUMN_ID_OBJETO FROM $TABLA_OBJETOS WHERE $COLUMN_NOMBRE_ARTICULO = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(nombreArticulo))

        var idArticulo: Int? = null

        if (cursor.moveToFirst()) {
            idArticulo = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_OBJETO))
        }
        cursor.close()
        db.close()

        return idArticulo
    }

    @SuppressLint("Range")
    fun buscarArticuloPorId(idArticulo: Int): Articulos? {
        val db = this.readableDatabase
        val selectQuery =
            "SELECT * FROM $TABLA_OBJETOS WHERE $COLUMN_ID_OBJETO = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(idArticulo.toString()))

        var articulo: Articulos? = null

        if (cursor.moveToFirst()) {
            val tipoArticuloString = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO_ARTICULO))
            val nombreArticuloString =
                cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE_ARTICULO))
            val peso = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO))
            val precio = cursor.getInt(cursor.getColumnIndex(COLUMN_PRECIO))
            val uri = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_IMAGEN))

            val tipoArticulo = when (tipoArticuloString) {
                "ARMA" -> Articulos.TipoArticulo.ARMA
                "OBJETO" -> Articulos.TipoArticulo.OBJETO
                "ORO" -> Articulos.TipoArticulo.ORO
                else -> Articulos.TipoArticulo.OBJETO
            }

            val nombreArticuloEnum =
                Articulos.Nombre.valueOf(nombreArticuloString) // Convertir cadena a enum

            articulo = Articulos(tipoArticulo, nombreArticuloEnum, peso, precio, uri)
        }
        cursor.close()
        db.close()

        return articulo
    }

    @SuppressLint("Range")
    fun obtenerListaArticulos(): ArrayList<Articulos> {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLA_OBJETOS"
        val cursor = db.rawQuery(selectQuery, null)
        val listaArticulos = arrayListOf<Articulos>()

        while (cursor.moveToNext()) {
            val tipoArticuloString = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO_ARTICULO))
            val nombreArticuloString =
                cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE_ARTICULO))
            val peso = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO))
            val precio = cursor.getInt(cursor.getColumnIndex(COLUMN_PRECIO))
            val uri = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_IMAGEN))

            val tipoArticulo = when (tipoArticuloString) {
                "ARMA" -> Articulos.TipoArticulo.ARMA
                "OBJETO" -> Articulos.TipoArticulo.OBJETO
                "ORO" -> Articulos.TipoArticulo.ORO
                else -> Articulos.TipoArticulo.OBJETO
            }

            val nombreArticulo = when (nombreArticuloString) {
                "AMULETO" -> Articulos.Nombre.AMULETO
                "BASTON" -> Articulos.Nombre.BASTON
                "DAGA" -> Articulos.Nombre.DAGA
                "GRIMORIO" -> Articulos.Nombre.GRIMORIO
                "HUEVO" -> Articulos.Nombre.HUEVO
                "MAPA" -> Articulos.Nombre.MAPA
                "PERGAMINOS" -> Articulos.Nombre.PERGAMINOS
                "POCION" -> Articulos.Nombre.POCION
                "RUNAS" -> Articulos.Nombre.RUNAS
                "MONEDA" -> Articulos.Nombre.MONEDA
                else -> Articulos.Nombre.AMULETO
            }

            listaArticulos.add(Articulos(tipoArticulo, nombreArticulo, peso, precio, uri))
        }
        cursor.close()
        db.close()

        return listaArticulos
    }

    @SuppressLint("Range")
    fun obtenerChatPorIDPersonaje(idPersonaje: String, nombreRespuesta: String): List<String> {
        val db = this.readableDatabase
        val chatList = mutableListOf<String>()
        val selectQuery =
            "SELECT $COLUMN_CONTENIDO FROM $TABLA_CHAT WHERE $COLUMN_ID_PERSONAJE = ? AND $COLUMN_NOMBRE_RESPUESTA = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(idPersonaje, nombreRespuesta))
        cursor.use {
            if (cursor.moveToFirst()) {
                do {
                    val contenido = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENIDO))
                    chatList.add(contenido)
                } while (cursor.moveToNext())
            }
        }
        return chatList
    }

    fun insertarMensajeChat(idPersonaje: String, nombreRespuesta: String, mensaje: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(
                COLUMN_ID_CHAT,
                UUID.randomUUID().toString()
            )
            put(COLUMN_ID_PERSONAJE, idPersonaje)
            put(COLUMN_NOMBRE_RESPUESTA, nombreRespuesta)
            put(COLUMN_CONTENIDO, mensaje)
        }
        db.insert(TABLA_CHAT, null, values)
    }

}