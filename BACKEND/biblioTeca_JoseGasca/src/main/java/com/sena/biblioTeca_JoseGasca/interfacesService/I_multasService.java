package com.sena.biblioTeca_JoseGasca.interfacesService;


import java.util.List;
import java.util.Optional;

import com.sena.biblioTeca_JoseGasca.models.multas;

public interface I_multasService {
    
       public String save(multas multas);    
    public List<multas> findAll();
    //public List<multas> filtromultas (String filtro);
    public Optional<multas> findOne(String id);
    public int delete(String id);
    
}
