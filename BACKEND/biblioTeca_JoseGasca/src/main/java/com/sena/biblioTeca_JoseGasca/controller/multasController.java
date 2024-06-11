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

import com.sena.biblioTeca_JoseGasca.interfacesService.I_multasService;
import com.sena.biblioTeca_JoseGasca.models.multas;



@RequestMapping("/api/v1/multas")
@RestController
@CrossOrigin
public class multasController {
 
       @Autowired
	private I_multasService multasService;
	
	@PostMapping("/")
	public ResponseEntity<Object> save(
			@ModelAttribute("multas")multas multas
			){
		multasService.save(multas);
		return new ResponseEntity<>(multas,HttpStatus.OK);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<Object> findAll(){
		var listaMultas=multasService.findAll();
		return new ResponseEntity<>(listaMultas,HttpStatus.OK);
	}
	
	// @GetMapping("/busquedafiltro/{filtro}")
	// public ResponseEntity<Object>findFiltro(@PathVariable String filtro){
	// 	var listaMultas = multasService.filtroMulta(filtro);
	// 	return new ResponseEntity<>(listaMultas, HttpStatus.OK);
	// }
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findOne(@PathVariable("id") String id){
		var multas=multasService.findOne(id);
		return new ResponseEntity<>(multas,HttpStatus.OK);
	}
	
	@GetMapping("/editarMulta/{id}")
	public String mostrarFormularioDeEditarMulta(@PathVariable("id") String id, @ModelAttribute("multas") multas multasUpdate) {
	    // Lógica para obtener la multa por ID y agregarlo al modelo
	    return "formularioEditarMulta";  // El nombre de la página Thymeleaf para el formulario de edición
	}

	@PostMapping("/editarMulta/{id}")
	public String actualizarMulta(@PathVariable("id") String id, @ModelAttribute("multas") multas multasUpdate) {
	    // Lógica para actualizar el multa en la base de datos
	    return "redirect:/listaMultas";  // Redirigir a la lista de multas después de la edición
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") String id){
		multasService.delete(id);
		return new ResponseEntity<>("Registro Eliminado",HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable("id") String id, @ModelAttribute("multas") multas multasUpdate){
		var multas= multasService.findOne(id).get();
		if (multas != null) {
			multas.setUsuario(multasUpdate.getUsuario());
			multas.setPrestamo(multasUpdate.getPrestamo());
			multas.setValor_multa(multasUpdate.getValor_multa());
			multas.setFecha_multa(multasUpdate.getFecha_multa());
			multas.setEstado(multasUpdate.getEstado());
			
			multasService.save(multas);
			return new ResponseEntity<>("Guardado",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Error multa no encontrada",HttpStatus.BAD_REQUEST);
		}
		
	}



}
