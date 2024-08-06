var url = "http://localhost:8080/api/v1/usuario/";


addEventListener("keypress", soloLetras);

/*
Este método solo permite letras, números, espacios, letras con tildes, punto y coma.
*/

function soloLetras(event) {
    console.log("Tecla presionada: " + event.key);
    console.log("Código de tecla: " + event.keyCode);

    // Obtener el código de la tecla presionada
    var codigoTecla = event.keyCode || event.which;

    // Permitir letras mayúsculas y minúsculas, espacios, la letra ñ, números, letras con tildes, punto y coma
    if (
        (codigoTecla >= 65 && codigoTecla <= 90)  // A-Z
        || (codigoTecla >= 97 && codigoTecla <= 122)  // a-z
        || codigoTecla === 32  // espacio
        || codigoTecla === 209  // letra Ñ mayúscula
        || codigoTecla === 241  // letra ñ minúscula
        || (codigoTecla >= 48 && codigoTecla <= 57)  // 0-9
        || codigoTecla === 46  // punto (.)
        || codigoTecla === 64  // arroba (@)
        || (codigoTecla >= 192 && codigoTecla <= 222) // Otros caracteres especiales como acentos (`) y tildes
        || codigoTecla === 225  // á (en teclados con distribución hispana)
        || codigoTecla === 233  // é
        || codigoTecla === 237  // í
        || codigoTecla === 243  // ó
        || codigoTecla === 250  // ú
    ) {
        return true;
    } else {
        event.preventDefault();
        return false;
    }
}




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
                                <td class="table-cell">
                                    <button class="btn_editar btn-editar" data-id="${usuario.id}">
                                        Editar 
                                        <svg class="svg" viewBox="0 0 512 512">
                                            <path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"></path>
                                        </svg>
                                    </button>
                                </td>
                                <td class="table-cell">
                                    <button type="button" class="button_garbaje btn-eliminar" data-id="${usuario.id}">
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
                var usuarioId = $(this).data("id");
                cargarDatosUsuarioEnModal(usuarioId);
            });

            $(".btn-eliminar").on("click", function() {
                var usuarioId = $(this).data("id");
                confirmarEliminacion(usuarioId);
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

function confirmarEliminacion(usuarioId) {
    Swal.fire({
        title: "¿Seguro que quieres eliminar este usuario?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar"
    }).then((result) => {
        if (result.isConfirmed) {
            eliminarUsuario(usuarioId);
        }
    });
}

function eliminarUsuario(usuarioId) {
    $.ajax({
        url: url + usuarioId,
        type: "DELETE",
        success: function() {
            listarUsuarios(); // Refrescar la lista después de eliminar
            Swal.fire(
                "Eliminado",
                "El usuario ha sido eliminado.",
                "success"
            );
        },
        error: function(error) {
            console.error("Error al eliminar el usuario", error);
            Swal.fire(
                "Error",
                "No se pudo eliminar el usuario, aseguerese de que no este en un prestamo | multa",
                "error"
            );
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
                success: function(result) {
                    Swal.fire({
                        icon: "success",
                        title: "Éxito",
                        text: "Se guardó correctamente"
                    });
                    listarUsuarios();
                },
                error: function(error) {
                    Swal.fire({
                        icon: "error",
                        title: "Error",
                        text: "Ya existe un usuario con este correo y rol."
                    });
                }
            });
        }
    });
}