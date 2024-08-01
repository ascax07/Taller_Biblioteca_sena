package com.example.crudlibrary

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibrary.config.config
import com.example.crudlibrary.models.prestamo
import com.google.gson.Gson
import org.json.JSONObject

class guardar_prestamo : AppCompatActivity() {

    private lateinit var txtFechaPrestamo: EditText
    private lateinit var txtFechaDevolucion: EditText
    private lateinit var txtUsuarioPrestamo: EditText
    private lateinit var txtLibro: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnVolver: Button

    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guardar_prestamo) // Usa el archivo de layout correcto

        txtFechaPrestamo = findViewById(R.id.txt_fecha_prestamo)
        txtFechaDevolucion = findViewById(R.id.txt_fecha_devolucion)
        txtUsuarioPrestamo = findViewById(R.id.txt_usuario_prestamo)
        txtLibro = findViewById(R.id.txt_libro)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnVolver = findViewById(R.id.btnVolver)

        btnGuardar.setOnClickListener {
            guardarPrestamo()
        }

        btnVolver.setOnClickListener {
            finish()
        }

        consultarPrestamo()
    }

    private fun consultarPrestamo() {
        if (id.isNotEmpty()) {
            val request = JsonObjectRequest(
                Request.Method.GET,
                config.urlPrestamo + id,
                null,
                { response ->
                    val gson = Gson()
                    val prestamo: prestamo = gson.fromJson(response.toString(), prestamo::class.java)
                    txtFechaPrestamo.setText(response.getString("fecha_prestamo"))
                    txtFechaDevolucion.setText(response.getString("fecha_devolucion"))
                    txtUsuarioPrestamo.setText(response.getString("usuario"))
                    txtLibro.setText(response.getString("libro"))
                },
                { error ->
                    Toast.makeText(this, "Error al consultar", Toast.LENGTH_LONG).show()
                }
            )
            val queue = Volley.newRequestQueue(this)
            queue.add(request)
        }
    }

    private fun guardarPrestamo() {
        try {
            val parametros = JSONObject()
            parametros.put("fecha_prestamo", txtFechaPrestamo.text.toString())
            parametros.put("fecha_devolucion", txtFechaDevolucion.text.toString())
            parametros.put("usuario", txtUsuarioPrestamo.text.toString())
            parametros.put("libro", txtLibro.text.toString())

            val request = JsonObjectRequest(
                Request.Method.POST,
                config.urlPrestamo,
                parametros,
                { response ->
                    Toast.makeText(this, "Se guardó correctamente", Toast.LENGTH_LONG).show()
                },
                { error ->
                    Toast.makeText(this, "Se generó un error", Toast.LENGTH_LONG).show()
                }
            )

            val queue = Volley.newRequestQueue(this)
            queue.add(request)
        } catch (error: Exception) {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_LONG).show()
        }
    }
}
