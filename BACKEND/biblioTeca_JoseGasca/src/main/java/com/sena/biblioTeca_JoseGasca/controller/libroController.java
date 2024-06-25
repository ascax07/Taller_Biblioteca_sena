package com.sena.biblioTeca_JoseGasca.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import com.sena.biblioTeca_JoseGasca.interfacesService.I_LibroService;
import com.sena.biblioTeca_JoseGasca.models.libro;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/libro")
@RestController
@CrossOrigin
@Validated
public class libroController {

    @Autowired
    private I_LibroService libroService;

    @PostMapping("/")
    public ResponseEntity<Object> save(@Valid @RequestBody libro libro, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        libroService.save(libro);
        return new ResponseEntity<>(libro, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAll() {
        var listaLibro = libroService.findAll();
        return new ResponseEntity<>(listaLibro, HttpStatus.OK);
    }

    @GetMapping("/busquedafiltro/{filtro}")
    public ResponseEntity<Object> findFiltro(@PathVariable String filtro) {
        var listaLibros = libroService.filtroLibro(filtro);
        return new ResponseEntity<>(listaLibros, HttpStatus.OK);
    }

    @PutMapping("/ejem/{id}")
    public ResponseEntity<Object> update_ejem(@PathVariable("id") String id, @RequestBody libro libroUpdate) {
        libroUpdate.setId(id);
        boolean isUpdated = libroService.updateLibro(libroUpdate);
        if (isUpdated) {
            return new ResponseEntity<>("Guardado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: número de ejemplares ocupados no puede ser mayor que el total de ejemplares", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable("id") String id) {
        var libro = libroService.findOne(id);
        return new ResponseEntity<>(libro, HttpStatus.OK);
    }

    @GetMapping("/editarLibro/{id}")
    public String mostrarFormularioDeEditarLibro(@PathVariable("id") String id, @ModelAttribute("libro") libro libroUpdate) {
        // Lógica para obtener el libro por ID y agregarlo al modelo
        return "formularioEditarLibro";  // El nombre de la página Thymeleaf para el formulario de edición
    }

    @PostMapping("/editarLibro/{id}")
    public String actualizarLibro(@PathVariable("id") String id, @ModelAttribute("libro") libro libroUpdate) {
        // Lógica para actualizar el libro en la base de datos
        return "redirect:/listaLibros";  // Redirigir a la lista de libros después de la edición
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        libroService.delete(id);
        return new ResponseEntity<>("Registro Eliminado", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody libro libroUpdate) {
        var libro = libroService.findOne(id).orElse(null);
        if (libro != null) {
            libro.setTitulo(libroUpdate.getTitulo());
            libro.setAutor(libroUpdate.getAutor());
            libro.setIsbn(libroUpdate.getIsbn());
            libro.setGenero(libroUpdate.getGenero());
            libro.setNum_ejem_dis(libroUpdate.getNum_ejem_dis());
            libro.setNum_ejem_ocup(libroUpdate.getNum_ejem_ocup());

            libroService.save(libro);
            return new ResponseEntity<>("Guardado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error libro no encontrado", HttpStatus.BAD_REQUEST);
        }
    }

}
