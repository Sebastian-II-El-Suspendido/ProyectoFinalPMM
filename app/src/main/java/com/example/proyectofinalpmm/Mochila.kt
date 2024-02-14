package com.example.proyectofinalpmm


import java.io.Serializable

class Mochila(private var pesoMochila: Int) : Serializable {
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