package com.sena.biblioTeca_JoseGasca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sena.biblioTeca_JoseGasca.interfaces.I_usuario;
import com.sena.biblioTeca_JoseGasca.interfacesService.I_usuarioService;
import com.sena.biblioTeca_JoseGasca.models.usuario;


@Service
public class usuarioService implements I_usuarioService{
    
    @Autowired
    private I_usuario data;


    @Override
    public String save(usuario usuario) {
        if (data.existsByCorreoAndTipoUsuario(usuario.getCorreo_electronico(), usuario.getTipo_usuario())) {
            throw new IllegalArgumentException("Ya existe un usuario con este correo y rol.");
        }
        data.save(usuario);
        return usuario.getId();
    }

    @Override
    public List<usuario> findAll() {
        List<usuario> listaUsuario = (List<usuario>) data.findAll();
        
        return listaUsuario;
    }


  @Override
    public List<usuario> filtroUsuario(String filtro) {
        return data.filtroUsuario(filtro);
    }
    @Override
    public Optional<usuario> findOne(String id) {
        Optional<usuario> usuario = data.findById(id);
        
        return usuario;
    }

    @Override
    public int delete(String id) {
        data.deleteById(id);
        return 1;
    }


}
