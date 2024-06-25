var url = "http://localhost:8080/api/v1/multas/";

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
                                <td>${multa.id}</td>
                                <td>${multa.fecha_multa}</td>
                                <td>${multa.valor_multa}</td>
                                <td>${multa.usuario.nombre}</td>
                                <td>${multa.prestamo.id}</td>
                                <td>${multa.estado}</td>
                                <td><button class="btn btn-warning btn-editar" data-id="${multa.id}">Editar</button></td>
                                <td><button class="btn btn-danger btn-eliminar" data-id="${multa.id}">Eliminar</button></td>
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