package com.sena.biblioTeca_JoseGasca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.biblioTeca_JoseGasca.interfaces.I_Libro;
import com.sena.biblioTeca_JoseGasca.interfaces.I_multas;
import com.sena.biblioTeca_JoseGasca.interfacesService.I_multasService;
import com.sena.biblioTeca_JoseGasca.models.libro;
import com.sena.biblioTeca_JoseGasca.models.multas;

@Service
public class multasService implements I_multasService{

    @Autowired
    private I_multas data;


    @Override
    public String save(multas multas) {
        data.save(multas);
        return multas.getId();
    }

    @Override
    public List<multas> findAll() {
        List<multas> listaMultas = (List<multas>) data.findAll();
        
        return listaMultas;
    }


    // @Override
	// public List<libro> filtroLibro(String filtro) {
	// 	List <libro> listaMultas=data.filtroLibro(filtro);
	// 	return listaMultas;
	// }

    @Override
    public Optional<multas> findOne(String id) {
        Optional<multas> multas = data.findById(id);
        
        return multas;
    }

    @Override
    public int delete(String id) {
        data.deleteById(id);
        return 1;
    }

    
}
