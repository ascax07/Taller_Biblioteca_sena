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
import com.example.crudlibrary.models.libro
import com.google.gson.Gson
import org.json.JSONObject

class guardarLibro : AppCompatActivity() {

    private lateinit var txtTitulo: EditText
    private lateinit var txtAutor: EditText
    private lateinit var txtGenero: EditText
    private lateinit var txtCodIsbn: EditText
    private lateinit var txtCantDis: EditText
    private lateinit var txtCantOcup: EditText
    private lateinit var btnGuardar: Button
    private lateinit var btnVolver: Button

    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guardarlibro) // Usa el archivo de layout correcto

        txtTitulo = findViewById(R.id.txt_titulo)
        txtAutor = findViewById(R.id.txt_autor)
        txtGenero = findViewById(R.id.txt_genero)
        txtCodIsbn = findViewById(R.id.txt_cod_isbn)
        txtCantDis = findViewById(R.id.txt_cant_disp)
        txtCantOcup = findViewById(R.id.txt_cant_ocup)
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
                config.urlLibro + id,
                null,
                { response ->
                    val gson = Gson()
                    val libro: libro = gson.fromJson(response.toString(), libro::class.java)
                    txtAutor.setText(response.getString("autor"))
                    txtTitulo.setText(response.getString("titulo"))
                    txtGenero.setText(response.getString("genero"))
                    txtCodIsbn.setText(response.getString("isbn"))
                    txtCantDis.setText(response.getString("num_ejem_dis"))
                    txtCantOcup.setText(response.getString("num_ejem_ocup"))
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
            parametros.put("titulo", txtTitulo.text.toString())
            parametros.put("autor", txtAutor.text.toString())
            parametros.put("genero", txtGenero.text.toString())
            parametros.put("isbn", txtCodIsbn.text.toString())
            parametros.put("num_ejem_dis", txtCantDis.text.toString())
            parametros.put("num_ejem_ocup", txtCantOcup.text.toString())

            val request = JsonObjectRequest(
                Request.Method.POST,
                config.urlLibro,
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
