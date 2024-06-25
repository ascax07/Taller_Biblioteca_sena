package com.sena.biblioTeca_JoseGasca.interfacesService;

import java.util.List;
import java.util.Optional;

import com.sena.biblioTeca_JoseGasca.models.libro;

public interface I_LibroService {

    public String save(libro libro);    
    public List<libro> findAll();
    public Optional<libro> findOne(String id);
    public int delete(String id);
    List<libro> filtroLibro(String filtro);
    boolean updateLibro(libro libroUpdate); 

}
