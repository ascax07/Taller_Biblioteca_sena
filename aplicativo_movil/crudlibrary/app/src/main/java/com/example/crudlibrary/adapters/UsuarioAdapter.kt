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
import com.example.crudlibrary.views.usuario.detalle_usuario
import com.example.crudlibrary.models.usuario

class UsuarioAdapter(private val context: Context, private val usuario: MutableList<usuario>) :
    RecyclerView.Adapter<UsuarioAdapter.usuarioViewHolder>() {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): usuarioViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_usuario, parent, false)
        return usuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: usuarioViewHolder, position: Int) {
        val usuario = usuario[position]
        holder.bind(usuario, position)
    }

    override fun getItemCount(): Int {
        return usuario.size
    }

    inner class usuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.titulo)
        private val direccion: TextView = itemView.findViewById(R.id.autor)
        private val correo_electronico: TextView = itemView.findViewById(R.id.isbn)
        private val tipo_usuario: TextView = itemView.findViewById(R.id.genero)
        private val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        private val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)

        fun bind(usuario: usuario, position: Int) {
            nombre.text = usuario.nombre
            direccion.text = usuario.direccion
            correo_electronico.text = usuario.correo_electronico
            tipo_usuario.text = usuario.tipo_usuario

            btnEditar.setOnClickListener {
                val intent = Intent(context, detalle_usuario::class.java)
                intent.putExtra("ID_LIBRO", usuario.id)
                context.startActivity(intent)
            }

            btnEliminar.setOnClickListener {
                AlertDialog.Builder(it.context).apply {
                    setTitle("Confirmación")
                    setMessage("¿Estás seguro que quieres eliminar este libro?")
                    setPositiveButton("Sí") { dialog, _ ->
                        eliminarLibro(usuario.id, position)
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
                usuario.removeAt(position)
                notifyItemRemoved(position)
                Toast.makeText(context, "usuario eliminado", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(context, "Error al eliminar el usuario", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(request)
    }
}

