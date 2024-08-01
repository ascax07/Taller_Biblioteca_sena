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
import com.example.crudlibrary.adapters.PrestamoAdapter
import com.example.crudlibrary.config.config.Companion.urlPrestamo
import com.example.crudlibrary.models.prestamo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class listaprestamo : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var prestamoAdapter: PrestamoAdapter
    private lateinit var prestamos: MutableList<prestamo>
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listaprestamo)

        recyclerView = findViewById(R.id.recyclerViewPrestamo)
        recyclerView.layoutManager = LinearLayoutManager(this)

        prestamos = mutableListOf()
        prestamoAdapter = PrestamoAdapter(this, prestamos)
        recyclerView.adapter = prestamoAdapter

        requestQueue = Volley.newRequestQueue(this)
        fetchPrestamos()

        val btnAtras: Button = findViewById(R.id.btnAtras)
        btnAtras.setOnClickListener {
            finish()
        }

        val btnCrearPrestamo: Button = findViewById(R.id.btnCrearPrestamo)
        btnCrearPrestamo.setOnClickListener {
            val intent = Intent(this, guardar_prestamo::class.java)
            startActivity(intent)
        }
    }

    private fun fetchPrestamos() {
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlPrestamo, null,
            Response.Listener { response ->
                val gson = Gson()
                val prestamosListType = object : TypeToken<List<prestamo>>() {}.type
                val prestamosList: List<prestamo> = gson.fromJson(response.toString(), prestamosListType)
                prestamos.clear()
                prestamos.addAll(prestamosList)
                prestamoAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonArrayRequest)
    }
}
