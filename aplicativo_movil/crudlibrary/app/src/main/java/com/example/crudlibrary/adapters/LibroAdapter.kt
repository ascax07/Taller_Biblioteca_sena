package com.example.crudlibrary.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibrary.R
import com.example.crudlibrary.config.config
import com.example.crudlibrary.detalleLibro
import com.example.crudlibrary.models.libro

class LibroAdapter(private val context: Context, private val libros: MutableList<libro>) :
    RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_libro, parent, false)
        return LibroViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libros[position]
        holder.bind(libro, position)
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
        private val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)

        fun bind(libro: libro, position: Int) {
            titulo.text = libro.titulo
            autor.text = libro.autor
            isbn.text = libro.isbn
            genero.text = libro.genero

            btnEditar.setOnClickListener {
                val intent = Intent(context, detalleLibro::class.java)
                intent.putExtra("ID_LIBRO", libro.id)
                context.startActivity(intent)
            }

            btnEliminar.setOnClickListener {
                AlertDialog.Builder(it.context).apply {
                    setTitle("Confirmación")
                    setMessage("¿Estás seguro que quieres eliminar este libro?")
                    setPositiveButton("Sí") { dialog, _ ->
                        eliminarLibro(libro.id, position)
                        dialog.dismiss()
                    }
                    setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    create().show()
                }
            }
        }
    }

    private fun eliminarLibro(libroId: String, position: Int) {
        val url = "${config.urlLibro}$libroId" // Usando la URL desde config

        val request = StringRequest(
            Request.Method.DELETE,
            url,
            Response.Listener {
                libros.removeAt(position)
                notifyItemRemoved(position)
                Toast.makeText(context, "Libro eliminado", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(context, "Error al eliminar el libro", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(request)
    }
}

