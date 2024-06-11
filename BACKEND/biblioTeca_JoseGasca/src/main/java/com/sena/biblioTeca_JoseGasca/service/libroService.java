package com.sena.biblioTeca_JoseGasca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.biblioTeca_JoseGasca.interfaces.I_Libro;
import com.sena.biblioTeca_JoseGasca.interfacesService.I_LibroService;
import com.sena.biblioTeca_JoseGasca.models.libro;

@Service
public class libroService implements I_LibroService {
    

    @Autowired
    private I_Libro data;


    @Override
    public String save(libro libro) {
        data.save(libro);
        return libro.getId();
    }

    @Override
    public List<libro> findAll() {
        List<libro> listaLibro = (List<libro>) data.findAll();
        
        return listaLibro;
    }


    // @Override
	// public List<libro> filtroLibro(String filtro) {
	// 	List <libro> listaLibro=data.filtroLibro(filtro);
	// 	return listaLibro;
	// }

    @Override
    public Optional<libro> findOne(String id) {
        Optional<libro> libro = data.findById(id);
        
        return libro;
    }

    @Override
    public int delete(String id) {
        data.deleteById(id);
        return 1;
    }


}
