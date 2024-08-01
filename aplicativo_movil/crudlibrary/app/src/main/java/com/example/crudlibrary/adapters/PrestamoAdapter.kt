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
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibrary.R
import com.example.crudlibrary.config.config
import com.example.crudlibrary.detalle_prestamo
import com.example.crudlibrary.models.prestamo

class PrestamoAdapter(private val context: Context, private val prestamos: MutableList<prestamo>) :
    RecyclerView.Adapter<PrestamoAdapter.PrestamoViewHolder>() {

    private val requestQueue = Volley.newRequestQueue(context) // Inicializar requestQueue

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrestamoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_prestamo, parent, false)
        return PrestamoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrestamoViewHolder, position: Int) {
        val prestamo = prestamos[position]
        holder.bind(prestamo)
    }

    override fun getItemCount(): Int {
        return prestamos.size
    }

    inner class PrestamoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fechaPrestamo: TextView = itemView.findViewById(R.id.fecha_prestamo)
        private val fechaDevolucion: TextView = itemView.findViewById(R.id.fecha_devolucion)
        private val usuario: TextView = itemView.findViewById(R.id.usuario)
        private val libro: TextView = itemView.findViewById(R.id.libro)
        private val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        private val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)

        fun bind(prestamo: prestamo) {
            fechaPrestamo.text = prestamo.fecha_prestamo
            fechaDevolucion.text = prestamo.fecha_devolucion
            usuario.text = "${prestamo.usuario.nombre} ${prestamo.usuario.correo_electronico}" // Concatenar el nombre y correo del usuario
            libro.text = "${prestamo.libro.titulo} ${prestamo.libro.isbn}" // Concatenar el titulo y ISBN del libro


            btnEditar.setOnClickListener {
                val intent = Intent(context, detalle_prestamo::class.java)
                intent.putExtra("ID_PRESTAMO", prestamo.id)
                context.startActivity(intent)
            }

            btnEliminar.setOnClickListener {
                AlertDialog.Builder(it.context).apply {
                    setTitle("Confirmación")
                    setMessage("¿Estás seguro que quieres eliminar este préstamo?")
                    setPositiveButton("Sí") { dialog, _ ->
                        eliminarPrestamo(prestamo.id, position)
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

    private fun eliminarPrestamo(prestamoId: String, position: Int) {
        val url = "${config.urlPrestamo}$prestamoId" // Usando la URL desde config

        val request = StringRequest(
            Request.Method.DELETE,
            url,
            Response.Listener {
                prestamos.removeAt(position)
                notifyItemRemoved(position)
                Toast.makeText(context, "Préstamo eliminado", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(context, "Error al eliminar el préstamo", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(request)
    }
}
