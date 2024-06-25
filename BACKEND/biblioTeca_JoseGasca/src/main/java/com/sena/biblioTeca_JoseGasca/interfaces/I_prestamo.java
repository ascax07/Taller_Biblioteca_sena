package com.sena.biblioTeca_JoseGasca.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sena.biblioTeca_JoseGasca.models.prestamo;


@Repository
public interface I_prestamo  extends CrudRepository<prestamo,String>{
    
    @Query("SELECT p FROM prestamo p WHERE p.usuario.nombre LIKE %?1%")
    List<prestamo> filtroPrestamo(String filtro);

}
