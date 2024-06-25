package com.sena.biblioTeca_JoseGasca.interfacesService;

import java.util.List;
import java.util.Optional;


import com.sena.biblioTeca_JoseGasca.models.usuario;

public interface I_usuarioService {
    
    public String save(usuario usuario);    
    public List<usuario> findAll();
    List<usuario> filtroUsuario(String filtro);
    public Optional<usuario> findOne(String id);
    public int delete(String id);

}
