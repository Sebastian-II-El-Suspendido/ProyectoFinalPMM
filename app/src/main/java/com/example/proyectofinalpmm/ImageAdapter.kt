package com.example.proyectofinalpmm


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ImageAdapter(
    private val context: Context,
    private val ciudadesList: List<Pair<Int, Int>>,
    private val zonasList: List<Pair<String, String>>
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewCasa)
        val textView: TextView = itemView.findViewById(R.id.textViewCasa)
      //  val imageViewBarrio: ImageView? = itemView.findViewById(R.id.imageViewBarrio)
       // val textViewDescripcion: TextView? = itemView.findViewById(R.id.textViewDescripcion)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.itemrecycler, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val ciudad = ciudadesList[position]
        holder.imageView.setImageResource(ciudad.first)


        val zona = zonasList[position]
        holder.textView.text = zona.first

        holder.imageView.setOnClickListener {
        val intent = Intent(context, DetallesCasita::class.java).apply {
            putExtra("IMAGEN_CASA", ciudad.first)
            putExtra("NOMBRE_BARRIO", zona.first)
            putExtra("DESC_BARRIO", zona.second)
            putExtra("FONDO_CASA", ciudad.second)
        }
        context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {

        return minOf(ciudadesList.size, zonasList.size)
    }
}



