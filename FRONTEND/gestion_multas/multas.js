var url = "http://localhost:8080/api/v1/multas/";



addEventListener("keypress", soloLetras);


/*
este metodo solo permite letras
*/



function soloLetras(event) {
    console.log("tecla presionada: " + event.key);
    console.log("código de tecla: " + event.keyCode);

    // Obtener el código de la tecla presionada
    var codigoTecla = event.keyCode || event.which;

    // Permitir letras mayúsculas y minúsculas, espacios, la letra ñ, números y letras con tildes
    if ((codigoTecla >= 65 && codigoTecla <= 90)   // A-Z
        || (codigoTecla >= 97 && codigoTecla <= 122)  // a-z
        || codigoTecla === 32  // espacio
        || codigoTecla === 209  // letra Ñ
        || (codigoTecla >= 48 && codigoTecla <= 57)  // 0-9
        || (codigoTecla >= 160 && codigoTecla <= 163) // letras con tilde mayúsculas (Á, É, Í, Ó, Ú)
        || (codigoTecla >= 181 && codigoTecla <= 186) // letras con tilde minúsculas (á, é, í, ó, ú)
        || codigoTecla === 225) { // código específico para letras acentuadas (á, é, í, ó, ú)
        return true;
    } else {
        event.preventDefault();
        return false;
    }
}


$(document).ready(function() {
    listarMultas();

    $(".input").on("input", function() {
        var filtro = $(this).val().toLowerCase();
        if (filtro.trim() !== "") {
            listarMultas(filtro);
        } else {
            listarMultas();
        }
    });
});

function listarMultas(filtro) {
    $.ajax({
        url: filtro ? url + "busquedafiltro/" + filtro : url,
        type: "GET",
        success: function(result) {
            var tbody = $("#multas-lista");
            tbody.empty();
            result.forEach(function(multa) {
                var fila = `<tr>
                                <td class="table-cell">${multa.id}</td>
                                <td class="table-cell">${multa.fecha_multa}</td>
                                <td class="table-cell">${multa.valor_multa}</td>
                                <td class="table-cell">${multa.usuario.nombre}</td>
                                <td class="table-cell">${multa.prestamo.id}</td>
                                <td class="table-cell">${multa.estado}</td>
                                 <td class="table-cell">
                                    <button class="btn_editar btn-editar" data-id="${multa.id}">
                                        Editar 
                                        <svg class="svg" viewBox="0 0 512 512">
                                            <path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"></path>
                                        </svg>
                                    </button>
                                </td>

                                <td class="table-cell">
                                    <button type="button" class="button_garbaje btn-eliminar" data-id="${multa.id}">
                                        <span class="button__text">Eliminar</span>
                                        <span class="button__icon">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="512" viewBox="0 0 512 512" height="512" class="svg">
                                                <title></title>
                                                <path style="fill:none;stroke:#fff;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px" d="M112,112l20,320c.95,18.49,14.4,32,32,32H348c17.67,0,30.87-13.51,32-32l20-320"></path>
                                                <line y2="112" y1="112" x2="432" x1="80" style="stroke:#fff;stroke-linecap:round;stroke-miterlimit:10;stroke-width:32px"></line>
                                                <path style="fill:none;stroke:#fff;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px" d="M192,112V72h0a23.93,23.93,0,0,1,24-24h80a23.93,23.93,0,0,1,24,24h0v40"></path>
                                                <line y2="400" y1="176" x2="256" x1="256" style="fill:none;stroke:#fff;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"></line>
                                                <line y2="400" y1="176" x2="192" x1="184" style="fill:none;stroke:#fff;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"></line>
                                                <line y2="400" y1="176" x2="320" x1="328" style="fill:none;stroke:#fff;stroke-linecap:round;stroke-linejoin:round;stroke-width:32px"></line>
                                            </svg>
                                        </span>
                                    </button>
                                </td>
                            </tr>`;
                tbody.append(fila);
            });

            $(".btn-editar").on("click", function() {
                var multaId = $(this).data("id");
                cargarDatosMultaEnModal(multaId);
            });

            $(".btn-eliminar").on("click", function() {
                var multaId = $(this).data("id");
                eliminarMulta(multaId);
            });
        },
        error: function(error) {
            console.error("Error al buscar multas", error);
        }
    });
}

