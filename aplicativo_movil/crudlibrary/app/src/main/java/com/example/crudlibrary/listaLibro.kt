package com.example.crudlibrary


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
import com.example.crudlibrary.adapters.LibroAdapter
import com.example.crudlibrary.config.config.Companion.urlLibro
import com.example.crudlibrary.models.libro
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class listalibro : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var libroAdapter: LibroAdapter
    private lateinit var libros: MutableList<libro>
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listalibro)

        recyclerView = findViewById(R.id.recyclerViewLibros)
        recyclerView.layoutManager = LinearLayoutManager(this)

        libros = mutableListOf()
        libroAdapter = LibroAdapter(this, libros) // Cambia el orden de los parámetros aquí
        recyclerView.adapter = libroAdapter

        requestQueue = Volley.newRequestQueue(this)
        fetchLibros()

        val btnAtras: Button = findViewById(R.id.btnAtras)
        btnAtras.setOnClickListener {
            finish()
        }
    }

    private fun fetchLibros() {
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlLibro, null,
            Response.Listener { response ->
                val gson = Gson()
                val librosListType = object : TypeToken<List<libro>>() {}.type
                val librosList: List<libro> = gson.fromJson(response.toString(), librosListType)
                libros.clear()
                libros.addAll(librosList)
                libroAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonArrayRequest)
    }
}