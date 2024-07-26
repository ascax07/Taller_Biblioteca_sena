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
import com.google.gson.Gson
import org.json.JSONObject

class detalleLibro : AppCompatActivity() {

    private lateinit var lblautor: EditText
    private lateinit var lbltitulo: EditText
    private lateinit var lblgenero: EditText
    private lateinit var lblcod_isbn: EditText
    private lateinit var lblcant_dis: EditText
    private lateinit var lblcant_ocup: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnAtras: Button // Añadir variable para el botón de atrás
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_detalle_libro)

        lblautor = findViewById(R.id.lblautor)
        lbltitulo = findViewById(R.id.lbltitulo)
        lblgenero = findViewById(R.id.lblgenero)
        lblcod_isbn = findViewById(R.id.lblcod_isbn)
        lblcant_dis = findViewById(R.id.lblcant_dis)
        lblcant_ocup = findViewById(R.id.lblcant_ocup)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnAtras = findViewById(R.id.btnAtras) // Encontrar el botón de atrás

        id = intent.getStringExtra("ID_LIBRO")
        consultarLibro()

        btnGuardar.setOnClickListener {
            actualizarLibro()
        }

        btnAtras.setOnClickListener {
            finish() // Finaliza la actividad actual y vuelve a la anterior
        }
    }

    private fun consultarLibro() {
        if (!id.isNullOrEmpty()) {
            val url = config.urlLibro + id
            val request = JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                { response ->
                    try {
                        val gson = Gson()
                        val libro: libro = gson.fromJson(response.toString(), libro::class.java)
                        lblautor.setText(libro.autor)
                        lbltitulo.setText(libro.titulo)
                        lblgenero.setText(libro.genero)
                        lblcod_isbn.setText(libro.isbn)
                        lblcant_dis.setText(libro.num_ejem_dis.toString())
                        lblcant_ocup.setText(libro.num_ejem_ocup.toString())
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
            Toast.makeText(this, "ID del libro es nulo o vacío", Toast.LENGTH_LONG).show()
        }
    }

    private fun actualizarLibro() {
        if (!id.isNullOrEmpty()) {
            val url = config.urlLibro + id
            val libroActualizado = libro(
                id = id!!,
                autor = lblautor.text.toString(),
                titulo = lbltitulo.text.toString(),
                genero = lblgenero.text.toString(),
                isbn = lblcod_isbn.text.toString(),
                num_ejem_dis = lblcant_dis.text.toString().toInt(),
                num_ejem_ocup = lblcant_ocup.text.toString().toInt()
            )
            val jsonBody = JSONObject(Gson().toJson(libroActualizado))
            val request = JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonBody,
                { error ->
                    Toast.makeText(this, "Error al actualizar: ${error}", Toast.LENGTH_LONG).show()
                },
                { response ->
                    Toast.makeText(this, "Libro actualizado correctamente", Toast.LENGTH_LONG).show()
                },

            )
            Volley.newRequestQueue(this).add(request)
        } else {
            Toast.makeText(this, "ID del libro es nulo o vacío", Toast.LENGTH_LONG).show()
        }
    }

}