function cargarDatosMultaEnModal(multaId) {
    $.ajax({
        url: url + multaId,
        type: "GET",
        success: function(multa) {
            $("#editar-fecha_multa").val(multa.fecha_multa);
            $("#editar-valor_multa").val(multa.valor_multa);
            $("#editar-usuario").val(multa.usuario.id);
            $("#editar-prestamo").val(multa.prestamo.id);
            $("#editar-estado").val(multa.estado);

            $("#guardar-cambios").off("click").on("click", function() {
                actualizarMulta(multaId);
            });

            var modal = new bootstrap.Modal(document.getElementById("staticBackdrop"));
            modal.show();
        },
        error: function(error) {
            console.error("Error al cargar los datos de la multa", error);
        }
    });
}

function actualizarMulta(multaId) {
    var multa = {
        fecha_multa: $("#editar-fecha_multa").val(),
        valor_multa: $("#editar-valor_multa").val(),
        usuario: { id: $("#editar-usuario").val() },
        prestamo: { id: $("#editar-prestamo").val() },
        estado: $("#editar-estado").val()
    };

    $.ajax({
        url: url + multaId,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(multa),
        success: function() {
            listarMultas();
            var modal = bootstrap.Modal.getInstance(document.getElementById("staticBackdrop"));
            modal.hide();
        },
        error: function(error) {
            Swal.fire({
                icon: "error",
                title: "Error",
                text: "No se pudo actualizar la multa: " + error.responseJSON.message
            });
            console.error("Error al actualizar la multa", error);
        }
    });
}

function eliminarMulta(multaId) {
    $.ajax({
        url: url + multaId,
        type: "DELETE",
        success: function() {
            listarMultas();
        },
        error: function(error) {
            console.error("Error al eliminar la multa", error);
        }
    });
}

function registrarMulta() {
    var fecha_multa = $("#fecha_multa").val();
    var valor_multa = $("#valor_multa").val();
    var usuario = $("#usuarioSelect").val();
    var prestamo = $("#prestamoSelect").val();
    var estado = $("#estado").val();

    // Validar que todos los campos estén completos
    if (!fecha_multa || !valor_multa || !usuario || !prestamo || !estado) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "Por favor, complete todos los campos."
        });
        return;
    }

    var multa = {
        fecha_multa: fecha_multa,
        valor_multa: valor_multa,
        usuario_id: usuario,
        prestamo_id: prestamo,
        estado: estado
    };

    $.ajax({
        url: url,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(multa),
        success: function(response) {
            Swal.fire({
                icon: "success",
                title: "Éxito",
                text: "Multa registrada exitosamente."
            }).then(function() {
                window.location.href = "crearMulta.html";  // Redirecciona a la lista de multas
            });
        },
        error: function(error) {
            console.error("Error al registrar multa", error);
            Swal.fire({
                icon: "error",
                title: "Error",
                text: "Ocurrió un error al registrar la multa, por favor ingrese el prestamo correspondiente al usuario"
            });
        }
    });
}

$(document).ready(function () {
    cargarListaUsuarios();
    cargarListaPrestamos();
});




//lista para el select

function cargarListaUsuarios() {
    var usuarioSelect = $("#usuarioSelect");

    if (usuarioSelect.length === 0) {
        console.error("Elemento con ID 'usuarioSelect' no encontrado.");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/usuario/",
        type: "GET",
        success: function (data) {
            data.forEach(function (usuario) {
                var option = $("<option></option>")
                    .val(usuario.id)
                    .text(usuario.id);
                usuarioSelect.append(option);
            });
        },
        error: function (error) {
            console.error("Error al obtener la lista de usuarios: ", error);
        }
    });
}

function cargarListaPrestamos() {
    var prestamoSelect = $("#prestamoSelect");

    if (prestamoSelect.length === 0) {
        console.error("Elemento con ID 'prestamoSelect' no encontrado.");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/prestamo/",
        type: "GET",
        success: function (data) {
            data.forEach(function (prestamo) {
                var option = $("<option></option>")
                    .val(prestamo.id)
                    .text(prestamo.id);
                prestamoSelect.append(option);
            });
        },
        error: function (error) {
            console.error("Error al obtener la lista de préstamos: ", error);
        }
    });
}