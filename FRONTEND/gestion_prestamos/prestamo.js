var url = "http://localhost:8080/api/v1/prestamo/";

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
                                <td>${prestamo.id}</td>
                                <td>${prestamo.fecha_prestamo}</td>
                                <td>${prestamo.fecha_devolucion}</td>
                                <td>${prestamo.usuario.nombre}</td>
                                <td>${prestamo.libro.titulo}</td>
                                <td>${prestamo.estado}</td>
                                <td><button class="btn btn-warning btn-editar" data-id="${prestamo.id}">Editar</button></td>
                                <td><button class="btn btn-danger btn-eliminar" data-id="${prestamo.id}">Eliminar</button></td>
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