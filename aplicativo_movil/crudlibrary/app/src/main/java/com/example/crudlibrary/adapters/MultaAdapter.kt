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
import com.example.crudlibrary.detalle_multa
import com.example.crudlibrary.models.multa

class MultaAdapter(private val context: Context, private val multas: MutableList<multa>) :
    RecyclerView.Adapter<MultaAdapter.MultaViewHolder>() {

    private val requestQueue = Volley.newRequestQueue(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_multa, parent, false) // Asegúrate de tener un layout llamado item_multa.xml
        return MultaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MultaViewHolder, position: Int) {
        val multa = multas[position]
        holder.bind(multa)
    }

    override fun getItemCount(): Int {
        return multas.size
    }

    inner class MultaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val id: TextView = itemView.findViewById(R.id.id)
        private val fechaMulta: TextView = itemView.findViewById(R.id.fecha_multa)
        private val usuario: TextView = itemView.findViewById(R.id.usuario)
        private val prestamo: TextView = itemView.findViewById(R.id.prestamo)
        private val valor_multa: TextView = itemView.findViewById(R.id.valor_multa)

        private val btnEditar: Button = itemView.findViewById(R.id.btnEditar)
        private val btnEliminar: Button = itemView.findViewById(R.id.btnEliminar)

        fun bind(multa: multa) {
            id.text = multa.id
            fechaMulta.text = multa.fecha_multa
            usuario.text = "${multa.usuario.nombre} ${multa.usuario.correo_electronico}"
            prestamo.text = "${multa.prestamo.fecha_devolucion} ${multa.prestamo.usuario}"
            valor_multa.text = multa.valor_multa.toString()

            btnEditar.setOnClickListener {
                val intent = Intent(context, detalle_multa::class.java)
                intent.putExtra("ID_MULTA", multa.id)
                context.startActivity(intent)
            }

            btnEliminar.setOnClickListener {
                AlertDialog.Builder(it.context).apply {
                    setTitle("Confirmación")
                    setMessage("¿Estás seguro que quieres eliminar esta multa?")
                    setPositiveButton("Sí") { dialog, _ ->
                        eliminarMulta(multa.id, position)
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

    private fun eliminarMulta(multaId: String, position: Int) {
        val url = "${config.urlMulta}$multaId" // Usando la URL desde config

        val request = StringRequest(
            Request.Method.DELETE,
            url,
            Response.Listener {
                multas.removeAt(position)
                notifyItemRemoved(position)
                Toast.makeText(context, "Multa eliminada", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(context, "No se puede eliminar la multa", Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(request)
    }
}
