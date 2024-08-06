var url = "http://localhost:8080/api/v1/libro/";


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
    // Llamar a listarLibros inicialmente al cargar la página
    listarLibros();

    // Función para manejar la búsqueda
    $(".input").on("input", function() {
        var filtro = $(this).val().toLowerCase(); // Obtener el valor del campo de búsqueda
        if (filtro.trim() !== "") {
            buscarLibros(filtro); // Llamar a la función para buscar libros con el filtro ingresado
        } else {
            listarLibros(); // Si el campo está vacío, mostrar todos los libros
        }
    });

    // Función para buscar libros con filtro
    function buscarLibros(filtro) {
        $.ajax({
            url: "http://localhost:8080/api/v1/libro/busquedafiltro/" + filtro, // Endpoint de búsqueda en el backend
            type: "GET",
            success: function(result) {
                var tbody = $("#libros-lista");
                tbody.empty();
                result.forEach(function(libro) {
                    var fila = `<tr>
                                <td class= "table-cell">${libro.id}</td>
                                <td class= "table-cell">${libro.titulo}</td>
                                <td class= "table-cell">${libro.autor}</td>
                                <td class= "table-cell">${libro.isbn}</td>
                                <td class= "table-cell">${libro.genero}</td>
                                <td class= "table-cell">${libro.num_ejem_dis}</td>
                                <td class= "table-cell">${libro.num_ejem_ocup}</td>
                                 <td class="table-cell">
                                    <button class="btn_editar btn-editar" data-id="${libro.id}">
                                        Editar 
                                        <svg class="svg" viewBox="0 0 512 512">
                                            <path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"></path>
                                        </svg>
                                    </button>
                                </td>

                                <td class="table-cell">
                                    <button type="button" class="button_garbaje btn-eliminar" data-id="${libro.id}">
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

                // Asignar eventos a los botones (ya está en tu código)
                $(".btn-editar").on("click", function() {
                    var libroId = $(this).data("id");
                    cargarDatosLibroEnModal(libroId);
                });

                $(".btn-eliminar").on("click", function() {
                    var libroId = $(this).data("id");
                    eliminarLibro(libroId);
                });
            },
            error: function(error) {
                console.error("Error al buscar libros", error);
            }
        });
    }
});





$(document).ready(function() {
    listarLibros();
});

function listarLibros() {
    $.ajax({
        url: url,
        type: "GET",
        success: function(result) {
            var tbody = $("#libros-lista");
            tbody.empty();
            result.forEach(function(libro) {
                var fila = `<tr>
                                <td class= "table-cell">${libro.id}</td>
                                <td class= "table-cell">${libro.titulo}</td>
                                <td class= "table-cell">${libro.autor}</td>
                                <td class= "table-cell">${libro.isbn}</td>
                                <td class= "table-cell">${libro.genero}</td>
                                <td class= "table-cell">${libro.num_ejem_dis}</td>
                                <td class= "table-cell">${libro.num_ejem_ocup}</td>
                                 <td class="table-cell">
                                    <button class="btn_editar btn-editar" data-id="${libro.id}">
                                        Editar 
                                        <svg class="svg" viewBox="0 0 512 512">
                                            <path d="M410.3 231l11.3-11.3-33.9-33.9-62.1-62.1L291.7 89.8l-11.3 11.3-22.6 22.6L58.6 322.9c-10.4 10.4-18 23.3-22.2 37.4L1 480.7c-2.5 8.4-.2 17.5 6.1 23.7s15.3 8.5 23.7 6.1l120.3-35.4c14.1-4.2 27-11.8 37.4-22.2L387.7 253.7 410.3 231zM160 399.4l-9.1 22.7c-4 3.1-8.5 5.4-13.3 6.9L59.4 452l23-78.1c1.4-4.9 3.8-9.4 6.9-13.3l22.7-9.1v32c0 8.8 7.2 16 16 16h32zM362.7 18.7L348.3 33.2 325.7 55.8 314.3 67.1l33.9 33.9 62.1 62.1 33.9 33.9 11.3-11.3 22.6-22.6 14.5-14.5c25-25 25-65.5 0-90.5L453.3 18.7c-25-25-65.5-25-90.5 0zm-47.4 168l-144 144c-6.2 6.2-16.4 6.2-22.6 0s-6.2-16.4 0-22.6l144-144c6.2-6.2 16.4-6.2 22.6 0s6.2 16.4 0 22.6z"></path>
                                        </svg>
                                    </button>
                                </td>

                                <td class="table-cell">
                                    <button type="button" class="button_garbaje btn-eliminar" data-id="${libro.id}">
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

            // Asignar eventos a los botones
            $(".btn-editar").on("click", function() {
                var libroId = $(this).data("id");
                cargarDatosLibroEnModal(libroId);
            });

            $(".btn-eliminar").on("click", function() {
                var libroId = $(this).data("id");
                eliminarLibro(libroId);
            });
        },
        error: function(error) {
            console.error("Error al obtener la lista de libros", error);
        }
    });
}

function cargarDatosLibroEnModal(libroId) {
    $.ajax({
        url: url + libroId,
        type: "GET",
        success: function(libro) {
            $("#editar-titulo").val(libro.titulo);
            $("#editar-autor").val(libro.autor);
            $("#editar-isbn").val(libro.isbn);
            $("#editar-genero").val(libro.genero);
            $("#editar-num_ejem_dis").val(libro.num_ejem_dis);
            $("#editar-num_ejem_ocup").val(libro.num_ejem_ocup);

            $("#guardar-cambios").off("click").on("click", function() {
                actualizarLibro(libroId);
            });

            var modal = new bootstrap.Modal(document.getElementById("staticBackdrop"));
            modal.show();
        },
        error: function(error) {
            console.error("Error al cargar los datos del libro", error);
        }
    });
}

function actualizarLibro(libroId) {
    var libro = {
        titulo: $("#editar-titulo").val(),
        autor: $("#editar-autor").val(),
        isbn: $("#editar-isbn").val(),
        genero: $("#editar-genero").val(),
        num_ejem_dis: $("#editar-num_ejem_dis").val(),
        num_ejem_ocup: $("#editar-num_ejem_ocup").val()
    };

    $.ajax({
        url: url + libroId,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(libro),
        success: function() {
            listarLibros();
            var modal = bootstrap.Modal.getInstance(document.getElementById("staticBackdrop"));
            modal.hide();
        },
        error: function(error) {
            console.error("Error al actualizar el libro", error);
        }
    });
}

function eliminarLibro(libroId) {
    $.ajax({
        url: url + libroId,
        type: "DELETE",
        success: function() {
            listarLibros();
        },
        error: function(error) {
            console.error("Error al eliminar el libro", error);
        }
    });
}








function registrarLibro() {
    let formData = {
        "titulo": document.getElementById("titulo").value,
        "autor": document.getElementById("autor").value,
        "isbn": document.getElementById("isbn").value,
        "genero": document.getElementById("genero").value,
        "num_ejem_dis": parseInt(document.getElementById("num_ejem_dis").value),
        "num_ejem_ocup": parseInt(document.getElementById("num_ejem_ocup").value)
    };

    // Validaciones
    if (!formData.titulo || formData.titulo.length < 1 || formData.titulo.length > 100) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "El título debe tener entre 1 y 100 caracteres.",
        });
        return;
    }

    if (!formData.autor || formData.autor.length < 1 || formData.autor.length > 100) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "El autor debe tener entre 1 y 100 caracteres.",
        });
        return;
    }

    if (!formData.isbn || !/^\d{13}$/.test(formData.isbn)) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "El ISBN debe ser válido y tener 13 caracteres numéricos.",
        });
        return;
    }

    if (!formData.genero) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "El género no puede estar vacío.",
        });
        return;
    }

    if (isNaN(formData.num_ejem_dis) || formData.num_ejem_dis < 0) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "El número de ejemplares disponibles no puede ser negativo.",
        });
        return;
    }

    if (isNaN(formData.num_ejem_ocup) || formData.num_ejem_ocup < 0) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "El número de ejemplares ocupados no puede ser negativo.",
        });
        return;
    }

    if (formData.num_ejem_ocup > formData.num_ejem_dis) {
        Swal.fire({
            icon: "error",
            title: "Error",
            text: "El número de ejemplares ocupados no puede ser mayor que el número de ejemplares disponibles.",
        });
        return;
    }

    $.ajax({
        url: url,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(formData),
        success: function (result) {
            Swal.fire({
                icon: "success",
                title: "Éxito",
                text: "Se guardó correctamente",
            });
        },
        error: function (error) {
            Swal.fire({
                icon: "error",
                title: "Error",
                text: "No se guardó correctamente",
                width: 600,
                padding: "3em",
                color: "#716add"
            });
        }
    });
}
