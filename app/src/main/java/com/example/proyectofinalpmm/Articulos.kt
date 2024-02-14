package com.example.proyectofinalpmm

import java.io.Serializable

class Articulos(private var tipoArticulo: TipoArticulo, private var nombre: Nombre, private var peso: Int, private var precio : Int, private var uri : Int, private var unidades : Int):
    Serializable {

    enum class TipoArticulo { ARMA, OBJETO, ORO }
    enum class Nombre { AMULETO, BASTON, DAGA, GRIMORIO, HUEVO, MAPA, PERGAMINOS, POCION, RUNAS, MONEDA }

    fun getPrecio():Int{
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

    fun getUri(): Int{
        return uri
    }

    fun getUnidades(): Int{
        return unidades
    }

    override fun toString(): String {
        return "[Tipo Artículo:$tipoArticulo, Nombre:$nombre, Peso:$peso]"
    }
}