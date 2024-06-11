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


import com.sena.biblioTeca_JoseGasca.interfacesService.I_prestamoService;
import com.sena.biblioTeca_JoseGasca.models.prestamo;




@RequestMapping("/api/v1/prestamo")
@RestController
@CrossOrigin
public class prestamoController {
    
    @Autowired
	private I_prestamoService prestamoService;
	
	@PostMapping("/")
	public ResponseEntity<Object> save(
			@ModelAttribute("prestamo")prestamo prestamo
			){
		prestamoService.save(prestamo);
		return new ResponseEntity<>(prestamo,HttpStatus.OK);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<Object> findAll(){
		var listaPrestamo=prestamoService.findAll();
		return new ResponseEntity<>(listaPrestamo,HttpStatus.OK);
	}
	
	// @GetMapping("/busquedafiltro/{filtro}")
	// public ResponseEntity<Object>findFiltro(@PathVariable String filtro){
	// 	var listaPrestamo = prestamoService.filtroprestamo(filtro);
	// 	return new ResponseEntity<>(listaPrestamo, HttpStatus.OK);
	// }
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findOne(@PathVariable("id") String id){
		var prestamo=prestamoService.findOne(id);
		return new ResponseEntity<>(prestamo,HttpStatus.OK);
	}
	
	@GetMapping("/editarPrestamo/{id}")
	public String mostrarFormularioDeEditarPrestamo(@PathVariable("id") String id, @ModelAttribute("prestamo") prestamo prestamoUpdate) {
	    // Lógica para obtener el prestamo por ID y agregarlo al modelo
	    return "formularioeditarPrestamo";  // El nombre de la página Thymeleaf para el formulario de edición
	}

	@PostMapping("/editarPrestamo/{id}")
	public String actualizarPrestamo(@PathVariable("id") String id, @ModelAttribute("prestamo") prestamo prestamoUpdate) {
	    // Lógica para actualizar el prestamo en la base de datos
	    return "redirect:/listaPrestamos";  // Redirigir a la lista de prestamos después de la edición
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") String id){
		prestamoService.delete(id);
		return new ResponseEntity<>("Registro Eliminado",HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable("id") String id, @ModelAttribute("prestamo") prestamo prestamoUpdate){
		var prestamo= prestamoService.findOne(id).get();
		if (prestamo != null) {
			prestamo.setFecha_prestamo(prestamoUpdate.getFecha_prestamo());
			prestamo.setFecha_devolucion(prestamoUpdate.getFecha_devolucion());
			prestamo.setUsuario(prestamoUpdate.getUsuario());
			prestamo.setLibro(prestamoUpdate.getLibro());
			prestamo.setEstado(prestamoUpdate.getEstado());
			
			prestamoService.save(prestamo);
			return new ResponseEntity<>("Guardado",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Error prestamo no encontrado",HttpStatus.BAD_REQUEST);
		}
		
	}


}
