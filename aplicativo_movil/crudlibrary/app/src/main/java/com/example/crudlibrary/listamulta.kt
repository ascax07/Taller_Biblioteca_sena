package com.example.crudlibrary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibrary.adapters.MultaAdapter
import com.example.crudlibrary.config.config.Companion.urlMulta
import com.example.crudlibrary.models.multa
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class listamulta : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var multaAdapter: MultaAdapter
    private lateinit var multas: MutableList<multa>
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listamulta)  // Asegúrate de cambiar el layout a lista_multa.xml

        recyclerView = findViewById(R.id.recyclerViewMulta)
        recyclerView.layoutManager = LinearLayoutManager(this)

        multas = mutableListOf()
        multaAdapter = MultaAdapter(this, multas)
        recyclerView.adapter = multaAdapter

        requestQueue = Volley.newRequestQueue(this)
        fetchMultas()

        val btnAtras: Button = findViewById(R.id.btnAtras)
        btnAtras.setOnClickListener {
            finish()
        }

        val btnCrearMulta: Button = findViewById(R.id.btnCrearMulta)
        btnCrearMulta.setOnClickListener {
            val intent = Intent(this, guardar_multa::class.java)
            startActivity(intent)
        }
    }

    private fun fetchMultas() {
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlMulta, null,
            Response.Listener { response ->
                val gson = Gson()
                val multasListType = object : TypeToken<List<multa>>() {}.type
                val multasList: List<multa> = gson.fromJson(response.toString(), multasListType)
                multas.clear()
                multas.addAll(multasList)
                multaAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonArrayRequest)
    }
}
