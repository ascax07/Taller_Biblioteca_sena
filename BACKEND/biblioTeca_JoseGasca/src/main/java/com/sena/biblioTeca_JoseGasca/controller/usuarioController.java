package com.sena.biblioTeca_JoseGasca.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.biblioTeca_JoseGasca.interfacesService.I_usuarioService;

import com.sena.biblioTeca_JoseGasca.models.usuario;

import jakarta.validation.Valid;


@RequestMapping("/api/v1/usuario")
@RestController
@CrossOrigin
@Validated
public class usuarioController {

    
    	@Autowired
	private I_usuarioService usuarioService;
	
    @PostMapping("/")
    public ResponseEntity<Object> save(@Valid @RequestBody usuario usuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            usuarioService.save(usuario);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

	
	
	@GetMapping("/")
	public ResponseEntity<Object> findAll(){
		var listaUsuario=usuarioService.findAll();
		return new ResponseEntity<>(listaUsuario,HttpStatus.OK);
	}
	

	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findOne(@PathVariable("id") String id){
		var usuario=usuarioService.findOne(id);
		return new ResponseEntity<>(usuario,HttpStatus.OK);
	}

	@GetMapping("/busquedafiltro/{filtro}")
    public ResponseEntity<Object> findFiltro(@PathVariable String filtro) {
        var listaUsuario = usuarioService.filtroUsuario(filtro);
        return new ResponseEntity<>(listaUsuario, HttpStatus.OK);
    }

	
	@GetMapping("/editarUsuario/{id}")
	public String mostrarFormularioDeEditarUsuario(@PathVariable("id") String id, @ModelAttribute("usuario") usuario usuarioUpdate) {
	    // Lógica para obtener el usuario por ID y agregarlo al modelo
	    return "formularioEditarUsuario";  // El nombre de la página Thymeleaf para el formulario de edición
	}

	@PostMapping("/editarUsuario/{id}")
	public String actualizarusuario(@PathVariable("id") String id, @ModelAttribute("usuario") usuario usuarioUpdate) {
	    // Lógica para actualizar el usuario en la base de datos
	    return "redirect:/listaUsuarios";  // Redirigir a la lista de usuarios después de la edición
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") String id){
		usuarioService.delete(id);
		return new ResponseEntity<>("Registro Eliminado",HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody usuario usuarioUpdate){
		var usuario= usuarioService.findOne(id).get();
		if (usuario != null) {
			usuario.setNombre(usuarioUpdate.getNombre());
			usuario.setDireccion(usuarioUpdate.getDireccion());
			usuario.setCorreo_electronico(usuarioUpdate.getCorreo_electronico());
			usuario.setTipo_usuario(usuarioUpdate.getTipo_usuario());
			
			usuarioService.save(usuario);
			return new ResponseEntity<>("Guardado",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Error usuario no encontrado",HttpStatus.BAD_REQUEST);
		}
		
	}

}
