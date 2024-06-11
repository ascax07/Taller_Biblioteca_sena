var url = "http://localhost:8080/api/v1/libro/";


function listacliente() {

    //var capturarFiltro = document.getElementById("Search").value;
    var urlCliente = url;

    //if (capturarFiltro !== "") {
    //urlCliente += "busquedafiltro/" + capturarFiltro;
    //}

    $.ajax({
        url: urlCliente,
        type: "GET",
        success: function (result) {
            console.log(result);
            var listacliente = document.getElementById("listacliente");
            listacliente.innerHTML = "";

            for (var i = 0; i < result.length; i++) {
                let trRegistro = document.createElement("tr");
                // Crear celdas para cada dato del cliente
                let celdaID = document.createElement("td");
                let celdaTipoIdentificacion = document.createElement("td");
                let celdaNumeroIdentificacion = document.createElement("td");
                let celdaNombreCliente = document.createElement("td");
                let celdaApellidoCliente = document.createElement("td");
                let celdaTelefono = document.createElement("td");
                let celdaDireccion = document.createElement("td");
                let celdaCiudad = document.createElement("td");
                let celdaCorreo = document.createElement("td");
                let celdaEstado = document.createElement("td");
                let celdaEditar = document.createElement("td");
                let celdaEliminar = document.createElement("td");

                // Asignar valores a las celdas
                celdaID.innerText = result[i]["id_cliente"];
                celdaTipoIdentificacion.innerText = result[i]["tipo_identificacion"];
                celdaNumeroIdentificacion.innerText = result[i]["numero_identificacion"];
                celdaNombreCliente.innerText = result[i]["nombre_cliente"] || "";
                celdaApellidoCliente.innerText = result[i]["apellido_cliente"] || "";
                celdaTelefono.innerText = result[i]["telefono"];
                celdaDireccion.innerText = result[i]["direccion"];
                celdaCiudad.innerText = result[i]["ciudad"];
                celdaCiudad.innerText = result[i]["ciudad"];
                celdaCorreo.innerText = result[i]["correo"];
                celdaEstado.innerText = result[i]["estado"];

                // Agregar botón "Editar" y "Eliminar"
                let botonEditar = document.createElement("a");
                botonEditar.className = "boton-editar";
                botonEditar.textContent = "Editar";
                botonEditar.id = "btnEditar";

                let botonEliminar = document.createElement("button");
                botonEliminar.className = "boton-eliminar";
                botonEliminar.textContent = "Eliminar";

                // Asignar eventos a los botones
                botonEliminar.onclick = (function (id) {
                    return function () {
                        eliminarcliente(id);
                    };
                })(result[i]["id_cliente"]);


                botonEditar.onclick = (function (index) {
                    return function () {
                        let idCliente = result[index]["id_cliente"];
                        let modal = document.getElementById("staticBackdrop");
                        if (!modal) {
                            let modalCode = `
                            <!-- Modal -->
                            <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                              <div class="modal-dialog">
                              <div class="modal-content">
                                  <div class="modal-header">
                                  <h1 class="modal-title fs-5" id="staticBackdropLabel">Editar Cliente</h1>
                                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                  </div>
                                  <div class="modal-body">
                                      <div class="container">
                                      <div class="row">
                                      
                                      <div class="col col-xl-6 col-12">
                                        <label class="" for="tipo_identificacion">Tipo documento</label>
                                            <select id="tipo_identificacion" class="form-select ">
                                                <option value="" selected disabled>Seleccione una opción</option>
                                                <option value="RC">Registro Civil</option>
                                                <option value="TI">Tarjeta de Identidad</option>
                                                <option value="CC">Cedula de Ciudadanía</option>
                                                <option value="CE">Cedula de Extranjería</option>
                                            </select>
                                     </div>

                                          <div class="col col-xl-6 col-12 ">
                                              <label class="" for="numero_identificacion">numero de identificacion</label>
                                              <input type="number" id="numero_identificacion" step="1" class="form-control ">
                                          </div>

                                          <div class="col col-xl-6 col-12 ">
                                              <label class="" for="nombre_cliente">Primer Nombre</label>
                                              <input type="text" id="nombre_cliente" class="form-control ">
                                          </div>
                                          <div class="col col-xl-6 col-12 ">
                                              <label class="" for="apellido_cliente">Segundo Nombre</label>
                                              <input type="text" id="apellido_cliente" class="form-control ">
                                          </div>
                                          </div>
                                          <div class="col col-xl-6 col-12 ">
                                              <label class="" for="telefono">Teléfono</label>
                                              <input type="text" id="telefono" class="form-control ">
                                          </div>

                                          <div class="col col-xl-6 col-12 ">
                                              <label class="" for="direccion">direccion</label>
                                              <input type="text" id="direccion" class="form-control ">
                                          </div>

                                          <div class="col col-xl-6 col-12 ">
                                              <label class="" for="ciudad">ciudad</label>
                                              <input type="text" id="ciudad" class="form-control ">
                                          </div>


                                          <div class="col col-xl-6 col-12 ">
                                              <label class="" for="correo">Correo</label>
                                              <input type="text" id="correo" class="form-control ">
                                          </div>

                                          <div class="col col-xl-6 col-12 ">
                                              <label class="" for="estado">estado</label>
                                              <input type="text" id="estado" class="form-control ">
                                          </div>


                                          <div class="modal-footer">
                                          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">cerrar</button>
                                          <button type="button" class="btn btn-primary">Guardar</button>
                                          </div>
                              </div>
                              </div>
                          </div>
                            `;
                            let modalContainer = document.createElement("div");
                            modalContainer.innerHTML = modalCode;
                            document.body.appendChild(modalContainer);
                            modal = document.getElementById("staticBackdrop");
                        }
                        let modalInstance = new bootstrap.Modal(modal);
                        modalInstance.show();
                        let botonGuardar = modal.querySelector(".btn-primary");
                        botonGuardar.addEventListener("click", function () {
                            guardarCambiosCliente(idCliente);
                        });
                        cargarDatosClienteEnModal(idCliente);
                    };
                })(i);

                celdaEditar.appendChild(botonEditar);
                celdaEliminar.appendChild(botonEliminar);

                // Agregar celdas a la fila
                trRegistro.appendChild(celdaID);
                trRegistro.appendChild(celdaTipoIdentificacion);
                trRegistro.appendChild(celdaNumeroIdentificacion);
                trRegistro.appendChild(celdaNombreCliente);
                trRegistro.appendChild(celdaApellidoCliente);
                trRegistro.appendChild(celdaTelefono);
                trRegistro.appendChild(celdaDireccion);
                trRegistro.appendChild(celdaCiudad);
                trRegistro.appendChild(celdaCorreo);
                trRegistro.appendChild(celdaEstado);
                trRegistro.appendChild(celdaEditar);
                trRegistro.appendChild(celdaEliminar);

                // Agregar fila a la tabla
                listacliente.appendChild(trRegistro);
            }
        },
        error: function (error) {
            alert("Error en la petición " + error);
        }
    });



}








