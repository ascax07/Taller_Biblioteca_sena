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
import com.example.crudlibrary.models.multa
import com.example.crudlibrary.models.prestamo
import com.example.crudlibrary.models.usuario
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class guardar_multa : AppCompatActivity() {

    private lateinit var txtValorMulta: EditText
    private lateinit var txtFechaMulta: EditText
    private lateinit var txtEstado: EditText
    private lateinit var spinnerUsuario: Spinner
    private lateinit var spinnerPrestamo: Spinner
    private lateinit var btnGuardar: Button
    private lateinit var btnVolver: Button

    private lateinit var usuarios: List<usuario>
    private lateinit var prestamos: List<prestamo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guardar_multa)

        txtValorMulta = findViewById(R.id.txt_valor_multa)
        txtFechaMulta = findViewById(R.id.txt_fecha_multa)
        txtEstado = findViewById(R.id.txt_estado)
        spinnerUsuario = findViewById(R.id.spinner_usuario)
        spinnerPrestamo = findViewById(R.id.spinner_prestamo)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnVolver = findViewById(R.id.btnVolver)

        btnGuardar.setOnClickListener {
            guardarMulta()
        }

        btnVolver.setOnClickListener {
            finish()
        }

        cargarUsuarios()
        cargarPrestamos()
    }

    private fun cargarUsuarios() {
        val request = JsonArrayRequest(Request.Method.GET, config.urlUsuario, null,
            { response ->
                val gson = Gson()
                val userType = object : TypeToken<List<usuario>>() {}.type
                usuarios = gson.fromJson(response.toString(), userType)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, usuarios)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerUsuario.adapter = adapter
            },
            { error ->
                Toast.makeText(this, "Error al cargar usuarios: ${error.message}", Toast.LENGTH_LONG).show()
            })
        Volley.newRequestQueue(this).add(request)
    }

    private fun cargarPrestamos() {
        val request = JsonArrayRequest(Request.Method.GET, config.urlPrestamo, null,
            { response ->
                val gson = Gson()
                val prestamoType = object : TypeToken<List<prestamo>>() {}.type
                prestamos = gson.fromJson(response.toString(), prestamoType)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, prestamos)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerPrestamo.adapter = adapter
            },
            { error ->
                Toast.makeText(this, "Error al cargar préstamos: ${error.message}", Toast.LENGTH_LONG).show()
            })
        Volley.newRequestQueue(this).add(request)
    }

    private fun guardarMulta() {
        try {
            val usuarioSeleccionado = spinnerUsuario.selectedItem as usuario
            val prestamoSeleccionado = spinnerPrestamo.selectedItem as prestamo

            val parametros = JSONObject()
            parametros.put("valor_multa", txtValorMulta.text.toString())
            parametros.put("fecha_multa", txtFechaMulta.text.toString())
            parametros.put("estado", txtEstado.text.toString())
            parametros.put("usuario_id", usuarioSeleccionado.id)
            parametros.put("prestamo_id", prestamoSeleccionado.id)

            val request = JsonObjectRequest(
                Request.Method.POST,
                config.urlMulta,
                parametros,
                { response ->
                    Toast.makeText(this, "Se guardó correctamente", Toast.LENGTH_LONG).show()
                },
                { error ->
                    Toast.makeText(this, "Se generó un error: ${String(error.networkResponse.data)}", Toast.LENGTH_LONG).show()
                }
            )

            Volley.newRequestQueue(this).add(request)
        } catch (error: Exception) {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_LONG).show()
        }
    }
}
