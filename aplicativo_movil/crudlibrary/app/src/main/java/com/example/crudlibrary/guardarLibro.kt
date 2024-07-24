package com.example.crudlibrary


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.crudlibrary.config.config
import com.example.crudlibrary.models.libro
import com.google.gson.Gson
//import com.google.gson.JsonObject //Genera error
import org.json.JSONObject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [guardarLibro.newInstance] factory method to
 * create an instance of this fragment.
 */

/*
* Request es peticion que hace a la API
* StringRequest=response or String
* JsonRequest = response OR Json
* JsonArrayRequest = response OR Arreglo OR json
* */
class guardarLibro : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var txt_titulo:EditText
    private lateinit var txt_autor:EditText
    private lateinit var txt_genero:EditText
    private lateinit var txt_cod_isbn:EditText
    private lateinit var txt_cant_dis:EditText
    private lateinit var txt_cant_ocup:EditText

    private lateinit var btnGuardar:Button


    private  var id:String=""

    /*
    Ejemplo de una petición que recibe un String
    * Request es peticion que hace a la API
    * StringRequest=responde
    * Un StringRequest=responde un json
    * JsonArrayRequest=Responde un arreglo de json*/

    fun consultarLibro(){
        /*Solo se debe consultar si el ID es diferente a vacio*/
        if (id!=""){
            var request=JsonObjectRequest(
                Method.GET,
                config.urlLibro+id,
                null,
                {response ->
                    //la variable responde contiene la respuesta de la API
                    //Se convierte de un json a un objeto tipo libro
                    val gson= Gson()
                    //se convierte
                    val libro: libro =gson.fromJson(response.toString(),libro::class.java)
                    //se modific el atributo text de los campos con el valor de objeto
                    txt_autor.setText(response.getString("autor"))
                    txt_titulo.setText(response.getString("titulo"))
                    txt_genero.setText(response.getString("genero"))
                    txt_cod_isbn.setText(response.getString("isbn"))
                    txt_cant_dis.setText(response.getString("num_ejem_dis"))
                    txt_cant_ocup.setText(response.getString("num_ejem_ocup"))
                },
                { error -> Toast.makeText(context,
                    "Error al consultar",
                    Toast.LENGTH_LONG).show()
                }
            )
            var queue=Volley.newRequestQueue(context)
            queue.add(request)
        }

    }



    fun guardarLibro(){
        try {
            if (id=="") {


                //se crea la petición  de crear libro
                /*val request = object : StringRequest(
                    Request.Method.POST, //El metodo de la peticion
                    config.urlLibro, //url de la peticion

                    Response.Listener {
                        //el metodo que se ejecuta cuando la peticion es correcta
                        Toast.makeText(context, "Se guardo correctamente", Toast.LENGTH_LONG).show()
                    },
                    Response.ErrorListener {
                        //metodo que se ejecuta cuando la peticion genera error
                        Toast.makeText(context, "Error al guardar", Toast.LENGTH_LONG).show()
                    }
                ){
                    override fun getParams(): Map<String,String>{
                        var parametros=HashMap<String,String>()
                        parametros.put("Titulo",txt_titulo.text.toString())
                        parametros.put("Autor",txt_autor.text.toString())
                        parametros.put("Genero",txt_genero.text.toString())
                        parametros.put("Isbn",txt_cod_isbn.text.toString())
                        parametros.put("Cant_Dis",txt_cant_dis.text.toString())
                        parametros.put("Cant_Ocup",txt_cant_ocup.text.toString())


                        return parametros
                    }
                }*/
                val parametros=JSONObject()
                parametros.put("titulo",txt_titulo.text.toString())
                parametros.put("autor",txt_autor.text.toString())
                parametros.put("genero",txt_genero.text.toString())
                parametros.put("isbn",txt_cod_isbn.text.toString())
                parametros.put("num_ejem_dis",txt_cant_dis.text.toString())
                parametros.put("num_ejem_ocup",txt_cant_ocup.text.toString())

                var request=JsonObjectRequest (
                    Request.Method.POST,
                    config.urlLibro,
                    parametros,
                    {Response->
                        Toast.makeText(
                            context,
                            "Se guardo correcamente",
                            Toast.LENGTH_LONG
                        ).show()
                    },//cuanod es correcta
                    {error -> Toast.makeText(
                        context,
                        "Se genero un error",
                        Toast.LENGTH_LONG
                    ).show()}//cuando es incorrecta
                )

                val queue=Volley.newRequestQueue(context)

                queue.add(request)

            }else{//a

            }
        }catch (error:Exception){
            Toast.makeText(context, "Error al guardar", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(
            R.layout.fragment_guardar_libro, container, false)
        txt_titulo=view.findViewById(R.id.txt_titulo)
        txt_autor=view.findViewById(R.id.txt_autor)
        txt_genero= view.findViewById(R.id.txt_genero)
        txt_cod_isbn = view.findViewById(R.id.txt_cod_isbn)
        txt_cant_dis = view.findViewById(R.id.txt_cant_disp)
        txt_cant_ocup = view.findViewById(R.id.txt_cant_ocup)
        btnGuardar = view.findViewById(R.id.btnGuardar)
        btnGuardar.setOnClickListener {
            guardarLibro() }
        consultarLibro();
        return view

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment guardarLibro.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            guardarLibro().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
