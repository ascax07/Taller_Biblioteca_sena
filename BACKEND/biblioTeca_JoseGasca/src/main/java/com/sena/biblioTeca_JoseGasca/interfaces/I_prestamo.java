package com.sena.biblioTeca_JoseGasca.interfaces;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sena.biblioTeca_JoseGasca.models.prestamo;


@Repository
public interface I_prestamo  extends CrudRepository<prestamo,String>{
    
}
