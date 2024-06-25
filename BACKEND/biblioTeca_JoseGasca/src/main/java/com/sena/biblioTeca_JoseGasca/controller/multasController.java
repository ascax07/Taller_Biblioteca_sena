package com.sena.biblioTeca_JoseGasca.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.biblioTeca_JoseGasca.interfacesService.I_multasService;
import com.sena.biblioTeca_JoseGasca.interfacesService.I_prestamoService;
import com.sena.biblioTeca_JoseGasca.interfacesService.I_usuarioService;
import com.sena.biblioTeca_JoseGasca.models.multas;
import com.sena.biblioTeca_JoseGasca.models.prestamo;
import com.sena.biblioTeca_JoseGasca.models.usuario;

import jakarta.validation.Valid;



@RequestMapping("/api/v1/multas")
@RestController
@CrossOrigin
public class multasController {

    @Autowired
    private I_multasService multasService;

    @Autowired
    private I_usuarioService usuarioService;

    @Autowired
    private I_prestamoService prestamoService;

    @PostMapping("/")
    public ResponseEntity<Object> save(@Valid @RequestBody Map<String, Object> payload, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            String usuarioId = (String) payload.get("usuario_id");
            String prestamoId = (String) payload.get("prestamo_id");
            double valorMulta = Double.parseDouble((String) payload.get("valor_multa"));
            String fechaMulta = (String) payload.get("fecha_multa");
            String estado = (String) payload.get("estado");

            usuario usuario = usuarioService.findOne(usuarioId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
            prestamo prestamo = prestamoService.findOne(prestamoId).orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

            if (!prestamo.getUsuario().getId().equals(usuarioId)) {
                throw new IllegalArgumentException("El préstamo no está asociado al usuario multado");
            }

            multas nuevaMulta = new multas();
            nuevaMulta.setUsuario(usuario);
            nuevaMulta.setPrestamo(prestamo);
            nuevaMulta.setValor_multa(valorMulta);
            nuevaMulta.setFecha_multa(fechaMulta);
            nuevaMulta.setEstado(estado);

            multasService.save(nuevaMulta);
            return new ResponseEntity<>(nuevaMulta, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> findAll() {
        var listaMultas = multasService.findAll();
        return new ResponseEntity<>(listaMultas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findOne(@PathVariable("id") String id) {
        var multas = multasService.findOne(id);
        return new ResponseEntity<>(multas, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        multasService.delete(id);
        return new ResponseEntity<>("Registro Eliminado", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @Valid @RequestBody Map<String, Object> payload) {
        var multaExistente = multasService.findOne(id).orElse(null);
        if (multaExistente != null) {
            try {
                String usuarioId = (String) payload.get("usuario_id");
                String prestamoId = (String) payload.get("prestamo_id");
                double valorMulta = Double.parseDouble((String) payload.get("valor_multa"));
                String fechaMulta = (String) payload.get("fecha_multa");
                String estado = (String) payload.get("estado");

                usuario usuario = usuarioService.findOne(usuarioId).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
                prestamo prestamo = prestamoService.findOne(prestamoId).orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

                if (!prestamo.getUsuario().getId().equals(usuarioId)) {
                    throw new IllegalArgumentException("El préstamo no está asociado al usuario multado");
                }

                multaExistente.setUsuario(usuario);
                multaExistente.setPrestamo(prestamo);
                multaExistente.setValor_multa(valorMulta);
                multaExistente.setFecha_multa(fechaMulta);
                multaExistente.setEstado(estado);

                multasService.save(multaExistente);
                return new ResponseEntity<>("Guardado", HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Error multa no encontrada", HttpStatus.BAD_REQUEST);
        }
    }
}