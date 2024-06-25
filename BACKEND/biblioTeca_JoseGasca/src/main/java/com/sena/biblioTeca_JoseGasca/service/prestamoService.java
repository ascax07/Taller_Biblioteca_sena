package com.sena.biblioTeca_JoseGasca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.biblioTeca_JoseGasca.interfaces.I_prestamo;
import com.sena.biblioTeca_JoseGasca.interfacesService.I_LibroService;
import com.sena.biblioTeca_JoseGasca.interfacesService.I_prestamoService;
import com.sena.biblioTeca_JoseGasca.interfacesService.I_usuarioService;
import com.sena.biblioTeca_JoseGasca.models.libro;
import com.sena.biblioTeca_JoseGasca.models.prestamo;
import com.sena.biblioTeca_JoseGasca.models.usuario;



@Service
public class prestamoService  implements I_prestamoService{

    @Autowired
    private I_prestamo data;

    @Autowired
    private I_usuarioService usuarioService;

    @Autowired
    private I_LibroService libroService;

    @Override
    public String save(prestamo prestamo) {
        // Validación lógica adicional
        Optional<usuario> usuarioOpt = usuarioService.findOne(prestamo.getUsuario().getId());
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("El usuario no existe en el sistema");
        }

        Optional<libro> libroOpt = libroService.findOne(prestamo.getLibro().getId());
        if (libroOpt.isEmpty()) {
            throw new IllegalArgumentException("El libro no existe en el sistema");
        }

        libro libro = libroOpt.get();
        if (libro.getNum_ejem_dis() <= 0) {
            throw new IllegalArgumentException("El libro no está disponible para préstamo");
        }

        
        // Validar que fecha_prestamo no es después de fecha_devolucion
        if (prestamo.getFecha_prestamo().compareTo(prestamo.getFecha_devolucion()) > 0) {
            throw new IllegalArgumentException("La fecha de préstamo no puede ser después de la fecha de devolución");
        }
    
        // Validar que fecha_devolucion no es antes de fecha_prestamo
        if (prestamo.getFecha_devolucion().compareTo(prestamo.getFecha_prestamo()) < 0) {
            throw new IllegalArgumentException("La fecha de devolución no puede ser antes de la fecha de préstamo");
        }
    

        data.save(prestamo);
        return prestamo.getId();
    }

    @Override
    public List<prestamo> findAll() {
        return (List<prestamo>) data.findAll();
    }

    @Override
    public Optional<prestamo> findOne(String id) {
        return data.findById(id);
    }

    @Override
    public int delete(String id) {
        data.deleteById(id);
        return 1;
    }

    @Override
    public List<prestamo> filtroPrestamo(String filtro) {
        return data.filtroPrestamo(filtro);
    }
}
