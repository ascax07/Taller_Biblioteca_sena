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
import com.example.crudlibrary.models.usuario
import com.google.gson.Gson
import org.json.JSONObject

class detalle_usuario : AppCompatActivity() {

    private lateinit var lblnombre: EditText
    private lateinit var lbldireccion: EditText
    private lateinit var lblcorreo_electronico: EditText
    private lateinit var lbltipo_usuario: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnAtras: Button
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detalle_usuario)

        lblnombre = findViewById(R.id.lblnombre)
        lbldireccion = findViewById(R.id.lbldireccion)
        lblcorreo_electronico = findViewById(R.id.lblcorreo_electronico)
        lbltipo_usuario = findViewById(R.id.lbltipo_usuario)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnAtras = findViewById(R.id.btnAtras)

        id = intent.getStringExtra("ID_USUARIO")
        consultarUsuario()

        btnGuardar.setOnClickListener {
            actualizarUsuario()
        }

        btnAtras.setOnClickListener {
            finish() // Finaliza la actividad actual y vuelve a la anterior
        }
    }

    private fun consultarUsuario() {
        if (!id.isNullOrEmpty()) {
            val url = config.urlUsuario + id
            val request = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                { response ->
                    try {
                        val gson = Gson()
                        val usuario: usuario = gson.fromJson(response.toString(), usuario::class.java)
                        lblnombre.setText(usuario.nombre)
                        lbldireccion.setText(usuario.direccion)
                        lblcorreo_electronico.setText(usuario.correo_electronico)
                        lbltipo_usuario.setText(usuario.tipo_usuario)
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
            Toast.makeText(this, "ID del usuario es nulo o vacío", Toast.LENGTH_LONG).show()
        }
    }

    private fun actualizarUsuario() {
        if (!id.isNullOrEmpty()) {
            val url = config.urlUsuario + id
            val usuarioActualizado = usuario(
                id = id!!,
                nombre = lblnombre.text.toString(),
                direccion = lbldireccion.text.toString(),
                correo_electronico = lblcorreo_electronico.text.toString(),
                tipo_usuario = lbltipo_usuario.text.toString()
            )
            val jsonBody = JSONObject(Gson().toJson(usuarioActualizado))
            val request = JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonBody,
                { response ->
                    Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_LONG).show()
                },
                { error ->
                    Toast.makeText(this, "Error al actualizar: ${error}", Toast.LENGTH_LONG).show()
                }
            )
            Volley.newRequestQueue(this).add(request)
        } else {
            Toast.makeText(this, "ID del usuario es nulo o vacío", Toast.LENGTH_LONG).show()
        }
    }
}
