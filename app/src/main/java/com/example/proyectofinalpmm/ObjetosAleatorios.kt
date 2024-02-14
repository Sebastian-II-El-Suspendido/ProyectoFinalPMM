package com.example.proyectofinalpmm

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ObjetosAleatorios(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "ObjetosAleatorios.db"
        private const val TABLA_ARTICULOS = "articulos"
        private const val KEY_ID = "_id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_TIPO = "tipo"
        private const val COLUMN_PESO = "peso"
        private const val COLUMN_PRECIO = "precio"
        private const val COLUMN_URI_IMAGEN = "url"
        private const val COLUMN_UNIDADES = "unidades"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE IF NOT EXISTS $TABLA_ARTICULOS ($KEY_ID INTEGER PRIMARY KEY, $COLUMN_NOMBRE TEXT, $COLUMN_TIPO TEXT, $COLUMN_PESO INTEGER, $COLUMN_PRECIO INTEGER, $COLUMN_URI_IMAGEN INTEGER, $COLUMN_UNIDADES INTEGER)"
        db?.execSQL(createTable)

        insertarArticulosDefault(db)
    }

    private fun insertarArticulosDefault(db: SQLiteDatabase?) {
        val listaArticulos = arrayListOf(
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.AMULETO, 5, 15, R.drawable.amuleto, 10),
            Articulos(Articulos.TipoArticulo.ARMA, Articulos.Nombre.BASTON, 8, 12, R.drawable.baston, 10),
            Articulos(Articulos.TipoArticulo.ARMA, Articulos.Nombre.DAGA, 3, 18, R.drawable.daga, 10),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.GRIMORIO, 10, 10, R.drawable.grimorio, 10),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.HUEVO, 2, 20, R.drawable.huevo, 10),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.MAPA, 7, 13, R.drawable.mapa, 10),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.PERGAMINOS, 1, 19, R.drawable.pergaminos, 10),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.POCION, 6, 14, R.drawable.pocion, 10),
            Articulos(Articulos.TipoArticulo.OBJETO, Articulos.Nombre.RUNAS, 4, 16, R.drawable.runas, 10)
        )
        val values = ContentValues()

        for ( i in listaArticulos ){
            values.clear()

            values.put(COLUMN_TIPO, i.getTipoArticulo().toString())
            values.put(COLUMN_NOMBRE, i.getNombre().toString())
            values.put(COLUMN_PESO, i.getPeso())
            values.put(COLUMN_PRECIO, i.getPrecio())
            values.put(COLUMN_URI_IMAGEN, i.getUri())
            values.put(COLUMN_UNIDADES, i.getUnidades())

            db?.insert(TABLA_ARTICULOS, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLA_ARTICULOS")
        onCreate(db)
    }

    fun insertarArticulo(articulo: Articulos): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, articulo.getNombre().toString())
            put(COLUMN_TIPO, articulo.getTipoArticulo().toString())
            put(COLUMN_PESO, articulo.getPeso())
            put(COLUMN_PRECIO, articulo.getPrecio())
            put(COLUMN_URI_IMAGEN, articulo.getUri())
            put(COLUMN_UNIDADES, articulo.getUnidades())
        }
        val id = db.insert(TABLA_ARTICULOS, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun getArticulos(): ArrayList<Articulos> {
        val articulos = ArrayList<Articulos>()
        val selectQuery = "SELECT * FROM $TABLA_ARTICULOS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val tipo = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO))
                val peso = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO))
                val precio = cursor.getInt(cursor.getColumnIndex(COLUMN_PRECIO))
                val uri = cursor.getInt(cursor.getColumnIndex(COLUMN_URI_IMAGEN))
                val unidades = cursor.getInt(cursor.getColumnIndex(COLUMN_UNIDADES))

                val nombreA = when (nombre) {
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

                val tipoA = when(tipo){
                    "ARMA"-> Articulos.TipoArticulo.ARMA
                    "OBJETO"-> Articulos.TipoArticulo.OBJETO
                    "ORO" -> Articulos.TipoArticulo.ORO
                    else -> Articulos.TipoArticulo.ARMA
                }

                articulos.add(Articulos(tipoA, nombreA, peso, precio, uri, unidades))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return articulos
    }
}