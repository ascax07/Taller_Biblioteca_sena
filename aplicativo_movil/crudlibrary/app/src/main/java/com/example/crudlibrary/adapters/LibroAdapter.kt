package com.example.crudlibrary.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.crudlibrary.R
import com.example.crudlibrary.models.libro
import com.example.crudlibrary.detalleLibro

class LibroAdapter(private val context: Context, private val libros: List<libro>) :
    RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_libro, parent, false)
        return LibroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libros[position]
        holder.bind(libro)
    }

    override fun getItemCount(): Int {
        return libros.size
    }

    inner class LibroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titulo: TextView = itemView.findViewById(R.id.titulo)
        private val autor: TextView = itemView.findViewById(R.id.autor)
        private val isbn: TextView = itemView.findViewById(R.id.isbn)
        private val genero: TextView = itemView.findViewById(R.id.genero)
        private val btnEditar: Button = itemView.findViewById(R.id.btnEditar)

        fun bind(libro: libro) {
            titulo.text = libro.titulo
            autor.text = libro.autor
            isbn.text = libro.isbn
            genero.text = libro.genero

            btnEditar.setOnClickListener {
                val intent = Intent(context, detalleLibro::class.java)
                intent.putExtra("ID_LIBRO", libro.id)
                context.startActivity(intent)
            }
        }
    }
}