function registrarLibro() {
    // Obtener los valores de los campos del formulario
    let formData = {
        "titulo": document.getElementById("titulo").value,
        "autor": document.getElementById("autor").value,
        "isbn": document.getElementById("isbn").value,
        "num_ejem_dis": document.getElementById("num_ejem_dis").value,
        "num_ejem_ocup": document.getElementById("num_ejem_ocup").value
    };

     let isbn = formData["isbn"];
  
    // Verificar duplicación de  isbn
    /* validarDocumentoRepetido(tipoDocumento, numeroDocumento, function(documentoRepetido) {
        if (documentoRepetido) {
            alert("El tipo y número de documento ya están en uso.");
        } else { */
    // Verificar duplicación de teléfono y correo
    /* validarTelefonoCorreo(formData["telefono"], formData["correo"], function(telefonoRepetido, correoRepetido) {
        if (telefonoRepetido && correoRepetido) {
            alert("El teléfono y el correo ya están en uso.");
        } else if (telefonoRepetido) {
            alert("El teléfono ya está en uso.");
        } else if (correoRepetido) {
            alert("El correo ya está en uso.");
        } else { */
    // Si no hay duplicados, proceder con el registro
    // Validar campos obligatorios al final
    /* if (!validarCampos()) {
        Swal.fire({
            title: "ERROR",
            text: "Llene todos los campos correctamente",
            icon: "error"
        });
        return;
    } */

    // Los campos son válidos, proceder con el registro
    $.ajax({
        url: url,
        type: "POST",
        data: formData,
        success: function (result) {

            alert("Se guardó correctamente");
        },
        error: function (error) {
            Swal.fire({
                icon: "error",
                title: "Error",
                text: "No se guardó correctamente",
                width: 600,
                padding: "3em",
                color: "#716add",
            });
        }
    });
    /* }
});
}
}); */

}