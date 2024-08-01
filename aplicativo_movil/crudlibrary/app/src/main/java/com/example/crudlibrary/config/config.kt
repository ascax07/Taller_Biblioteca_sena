package com.example.crudlibrary.config

class config {
    /*
    Se crea una url static, para consultar sin
    instanciar metodo compain object para almacenar las variables stattic
     */

    companion object{
        val urlBase="http://192.168.0.6:8080/api/v1/"
        val urlLibro = urlBase+"libro/"

        val urlUsuario = urlBase+"usuario/"

        val urlPrestamo = urlBase+"prestamo/"



    }
}