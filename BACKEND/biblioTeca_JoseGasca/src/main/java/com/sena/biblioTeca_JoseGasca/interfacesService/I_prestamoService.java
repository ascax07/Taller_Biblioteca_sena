package com.sena.biblioTeca_JoseGasca.interfacesService;

import java.util.List;
import java.util.Optional;

import com.sena.biblioTeca_JoseGasca.models.prestamo;

public interface I_prestamoService {
 
      public String save(prestamo prestamo);    
    public List<prestamo> findAll();
    //public List<prestamo> filtroprestamo (String filtro);
    public Optional<prestamo> findOne(String id);
    public int delete(String id);


}
