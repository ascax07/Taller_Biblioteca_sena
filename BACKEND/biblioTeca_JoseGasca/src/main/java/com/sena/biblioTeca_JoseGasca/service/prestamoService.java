package com.sena.biblioTeca_JoseGasca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.biblioTeca_JoseGasca.interfaces.I_prestamo;
import com.sena.biblioTeca_JoseGasca.interfaces.I_prestamo;
import com.sena.biblioTeca_JoseGasca.interfacesService.I_prestamoService;
import com.sena.biblioTeca_JoseGasca.models.prestamo;
import com.sena.biblioTeca_JoseGasca.models.prestamo;


@Service
public class prestamoService  implements I_prestamoService{

     @Autowired
    private I_prestamo data;


    @Override
    public String save(prestamo prestamo) {
        data.save(prestamo);
        return prestamo.getId();
    }

    @Override
    public List<prestamo> findAll() {
        List<prestamo> listaPrestamo = (List<prestamo>) data.findAll();
        
        return listaPrestamo;
    }


    // @Override
	// public List<prestamo> filtroprestamo(String filtro) {
	// 	List <prestamo> listaPrestamo=data.filtroprestamo(filtro);
	// 	return listaPrestamo;
	// }

    @Override
    public Optional<prestamo> findOne(String id) {
        Optional<prestamo> prestamo = data.findById(id);
        
        return prestamo;
    }

    @Override
    public int delete(String id) {
        data.deleteById(id);
        return 1;
    }
    
}
