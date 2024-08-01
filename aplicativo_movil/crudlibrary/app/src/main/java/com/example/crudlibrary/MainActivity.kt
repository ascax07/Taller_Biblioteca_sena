package com.example.crudlibrary



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLibros: Button = findViewById(R.id.btnLibros)
        btnLibros.setOnClickListener {
            val intent = Intent(this, listalibro::class.java)
            startActivity(intent)
        }

        val btnUsuarios: Button = findViewById(R.id.btnUsuarios)
        btnUsuarios.setOnClickListener {
            val intent = Intent(this, listausuario::class.java)
            startActivity(intent)
        }

        val btnPrestamos: Button = findViewById(R.id.btnPrestamos)
        btnPrestamos.setOnClickListener {
            val intent = Intent(this, listaprestamo::class.java)
            startActivity(intent)
        }
    }
}
