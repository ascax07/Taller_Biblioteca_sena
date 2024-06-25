package com.sena.biblioTeca_JoseGasca.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.sena.biblioTeca_JoseGasca.models.usuario;



@Repository
public interface I_usuario  extends CrudRepository<usuario,String>{
    
        @Query("SELECT u FROM usuario u WHERE "
        + "u.nombre LIKE %?1% OR "
        + "u.correo_electronico LIKE %?1%")

    List<usuario> filtroUsuario(String filtro);


    @Query("SELECT COUNT(u) > 0 FROM usuario u WHERE u.correo_electronico = ?1 AND u.tipo_usuario = ?2")
    boolean existsByCorreoAndTipoUsuario(String correo_electronico, String tipo_usuario);

}
