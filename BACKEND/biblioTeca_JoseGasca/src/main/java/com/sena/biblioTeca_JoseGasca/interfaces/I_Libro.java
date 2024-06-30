package com.sena.biblioTeca_JoseGasca.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sena.biblioTeca_JoseGasca.models.libro;


@Repository
public interface I_Libro  extends CrudRepository<libro,String>{
    

        @Query("SELECT l FROM libro l WHERE "
        + "l.titulo LIKE %?1% OR "
        + "l.autor  LIKE %?1% OR "
        + "l.isbn   LIKE %?1% OR "
        + "l.genero LIKE %?1%")

    List<libro> filtroLibro(String filtro);


}
