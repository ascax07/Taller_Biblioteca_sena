var url = "http://localhost:8080/api/v1/libro/";



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
                                    <td>${libro.id}</td>
                                    <td>${libro.titulo}</td>
                                    <td>${libro.autor}</td>
                                    <td>${libro.isbn}</td>
                                    <td>${libro.genero}</td>
                                    <td>${libro.num_ejem_dis}</td>
                                    <td>${libro.num_ejem_ocup}</td>
                                    <td><button class="btn btn-warning btn-editar" data-id="${libro.id}">Editar</button></td>
                                    <td><button class="btn btn-danger btn-eliminar" data-id="${libro.id}">Eliminar</button></td>
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
                                <td>${libro.id}</td>
                                <td>${libro.titulo}</td>
                                <td>${libro.autor}</td>
                                <td>${libro.isbn}</td>
                                <td>${libro.genero}</td>
                                <td>${libro.num_ejem_dis}</td>
                                <td>${libro.num_ejem_ocup}</td>
                                <td><button class="btn btn-warning btn-editar" data-id="${libro.id}">Editar</button></td>
                                <td><button class="btn btn-danger btn-eliminar" data-id="${libro.id}">Eliminar</button></td>
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