package com.sena.biblioTeca_JoseGasca.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.biblioTeca_JoseGasca.interfacesService.I_LibroService;
import com.sena.biblioTeca_JoseGasca.models.libro;


@RequestMapping("/api/v1/libro")
@RestController
@CrossOrigin
public class libroController {


    @Autowired
	private I_LibroService libroService;
	
	@PostMapping("/")
	public ResponseEntity<Object> save(
			@ModelAttribute("libro")libro libro
			){
		libroService.save(libro);
		return new ResponseEntity<>(libro,HttpStatus.OK);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<Object> findAll(){
		var listaLibro=libroService.findAll();
		return new ResponseEntity<>(listaLibro,HttpStatus.OK);
	}
	
	// @GetMapping("/busquedafiltro/{filtro}")
	// public ResponseEntity<Object>findFiltro(@PathVariable String filtro){
	// 	var listaLibro = libroService.filtroLibro(filtro);
	// 	return new ResponseEntity<>(listaLibro, HttpStatus.OK);
	// }
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findOne(@PathVariable("id") String id){
		var libro=libroService.findOne(id);
		return new ResponseEntity<>(libro,HttpStatus.OK);
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
	public ResponseEntity<Object> delete(@PathVariable("id") String id){
		libroService.delete(id);
		return new ResponseEntity<>("Registro Eliminado",HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable("id") String id, @ModelAttribute("libro") libro libroUpdate){
		var libro= libroService.findOne(id).get();
		if (libro != null) {
			libro.setTitulo(libroUpdate.getTitulo());
			libro.setTitulo(libroUpdate.getAutor());
			libro.setIsbn(libroUpdate.getIsbn());
			libro.setNum_ejem_dis(libroUpdate.getNum_ejem_dis());
			libro.setNum_ejem_ocup(libroUpdate.getNum_ejem_ocup());
			
			libroService.save(libro);
			return new ResponseEntity<>("Guardado",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Error libro no encontrado",HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
