package com.example.crudlibrary

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibrary.config.config
import com.example.crudlibrary.models.libro
import com.example.crudlibrary.models.usuario
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class guardar_prestamo : AppCompatActivity() {

    private lateinit var txtFechaPrestamo: EditText
    private lateinit var txtFechaDevolucion: EditText
    private lateinit var spinnerUsuario: Spinner
    private lateinit var spinnerLibro: Spinner
    private lateinit var txt_estado: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnVolver: Button

    private lateinit var usuarios: List<usuario>
    private lateinit var libros: List<libro>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guardar_prestamo)

        txtFechaPrestamo = findViewById(R.id.txt_fecha_prestamo)
        txtFechaDevolucion = findViewById(R.id.txt_fecha_devolucion)
        spinnerUsuario = findViewById(R.id.spinner_usuario)
        spinnerLibro = findViewById(R.id.spinner_libro)
        txt_estado = findViewById(R.id.txt_estado)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnVolver = findViewById(R.id.btnVolver)

        btnGuardar.setOnClickListener {
            guardarPrestamo()
        }

        btnVolver.setOnClickListener {
            finish()
        }

        cargarUsuarios()
        cargarLibros()
    }

    private fun cargarUsuarios() {
        val request = JsonArrayRequest(Request.Method.GET, config.urlUsuario, null,
            { response ->
                val gson = Gson()
                val userType = object : TypeToken<List<usuario>>() {}.type
                val usuarios: List<usuario> = gson.fromJson(response.toString(), userType)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, usuarios)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerUsuario.adapter = adapter
            },
            { error ->
                Toast.makeText(this, "Error al cargar usuarios: ${error.message}", Toast.LENGTH_LONG).show()
            })
        Volley.newRequestQueue(this).add(request)
    }

    private fun cargarLibros() {
        val request = JsonArrayRequest(Request.Method.GET, config.urlLibro, null,
            { response ->
                val gson = Gson()
                val bookType = object : TypeToken<List<libro>>() {}.type
                val libros: List<libro> = gson.fromJson(response.toString(), bookType)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, libros)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerLibro.adapter = adapter
            },
            { error ->
                Toast.makeText(this, "Error al cargar libros: ${error.message}", Toast.LENGTH_LONG).show()
            })
        Volley.newRequestQueue(this).add(request)
    }


    private fun guardarPrestamo() {
        try {
            val usuarioSeleccionado = spinnerUsuario.selectedItem as usuario
            val libroSeleccionado = spinnerLibro.selectedItem as libro

            val parametros = JSONObject()
            parametros.put("fecha_prestamo", txtFechaPrestamo.text.toString())
            parametros.put("fecha_devolucion", txtFechaDevolucion.text.toString())
            parametros.put("usuario_id", usuarioSeleccionado.id)
            parametros.put("libro_id", libroSeleccionado.id)
            parametros.put("estado", txt_estado.text.toString())

            val request = JsonObjectRequest(
                Request.Method.POST,
                config.urlPrestamo,
                parametros,
                { response ->
                    Toast.makeText(this, "Se guardó correctamente", Toast.LENGTH_LONG).show()
                },
                { error ->
                    Toast.makeText(this, "Se generó un error: ${String(error.networkResponse.data)}", Toast.LENGTH_LONG).show()
                }
            )

            val queue = Volley.newRequestQueue(this)
            queue.add(request)
        } catch (error: Exception) {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_LONG).show()
        }
    }

}
