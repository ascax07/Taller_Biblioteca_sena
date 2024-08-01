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
import com.example.crudlibrary.adapters.UsuarioAdapter
import com.example.crudlibrary.config.config.Companion.urlUsuario
import com.example.crudlibrary.models.usuario
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class listausuario : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var UsuarioAdapter: UsuarioAdapter
    private lateinit var usuarios: MutableList<usuario>
    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listausuario)

        recyclerView = findViewById(R.id.recyclerViewUsuarios)
        recyclerView.layoutManager = LinearLayoutManager(this)

        usuarios = mutableListOf()
        UsuarioAdapter = UsuarioAdapter(this, usuarios)
        recyclerView.adapter = UsuarioAdapter

        requestQueue = Volley.newRequestQueue(this)
        fetchUsuarios()

        val btnAtras: Button = findViewById(R.id.btnAtras)
        btnAtras.setOnClickListener {
            finish()
        }


        val btnUsuarios: Button = findViewById(R.id.btnCrearUsuario)
        btnUsuarios.setOnClickListener {
            val intent = Intent(this, guardar_usuario::class.java)
            startActivity(intent)
        }




    }


    private fun fetchUsuarios() {
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlUsuario, null,
            Response.Listener { response ->
                val gson = Gson()
                val UsuariosListType = object : TypeToken<List<usuario>>() {}.type
                val UsuariosList: List<usuario> = gson.fromJson(response.toString(), UsuariosListType)
                usuarios.clear()
                usuarios.addAll(UsuariosList)
                UsuarioAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonArrayRequest)
    }


}