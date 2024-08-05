package com.example.crudlibrary

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.crudlibrary.config.config
import com.example.crudlibrary.models.libro
import com.example.crudlibrary.models.prestamo
import com.example.crudlibrary.models.usuario
import com.example.crudlibrary.models.multa
import com.google.gson.Gson
import org.json.JSONObject

class detalle_multa : AppCompatActivity() {

    private lateinit var lblid: EditText
    private lateinit var lblvalor_multa: EditText
    private lateinit var lblfecha_multa: EditText
    private lateinit var lblestado: EditText
    private lateinit var spinnerUsuario: Spinner
    private lateinit var spinnerPrestamo: Spinner
    private lateinit var btnGuardar: Button
    private lateinit var btnAtras: Button
    private var id: String? = null
    private var usuarioId: String? = null
    private var prestamoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalle_multa)

        lblid = findViewById(R.id.lblid)
        lblvalor_multa = findViewById(R.id.lblvalor_multa)
        lblfecha_multa = findViewById(R.id.lblfecha_multa)
        lblestado = findViewById(R.id.lblestado)
        spinnerUsuario = findViewById(R.id.spinnerUsuario)
        spinnerPrestamo = findViewById(R.id.spinnerPrestamo)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnAtras = findViewById(R.id.btnAtras)

        id = intent.getStringExtra("ID_MULTA")
        consultarMulta()

        cargarUsuarios()
        cargarPrestamos()

        spinnerUsuario.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                usuarioId = (parent.adapter.getItem(position) as usuario).id
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        spinnerPrestamo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                prestamoId = (parent.adapter.getItem(position) as prestamo).id
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        btnGuardar.setOnClickListener {
            actualizarMulta()
        }

        btnAtras.setOnClickListener {
            finish()
        }
    }

    private fun cargarUsuarios() {
        val url = config.urlUsuario
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val usuarios = ArrayList<usuario>()
                for (i in 0 until response.length()) {
                    val userJson = response.getJSONObject(i)
                    val usuario = Gson().fromJson(userJson.toString(), usuario::class.java)
                    usuarios.add(usuario)
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, usuarios)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerUsuario.adapter = adapter
            },
            { error ->
                Toast.makeText(this, "Error al cargar usuarios: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )
        Volley.newRequestQueue(this).add(request)
    }

    private fun cargarPrestamos() {
        val url = config.urlPrestamo
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val prestamos = ArrayList<prestamo>()
                for (i in 0 until response.length()) {
                    val prestamoJson = response.getJSONObject(i)
                    val prestamo = Gson().fromJson(prestamoJson.toString(), prestamo::class.java)
                    prestamos.add(prestamo)
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, prestamos)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerPrestamo.adapter = adapter
            },
            { error ->
                Toast.makeText(this, "Error al cargar préstamos: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )
        Volley.newRequestQueue(this).add(request)
    }





    private fun consultarMulta() {
        if (!id.isNullOrEmpty()) {
            val url = "${config.urlMulta}$id"
            val request = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                { response ->
                    try {
                        val gson = Gson()
                        val multa: multa = gson.fromJson(response.toString(), multa::class.java)
                        lblid.setText(multa.id)
                        lblvalor_multa.setText(multa.valor_multa.toString())
                        lblfecha_multa.setText(multa.fecha_multa)
                        lblestado.setText(multa.estado)
                        usuarioId = multa.usuario.id
                        prestamoId = multa.prestamo.id

                        // Espera a que los spinners estén llenos antes de seleccionar
                        spinnerUsuario.post {
                            val usuarioPosition = (spinnerUsuario.adapter as ArrayAdapter<usuario>).getPosition(multa.usuario)
                            spinnerUsuario.setSelection(usuarioPosition)
                        }
                        spinnerPrestamo.post {
                            val prestamoPosition = (spinnerPrestamo.adapter as ArrayAdapter<prestamo>).getPosition(multa.prestamo)
                            spinnerPrestamo.setSelection(prestamoPosition)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
                { error ->
                    Toast.makeText(this, "Error al consultar: ${error.message}", Toast.LENGTH_LONG).show()
                }
            )
            Volley.newRequestQueue(this).add(request)
        } else {
            Toast.makeText(this, "ID de la multa es nulo o vacío", Toast.LENGTH_LONG).show()
        }
    }

    private fun actualizarMulta() {
        if (!id.isNullOrEmpty()) {
            val url = "${config.urlMulta}$id"

            val multaActualizada = multa(
                id = lblid.text.toString(),
                valor_multa = lblvalor_multa.text.toString().toDoubleOrNull() ?: 0.0,
                fecha_multa = lblfecha_multa.text.toString(),
                estado = lblestado.text.toString(),
                usuario = usuario(
                    id = usuarioId ?: "",
                    nombre = "",
                    direccion = "",
                    correo_electronico = "",
                    tipo_usuario = ""
                ),
                prestamo = prestamo(
                    id = prestamoId ?: "",
                    fecha_prestamo = "",
                    fecha_devolucion = "",
                    usuario = usuario(
                        id = "",
                        nombre = "",
                        direccion = "",
                        correo_electronico = "",
                        tipo_usuario = ""
                    ),
                    libro = libro(
                        id = "",
                        titulo = "",
                        autor = "",
                        isbn = "",
                        genero = "",
                        num_ejem_dis = 0,
                        num_ejem_ocup = 0
                    ),
                    estado = ""
                )
            )

            val jsonBody = JSONObject(Gson().toJson(multaActualizada))
            val request = JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonBody,
                { response ->
                    Toast.makeText(this, "Multa actualizada correctamente", Toast.LENGTH_LONG).show()
                },
                { error ->
                    Toast.makeText(this, "Error al actualizar: ${error.message}", Toast.LENGTH_LONG).show()
                }
            )
            Volley.newRequestQueue(this).add(request)
        } else {
            Toast.makeText(this, "ID de la multa es nulo o vacío", Toast.LENGTH_LONG).show()
        }
    }

}
