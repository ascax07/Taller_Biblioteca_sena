var url = "http://localhost:8080/api/v1/usuario/";

$(document).ready(function() {
    listarUsuarios();

    $(".input").on("input", function() {
        var filtro = $(this).val().toLowerCase();
        if (filtro.trim() !== "") {
            listarUsuarios(filtro);
        } else {
            listarUsuarios();
        }
    });
});



function listarUsuarios(filtro) {
    $.ajax({
        url: filtro ? url + "busquedafiltro/" + filtro : url,
        type: "GET",
        success: function(result) {
            var tbody = $("#usuarios-lista");
            tbody.empty();
            result.forEach(function(usuario) {
                var fila = `<tr class="table-row">
                                <td class="table-cell">${usuario.id}</td>
                                <td class="table-cell">${usuario.nombre}</td>
                                <td class="table-cell">${usuario.direccion}</td>
                                <td class="table-cell">${usuario.correo_electronico}</td>
                                <td class="table-cell">${usuario.tipo_usuario}</td>
                                <td class="table-cell"><button class="btn btn-warning btn-editar" data-id="${usuario.id}">Editar</button></td>
                                <td class="table-cell"><button class="btn btn-danger btn-eliminar" data-id="${usuario.id}">Eliminar</button></td>
                            </tr>`;
                tbody.append(fila);
            });

            $(".btn-editar").on("click", function() {
                var usuarioId = $(this).data("id");
                cargarDatosUsuarioEnModal(usuarioId);
            });

            $(".btn-eliminar").on("click", function() {
                var usuarioId = $(this).data("id");
                eliminarUsuario(usuarioId);
            });
        },
        error: function(error) {
            console.error("Error al buscar usuarios", error);
        }
    });
}

function cargarDatosUsuarioEnModal(usuarioId) {
    $.ajax({
        url: url + usuarioId,
        type: "GET",
        success: function(usuario) {
            $("#editar-nombre").val(usuario.nombre);
            $("#editar-direccion").val(usuario.direccion);
            $("#editar-correo_electronico").val(usuario.correo_electronico);
            $("#editar-tipo_usuario").val(usuario.tipo_usuario);

            $("#guardar-cambios").off("click").on("click", function() {
                actualizarUsuario(usuarioId);
            });

            var modal = new bootstrap.Modal(document.getElementById("staticBackdrop"));
            modal.show();
        },
        error: function(error) {
            console.error("Error al cargar los datos del usuario", error);
        }
    });
}

function actualizarUsuario(usuarioId) {
    var usuario = {
        nombre: $("#editar-nombre").val(),
        direccion: $("#editar-direccion").val(),
        correo_electronico: $("#editar-correo_electronico").val(),
        tipo_usuario: $("#editar-tipo_usuario").val()
    };

    // Validaciones
    if (!usuario.nombre || usuario.nombre.length < 1 || usuario.nombre.length > 100) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "El nombre debe tener entre 1 y 100 caracteres."
        });
        return;
    }

    if (!usuario.direccion || usuario.direccion.length < 1 || usuario.direccion.length > 100) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "La dirección debe tener entre 1 y 100 caracteres."
        });
        return;
    }

    if (!usuario.correo_electronico || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(usuario.correo_electronico)) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "El correo electrónico debe tener un formato válido."
        });
        return;
    }

    const validRoles = ["lector", "bibliotecario", "administrador"];
    if (!usuario.tipo_usuario || !validRoles.includes(usuario.tipo_usuario)) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "El tipo de usuario debe ser uno de los siguientes: lector, bibliotecario, administrador."
        });
        return;
    }

    $.ajax({
        url: url + usuarioId,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(usuario),
        success: function() {
            listarUsuarios();
            var modal = bootstrap.Modal.getInstance(document.getElementById("staticBackdrop"));
            modal.hide();
        },
        error: function(error) {
            Swal.fire({
                icon: "error",
                title: "Error",
                text: "No se pudo actualizar el usuario: " + error.responseJSON.message
            });
            console.error("Error al actualizar el usuario", error);
        }
    });
}

function eliminarUsuario(usuarioId) {
    $.ajax({
        url: url + usuarioId,
        type: "DELETE",
        success: function() {
            listarUsuarios();
        },
        error: function(error) {
            console.error("Error al eliminar el usuario", error);
        }
    });
}

function validarCorreoYRol(correo, tipo_usuario, callback) {
    $.ajax({
        url: url + "existe?correo=" + correo + "&tipo_usuario=" + tipo_usuario,
        type: "GET",
        success: function(existe) {
            callback(existe);
        },
        error: function(error) {
            console.error("Error al verificar la existencia del usuario", error);
            callback(false);
        }
    });
}

function registrarUsuario() {
    let formData = {
        "nombre": document.getElementById("nombre").value,
        "direccion": document.getElementById("direccion").value,
        "correo_electronico": document.getElementById("correo_electronico").value,
        "tipo_usuario": document.getElementById("tipo_usuario").value
    };

    // Validaciones
    if (!formData.nombre || formData.nombre.length < 1 || formData.nombre.length > 100) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "El nombre debe tener entre 1 y 100 caracteres."
        });
        return;
    }

    if (!formData.direccion || formData.direccion.length < 1 || formData.direccion.length > 100) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "La dirección debe tener entre 1 y 100 caracteres."
        });
        return;
    }

    if (!formData.correo_electronico || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.correo_electronico)) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "El correo electrónico debe tener un formato válido."
        });
        return;
    }

    const validRoles = ["lector", "bibliotecario", "administrador"];
    if (!formData.tipo_usuario || !validRoles.includes(formData.tipo_usuario)) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "El tipo de usuario debe ser uno de los siguientes: lector, bibliotecario, administrador."
        });
        return;
    }

    // Verificar si ya existe un usuario con el mismo correo y rol
    validarCorreoYRol(formData.correo_electronico, formData.tipo_usuario, function(existe) {
        if (existe) {
            Swal.fire({
                icon: "error",
                title: "Error",
                text: "Ya existe un usuario con este correo y rol."
            });
        } else {
            // Si no existe, proceder con el registro
            $.ajax({
                url: url,
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(formData),
                success: function (result) {
                    Swal.fire({
                        icon: "success",
                        title: "Éxito",
                        text: "Se guardó correctamente"
                    });
                    listarUsuarios();
                },
                error: function (error) {
                    Swal.fire({
                        icon: "error",
                        title: "Error",
                        text: "Ya existe un usuario con este correo y rol "
                    });
                }
            });
        }
    });
}