package com.example.proyectofinalpmm.base_de_datos

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyectofinalpmm.Articulos
import com.example.proyectofinalpmm.PersonajeP

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
        private const val COLUMN_CONTENIDO = "mensaje"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableObjetos = (
                "CREATE TABLE IF NOT EXISTS $TABLA_OBJETOS (" +
                        "$COLUMN_ID_OBJETO INTEGER PRIMARY KEY," +
                        "$COLUMN_NOMBRE_ARTICULO TEXT," +
                        "$COLUMN_PESO INTEGER," +
                        "$COLUMN_PRECIO INTEGER," +
                        "$COLUMN_TIPO_ARTICULO TEXT," +
                        "$COLUMN_ID_IMAGEN INTEGER"
                )

        val createTablePersonaje = (
                "CREATE TABLE IF NOT EXISTS $TABLA_PERSONAJE (" +
                        "$COLUMN_ID_USER INTEGER PRIMARY KEY," +
                        "$COLUMN_NOMBRE_PERSONAJE TEXT," +
                        "$COLUMN_RAZA TEXT," +
                        "$COLUMN_CLASE TEXT," +
                        "$COLUMN_ESTADOVITAL TEXT," +
                        "$COLUMN_MONEDERO INTEGER)"
                )

        val createTableMercader = (
                "CREATE TABLE IF NOT EXISTS $TABLA_MERCADER (" +
                        "$COLUMN_ID_MERCADER INTEGER PRIMARY KEY"
                )

        val createTableRelacionPersonajeObjetos = (
                "CREATE TABLE IF NOT EXISTS $TABLA_PERSONAJE_ARTICULOS (" +
                        "$COLUMN_ID_RELACION_PERSONAJE_OBJETOS INTEGER PRIMARY KEY," +
                        "$COLUMN_ID_PERSONAJE_REL INTEGER," +
                        "$COLUMN_ID_OBJETO_REL_OP INTEGER," +
                        "$COLUMN_CANTIDAD_OP INTEGER," +
                        "FOREIGN KEY($COLUMN_ID_PERSONAJE_REL) REFERENCES $TABLA_PERSONAJE($COLUMN_ID_USER)," +
                        "FOREIGN KEY($COLUMN_ID_OBJETO_REL_OP) REFERENCES $TABLA_OBJETOS($COLUMN_ID_OBJETO))"
                )

        val createTableRelacionMercaderObjetos = (
                "CREATE TABLE IF NOT EXISTS $TABLA_MERCADER_ARTICULOS (" +
                        "$COLUMN_ID_RELACION_MERCADER_OBJETOS INTEGER PRIMARY KEY," +
                        "$COLUMN_ID_MERCADER_REL INTEGER," +
                        "$COLUMN_ID_OBJETO_REL_OM INTEGER," +
                        "$COLUMN_CANTIDAD_OM INTEGER," +
                        "FOREIGN KEY($COLUMN_ID_MERCADER_REL) REFERENCES $TABLA_MERCADER($COLUMN_ID_MERCADER)," +
                        "FOREIGN KEY($COLUMN_ID_OBJETO_REL_OM) REFERENCES $TABLA_OBJETOS($COLUMN_ID_OBJETO))"
                )

        val createTableChat = (
                "CREATE TABLE IF NOT EXISTS $TABLA_CHAT (" +
                        "$COLUMN_ID_CHAT INTEGER PRIMARY KEY," +
                        "$COLUMN_ID_PERSONAJE INTEGER," +
                        "$COLUMN_CONTENIDO TEXT," +
                        "FOREIGN KEY($COLUMN_ID_PERSONAJE) REFERENCES $TABLA_PERSONAJE($COLUMN_ID_USER))"
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

        iniciarArticulos()
    }

    private fun crearTablas(tablas: Array<String>, db: SQLiteDatabase?) {
        tablas.forEach {
            db?.execSQL(it)
        }
    }

    private fun iniciarArticulos(){
        val listaArticulos = arrayListOf(
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.AMULETO, 5, 15, 1),
            Articulos(Articulos.TipoArticulo.ARMA, Articulos.Nombre.BASTON, 10, 15, 1),
            Articulos(Articulos.TipoArticulo.ARMA, Articulos.Nombre.DAGA, 15, 20, 1),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.GRIMORIO, 10, 10, 1),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.HUEVO, 15, 20, 1),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.MAPA, 5, 10, 1),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.PERGAMINOS, 5, 5, 1),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.POCION, 5, 15, 1),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.RUNAS, 5, 15, 1),
            Articulos(Articulos.TipoArticulo.ORO, Articulos.Nombre.MONEDA, 0, 5, 1)
        )
        listaArticulos.forEach { insertarArticulo( it ) }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertarPersonaje(personajeP: PersonajeP, id: Int): Long {
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
        }
        val idR = db.insert(TABLA_PERSONAJE, null, values)
        db.close()
        return idR
    }

    @SuppressLint("Range")
    fun obtenerPersonajePorID(id: Int, context: Context): PersonajeP? {
        val selectQuery = "SELECT * FROM $TABLA_PERSONAJE WHERE $COLUMN_ID_USER = ?"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, arrayOf(id.toString()))

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

    fun agregarArticuloPersonaje(idPersonaje: Int, idObjeto: Int, cantidad: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_ID_PERSONAJE_REL, idPersonaje)
            put(COLUMN_ID_OBJETO_REL_OP, idObjeto)
            put(COLUMN_CANTIDAD_OP, cantidad)
        }
        db?.insert(TABLA_PERSONAJE_ARTICULOS, null, contentValues)
    }

    fun actualizarArticuloPersonaje(idPersonaje: Int, idObjeto: Int, nuevaCantidad: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_CANTIDAD_OP, nuevaCantidad)
        }
        val whereClause = "$COLUMN_ID_PERSONAJE_REL = $idPersonaje AND $COLUMN_ID_OBJETO_REL_OP = $idObjeto"
        val whereArgs = arrayOf(idPersonaje.toString(), idObjeto.toString())
        db.update(TABLA_PERSONAJE_ARTICULOS, contentValues, whereClause, whereArgs)
    }

    fun insertarMercader(id: Int){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID_MERCADER, id)
        }
        db.insert(TABLA_MERCADER, null, values)
    }

    fun agregarArticuloMercader(idMercader: Int, idObjeto: Int, cantidad: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_ID_MERCADER_REL, idMercader)
            put(COLUMN_ID_OBJETO_REL_OM, idObjeto)
            put(COLUMN_CANTIDAD_OM, cantidad)
        }
        db?.insert(TABLA_MERCADER_ARTICULOS, null, contentValues)
    }

    fun actualizarArticuloMercader(idMercader: Int, idObjeto: Int, nuevaCantidad: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_CANTIDAD_OM, nuevaCantidad)
        }
        val whereClause = "$COLUMN_ID_MERCADER_REL = $idMercader AND $COLUMN_ID_OBJETO_REL_OM = $idObjeto"
        val whereArgs = arrayOf(idMercader.toString(), idObjeto.toString())
        db.update(TABLA_MERCADER_ARTICULOS, contentValues, whereClause, whereArgs)
    }

    fun insertarArticulo(articulo: Articulos): Long {
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
    fun buscarArticuloPorNombre(nombreArticulo: Articulos.Nombre): Articulos? {
        val db = this.readableDatabase
        val selectQuery =
            "SELECT * FROM $TABLA_OBJETOS WHERE $COLUMN_NOMBRE_ARTICULO = ?"
        val cursor = db.rawQuery(selectQuery, arrayOf(nombreArticulo.name))

        var articulo: Articulos? = null

        if (cursor.moveToFirst()) {
            val tipoArticuloString = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO_ARTICULO))
            val peso = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO))
            val precio = cursor.getInt(cursor.getColumnIndex(COLUMN_PRECIO))
            val uri = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_IMAGEN))

            val tipoArticulo = when (tipoArticuloString) {
                "ARMA" -> Articulos.TipoArticulo.ARMA
                "OBJETO" -> Articulos.TipoArticulo.OBJETO
                "ORO" -> Articulos.TipoArticulo.ORO
                else -> Articulos.TipoArticulo.OBJETO
            }

            articulo = Articulos(tipoArticulo, nombreArticulo, peso, precio, uri)
        }
        cursor.close()
        db.close()

        return articulo
    }

    @SuppressLint("Range")
    fun obtenerListaArticulos() : ArrayList<Articulos>{
        val db = this.readableDatabase
        val selectQuery =
            "SELECT * FROM $TABLA_OBJETOS"
        val cursor = db.rawQuery(selectQuery, null)
        val listaArticulos = arrayListOf<Articulos>()

        if (cursor.moveToFirst()) {
            val tipoArticuloString = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO_ARTICULO))
            val nombreArticuloString = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE_ARTICULO))
            val peso = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO))
            val precio = cursor.getInt(cursor.getColumnIndex(COLUMN_PRECIO))
            val uri = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_IMAGEN))

            val tipoArticulo = when (tipoArticuloString) {
                "ARMA" -> Articulos.TipoArticulo.ARMA
                "OBJETO" -> Articulos.TipoArticulo.OBJETO
                "ORO" -> Articulos.TipoArticulo.ORO
                else -> Articulos.TipoArticulo.OBJETO
            }

            val nombreArticulo = when(nombreArticuloString){
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
}