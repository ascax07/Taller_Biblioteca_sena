package com.example.crudlibrary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibrary.config.config
import com.example.crudlibrary.models.libro
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [detalleLibro.newInstance] factory method to
 * create an instance of this fragment.
 */
class detalleLibro : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    private lateinit var lbltitulo:TextView
    private lateinit var lblautor:TextView
    private lateinit var lblgenero:TextView
    private lateinit var lblcod_isbn:TextView
    private lateinit var lblcant_dis:TextView
    private lateinit var lblcant_ocup:TextView

    private lateinit var btnEditar: Button
    private lateinit var btnEliminar:Button

    //se asigna un id existente temporal
    private  var id:String="5a41145e-4c4c-41c2-99cc-c0fe1e7a265c"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    /*
    Ejemplo de una petición que recibe un String
    * Request es peticion que hace a la API
    * StringRequest=responde
    * Un StringRequest=responde un json
    * JsonArrayRequest=Responde un arreglo de json*/

    fun consultarLibro() {
        /*Solo se debe consultar si el ID es diferente a vacio*/
        if (id != "") {
            var request = JsonObjectRequest(
                Request.Method.GET,
                config.urlLibro + id,
                null,
                { response ->
                    // Convertir la respuesta JSON a un objeto libro usando Gson
                    val gson = Gson()
                    val libro: libro = gson.fromJson(response.toString(), libro::class.java)

                    // Actualizar los TextViews con los datos del objeto libro
                    lblautor.text = libro.autor
                    lbltitulo.text = libro.titulo
                    lblgenero.text = libro.genero
                    lblcod_isbn.text = libro.isbn
                    lblcant_dis.text = libro.num_ejem_dis.toString()
                    lblcant_ocup.text = libro.num_ejem_ocup.toString()
                },
                { error ->
                    Toast.makeText(context,
                        "Error al consultar: ${error.message}",
                        Toast.LENGTH_LONG).show()
                }
            )

            // Agregar la solicitud a la cola de Volley
            Volley.newRequestQueue(context).add(request)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_detalle_libro, container, false)
        lbltitulo = view.findViewById(R.id.lbltitulo)
        lblautor = view.findViewById(R.id.lblautor)
        lblgenero = view.findViewById(R.id.lblgenero)
        lblcod_isbn = view.findViewById(R.id.lblcod_isbn)
        lblcant_dis = view.findViewById(R.id.lblcant_disp)
        lblcant_ocup = view.findViewById(R.id.lblcant_ocup)
        consultarLibro()


        btnEditar = view.findViewById(R.id.btnEditar)
        btnEditar.setOnClickListener { editarLibro() }
        btnEliminar = view.findViewById(R.id.btnEliminar)
        btnEliminar.setOnClickListener { eliminarLibro() }

        return view
    }

    fun editarLibro()  {


    }


    fun eliminarLibro() {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment detalleLibro.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            detalleLibro().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

