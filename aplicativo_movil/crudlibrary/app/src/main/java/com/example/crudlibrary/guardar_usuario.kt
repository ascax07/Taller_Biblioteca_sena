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
import com.example.crudlibrary.models.usuario
import com.google.gson.Gson
import org.json.JSONObject

class guardar_usuario : AppCompatActivity() {

    private lateinit var txt_nombre: EditText
    private lateinit var txt_direccion: EditText
    private lateinit var txt_correo_electronico: EditText
    private lateinit var txt_tipo_usuario: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnVolver: Button



    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guardar_usuario) // Usa el archivo de layout correcto

        txt_nombre = findViewById(R.id.txt_nombre)
        txt_direccion = findViewById(R.id.txt_direccion)
        txt_correo_electronico = findViewById(R.id.txt_correo_electronico)
        txt_tipo_usuario = findViewById(R.id.txt_tipo_usuario)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnVolver = findViewById(R.id.btnVolver)

        btnGuardar.setOnClickListener {
            guardarLibro()
        }

        btnVolver.setOnClickListener {
            finish()
        }

        consultarLibro()
    }

    private fun consultarLibro() {
        if (id.isNotEmpty()) {
            val request = JsonObjectRequest(
                Request.Method.GET,
                config.urlUsuario + id,
                null,
                { response ->
                    val gson = Gson()
                    val libro: usuario = gson.fromJson(response.toString(), usuario::class.java)
                    txt_nombre.setText(response.getString("nombre"))
                    txt_direccion.setText(response.getString("direccion"))
                    txt_correo_electronico.setText(response.getString("correo_electronico"))
                    txt_tipo_usuario.setText(response.getString("tipo_usuario"))

                },
                { error ->
                    Toast.makeText(this, "Error al consultar", Toast.LENGTH_LONG).show()
                }
            )
            val queue = Volley.newRequestQueue(this)
            queue.add(request)
        }
    }

    private fun guardarLibro() {
        try {
            val parametros = JSONObject()
            parametros.put("nombre", txt_nombre.text.toString())
            parametros.put("direccion", txt_direccion.text.toString())
            parametros.put("correo_electronico", txt_correo_electronico.text.toString())
            parametros.put("tipo_usuario", txt_tipo_usuario.text.toString())


            val request = JsonObjectRequest(
                Request.Method.POST,
                config.urlUsuario,
                parametros,
                { response ->
                    Toast.makeText(this, "Se guardo correctamente", Toast.LENGTH_LONG).show()
                },
                { error ->
                    Toast.makeText(this, "Se genero un error", Toast.LENGTH_LONG).show()
                }
            )

            val queue = Volley.newRequestQueue(this)
            queue.add(request)
        } catch (error: Exception) {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_LONG).show()
        }
    }
}