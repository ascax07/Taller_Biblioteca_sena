package com.example.crudlibrary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import android.widget.Toast
import com.example.crudlibrary.config.config
import com.example.crudlibrary.models.libro
import com.example.crudlibrary.models.prestamo
import com.example.crudlibrary.models.usuario
import com.google.gson.Gson
import org.json.JSONObject

class detalle_prestamo : AppCompatActivity() {

    private lateinit var lblid: EditText
    private lateinit var lblfecha_prestamo: EditText
    private lateinit var lblfecha_devolucion: EditText
    private lateinit var lblusuario_prestamo: EditText
    private lateinit var lbllibro: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnAtras: Button
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalle_prestamo)

        lblid = findViewById(R.id.lblid)
        lblfecha_prestamo = findViewById(R.id.lblfecha_prestamo)
        lblfecha_devolucion = findViewById(R.id.lblfecha_devolucion)
        lblusuario_prestamo = findViewById(R.id.lblusuario_prestamo)
        lbllibro = findViewById(R.id.lbllibro)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnAtras = findViewById(R.id.btnAtras)

        id = intent.getStringExtra("ID_PRESTAMO")
        consultarPrestamo()

        btnGuardar.setOnClickListener {
            actualizarPrestamo()
        }

        btnAtras.setOnClickListener {
            finish()
        }
    }

    private fun consultarPrestamo() {
        if (!id.isNullOrEmpty()) {
            val url = config.urlPrestamo + id
            val request = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                { response ->
                    try {
                        val gson = Gson()
                        val prestamo: prestamo = gson.fromJson(response.toString(), prestamo::class.java)
                        lblid.setText(prestamo.id)
                        lblfecha_prestamo.setText(prestamo.fecha_prestamo)
                        lblfecha_devolucion.setText(prestamo.fecha_devolucion)
                        // Convertir usuario a una cadena que se pueda mostrar
                        val usuarioTexto = "${prestamo.usuario.nombre} (${prestamo.usuario.correo_electronico})"
                        lblusuario_prestamo.setText(usuarioTexto)
                        // Convertir libro a una cadena que se pueda mostrar
                        val libroTexto = "${prestamo.libro.titulo} (${prestamo.libro.isbn})"
                        lbllibro.setText(libroTexto)

                    } catch (e: Exception) {
                        Toast.makeText(this, "Error al procesar los datos", Toast.LENGTH_LONG).show()
                    }
                },
                { error ->
                    Toast.makeText(this, "Error al consultar: ${error.message}", Toast.LENGTH_LONG).show()
                }
            )
            Volley.newRequestQueue(this).add(request)
        } else {
            Toast.makeText(this, "ID del préstamo es nulo o vacío", Toast.LENGTH_LONG).show()
        }
    }

    private fun actualizarPrestamo() {
        if (!id.isNullOrEmpty()) {
            val url = config.urlPrestamo + id

            // Construir el objeto Usuario a partir del texto recibido
            val usuario = usuario(
                id = "", // Proporciona un valor adecuado si es necesario
                nombre = lblusuario_prestamo.text.toString(), // Ajusta según el formato de tu texto
                direccion = "", // Proporciona un valor adecuado
                correo_electronico = "", // Proporciona un valor adecuado
                tipo_usuario = "" // Proporciona un valor adecuado
            )

            // Construir el objeto Libro a partir del texto recibido
            val libro = libro(
                id = "", // Proporciona un valor adecuado si es necesario
                titulo = lbllibro.text.toString(), // Asume que el título se obtiene del TextView
                autor = "", // Proporciona un valor adecuado
                isbn = "", // Proporciona un valor adecuado
                genero = "", // Proporciona un valor adecuado
                num_ejem_dis = 0, // Proporciona un valor adecuado
                num_ejem_ocup = 0 // Proporciona un valor adecuado
            )

            val prestamoActualizado = prestamo(
                id = lblid.text.toString(),
                fecha_prestamo = lblfecha_prestamo.text.toString(),
                fecha_devolucion = lblfecha_devolucion.text.toString(),
                usuario = usuario, // Aquí se asigna el objeto Usuario
                libro = libro // Aquí se asigna el objeto Libro
            )

            val jsonBody = JSONObject(Gson().toJson(prestamoActualizado))
            val request = JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonBody,
                { error ->
                    Toast.makeText(this, "Error al actualizar: ${error}", Toast.LENGTH_LONG).show()
                },
                { response ->
                    Toast.makeText(this, "Préstamo actualizado correctamente", Toast.LENGTH_LONG).show()
                }
            )
            Volley.newRequestQueue(this).add(request)
        } else {
            Toast.makeText(this, "ID del préstamo es nulo o vacío", Toast.LENGTH_LONG).show()
        }
    }



}
