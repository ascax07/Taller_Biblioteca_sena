package com.example.crudlibrary



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.crudlibrary.listalibro
import com.example.crudlibrary.lista_usuario



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
        btnLibros.setOnClickListener {
            val intent = Intent(this, lista_usuario::class.java)
            startActivity(intent)
        }
    }
}
