var url = "http://localhost:8080/api/v1/prestamo/";


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
    listarPrestamos();

    $(".input").on("input", function() {
        var filtro = $(this).val().toLowerCase();
        if (filtro.trim() !== "") {
            listarPrestamos(filtro);
        } else {
            listarPrestamos();
        }
    });
});

function listarPrestamos(filtro) {
    $.ajax({
        url: filtro ? url + "busquedafiltro/" + filtro : url,
        type: "GET",
        success: function(result) {
            var tbody = $("#prestamos-lista");
            tbody.empty();
            result.forEach(function(prestamo) {
                var fila = `<tr>
                                <td class="table-cell">${prestamo.id}</td>
                                <td class="table-cell">${prestamo.fecha_prestamo}</td>
                                <td class="table-cell">${prestamo.fecha_devolucion}</td>
                                <td class="table-cell">${prestamo.usuario.nombre}</td>
                                <td class="table-cell">${prestamo.libro.titulo}</td>
                                <td class="table-cell">${prestamo.estado}</td>
                                <td class="table-cell">
                                    <button class="btn_editar btn-editar" data-id="${prestamo.id}">
                                        Editar 
                                        <svg class="svg" viewBox="0 0 512 512">
                                            <path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"></path>
                                        </svg>
                                    </button>
                                </td>

                                <td class="table-cell">
                                    <button type="button" class="button_garbaje btn-eliminar" data-id="${prestamo.id}">
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
                var prestamoId = $(this).data("id");
                cargarDatosPrestamoEnModal(prestamoId);
            });

            $(".btn-eliminar").on("click", function() {
                var prestamoId = $(this).data("id");
                eliminarPrestamo(prestamoId);
            });
        },
        error: function(error) {
            console.error("Error al buscar préstamos", error);
        }
    });
}

function cargarDatosPrestamoEnModal(prestamoId) {
    $.ajax({
        url: url + prestamoId,
        type: "GET",
        success: function(prestamo) {
            $("#editar-fecha_prestamo").val(prestamo.fecha_prestamo);
            $("#editar-fecha_devolucion").val(prestamo.fecha_devolucion);
            $("#editar-usuario").val(prestamo.usuario.id);
            $("#editar-libro").val(prestamo.libro.id);

            $("#guardar-cambios").off("click").on("click", function() {
                actualizarPrestamo(prestamoId);
            });

            var modal = new bootstrap.Modal(document.getElementById("staticBackdrop"));
            modal.show();
        },
        error: function(error) {
            console.error("Error al cargar los datos del préstamo", error);
        }
    });
}

function actualizarPrestamo(prestamoId) {
    var prestamo = {
        fecha_prestamo: $("#editar-fecha_prestamo").val(),
        fecha_devolucion: $("#editar-fecha_devolucion").val(),
        usuario: { id: $("#editar-usuario").val() },
        libro: { id: $("#editar-libro").val() }
    };

    // Validaciones
    if (!prestamo.fecha_prestamo || new Date(prestamo.fecha_prestamo) > new Date(prestamo.fecha_devolucion)) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "La fecha de préstamo no puede ser después de la fecha de devolución."
        });
        return;
    }

    if (!prestamo.fecha_devolucion || new Date(prestamo.fecha_devolucion) < new Date(prestamo.fecha_prestamo)) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "La fecha de devolución no puede ser antes de la fecha de préstamo."
        });
        return;
    }

    $.ajax({
        url: url + prestamoId,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(prestamo),
        success: function() {
            listarPrestamos();
            var modal = bootstrap.Modal.getInstance(document.getElementById("staticBackdrop"));
            modal.hide();
        },
        error: function(error) {
            Swal.fire({
                icon: "error",
                title: "Error",
                text: "No se pudo actualizar el préstamo: " + error.responseJSON.message
            });
            console.error("Error al actualizar el préstamo", error);
        }
    });
}

function eliminarPrestamo(prestamoId) {
    $.ajax({
        url: url + prestamoId,
        type: "DELETE",
        success: function() {
            listarPrestamos();
        },
        error: function(error) {
            console.error("Error al eliminar el préstamo", error);
        }
    });
}



function registrarPrestamo() {
    var fecha_prestamo = $("#fecha_prestamo").val();
    var fecha_devolucion = $("#fecha_devolucion").val();
    var usuario = $("#usuarioSelect").val();
    var libro = $("#libroSelect").val();
    var estado = $("#estado").val();

    // Validar que todos los campos estén completos
    if (!fecha_prestamo || !fecha_devolucion || !usuario || !libro || !estado) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "Por favor, complete todos los campos."
        });
        return;
    }

    // Validar que la fecha de préstamo no sea después de la fecha de devolución
    if (new Date(fecha_prestamo) > new Date(fecha_devolucion)) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "La fecha de préstamo no puede ser después de la fecha de devolución."
        });
        return;
    }

    // Validar que la fecha de devolución no sea antes de la fecha de préstamo
    if (new Date(fecha_devolucion) < new Date(fecha_prestamo)) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "La fecha de devolución no puede ser antes de la fecha de préstamo."
        });
        return;
    }

    var prestamo = {
        fecha_prestamo: fecha_prestamo,
        fecha_devolucion: fecha_devolucion,
        usuario_id: usuario,
        libro_id: libro,
        estado: estado
    };

    $.ajax({
        url: url,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(prestamo),
        success: function(response) {
            Swal.fire({
                icon: "success",
                title: "Éxito",
                text: "Préstamo registrado exitosamente."
            }).then(function() {
                window.location.href = "crearPrestamo.html";  // Redirecciona a la lista de préstamos
            });
        },
        error: function(error) {
            console.error("Error al registrar préstamo", error);
            Swal.fire({
                icon: "error",
                title: "Error",
                text: "Ocurrió un error al registrar el préstamo."
            });
        }
    });
}







$(document).ready(function () {
    cargarListaUsuarios();
    cargarListaLibros();
});

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


function cargarListaLibros() {
    var libroSelect = $("#libroSelect");

    if (libroSelect.length === 0) {
        console.error("Elemento con ID 'libroSelect' no encontrado.");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/libro/",
        type: "GET",
        success: function (data) {
            data.forEach(function (libro) {
                var option = $("<option></option>")
                    .val(libro.id)
                    .text(libro.id + " - " + libro.isbn);
                libroSelect.append(option);
            });
        },
        error: function (error) {
            console.error("Error al obtener la lista de libro: ", error);
        }
    });
}