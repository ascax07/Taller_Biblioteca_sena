package com.example.crudlibrary

import android.os.Bundle
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
import com.google.gson.Gson
import org.json.JSONObject

class detalle_prestamo : AppCompatActivity() {

    private lateinit var lblid: EditText
    private lateinit var lblfecha_prestamo: EditText
    private lateinit var lblfecha_devolucion: EditText
    private lateinit var spinnerUsuario: Spinner
    private lateinit var spinnerLibro: Spinner
    private lateinit var lblestado: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnAtras: Button
    private var id: String? = null
    private var usuarioId: String? = null
    private var libroId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detalle_prestamo)

        lblid = findViewById(R.id.lblid)
        lblfecha_prestamo = findViewById(R.id.lblfecha_prestamo)
        lblfecha_devolucion = findViewById(R.id.lblfecha_devolucion)
        spinnerUsuario = findViewById(R.id.spinnerUsuario)
        spinnerLibro = findViewById(R.id.spinnerLibro)
        lblestado = findViewById(R.id.lblestado)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnAtras = findViewById(R.id.btnAtras)

        id = intent.getStringExtra("ID_PRESTAMO")
        consultarPrestamo()

        cargarUsuarios()
        cargarLibros()

        spinnerUsuario.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                usuarioId = (parent.adapter.getItem(position) as usuario).id
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        spinnerLibro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                libroId = (parent.adapter.getItem(position) as libro).id
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        btnGuardar.setOnClickListener {
            actualizarPrestamo()
        }

        btnAtras.setOnClickListener {
            finish()
        }
    }

    private fun cargarUsuarios() {
        val url = "${config.urlUsuario}"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val usuarios = ArrayList<usuario>()
                for (i in 0 until response.length()) {
                    val userJson = response.getJSONObject(i)
                    val usuario = usuario(
                        id = userJson.getString("id"),
                        nombre = userJson.getString("nombre"),
                        direccion = userJson.getString("direccion"),
                        correo_electronico = userJson.getString("correo_electronico"),
                        tipo_usuario = userJson.getString("tipo_usuario")
                    )
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

    private fun cargarLibros() {
        val url = "${config.urlLibro}"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val libros = ArrayList<libro>()
                for (i in 0 until response.length()) {
                    val libroJson = response.getJSONObject(i)
                    val libro = libro(
                        id = libroJson.getString("id"),
                        titulo = libroJson.getString("titulo"),
                        autor = libroJson.getString("autor"),
                        isbn = libroJson.getString("isbn"),
                        genero = libroJson.getString("genero"),
                        num_ejem_dis = libroJson.getInt("num_ejem_dis"),
                        num_ejem_ocup = libroJson.getInt("num_ejem_ocup")
                    )
                    libros.add(libro)
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, libros)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerLibro.adapter = adapter
            },
            { error ->
                Toast.makeText(this, "Error al cargar libros: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )
        Volley.newRequestQueue(this).add(request)
    }

    private fun consultarPrestamo() {
        if (!id.isNullOrEmpty()) {
            val url = "${config.urlPrestamo}$id"
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
                        lblestado.setText(prestamo.estado)
                        usuarioId = prestamo.usuario.id
                        libroId = prestamo.libro.id

                        // Seleccionar el usuario y el libro en los spinners
                        val usuarioPosition = (spinnerUsuario.adapter as ArrayAdapter<usuario>).getPosition(prestamo.usuario)
                        spinnerUsuario.setSelection(usuarioPosition)
                        val libroPosition = (spinnerLibro.adapter as ArrayAdapter<libro>).getPosition(prestamo.libro)
                        spinnerLibro.setSelection(libroPosition)
                    } catch (e: Exception) {
                        e.printStackTrace() // Solo imprime el stack trace para depuración
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
            val url = "${config.urlPrestamo}$id"

            val prestamoActualizado = prestamo(
                id = lblid.text.toString(),
                fecha_prestamo = lblfecha_prestamo.text.toString(),
                fecha_devolucion = lblfecha_devolucion.text.toString(),
                usuario = usuario(
                    id = usuarioId ?: "",
                    nombre = spinnerUsuario.selectedItem.toString(),
                    direccion = "",
                    correo_electronico = "",
                    tipo_usuario = ""
                ),
                libro = libro(
                    id = libroId ?: "",
                    titulo = spinnerLibro.selectedItem.toString(),
                    autor = "",
                    isbn = "",
                    genero = "",
                    num_ejem_dis = 0,
                    num_ejem_ocup = 0
                ),
                estado = lblestado.text.toString(),
            )

            val jsonBody = JSONObject(Gson().toJson(prestamoActualizado))
            val request = JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonBody,

                { error ->
                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_LONG).show()
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
