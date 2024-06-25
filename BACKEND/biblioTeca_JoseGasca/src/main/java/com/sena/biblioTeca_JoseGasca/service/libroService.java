package com.sena.biblioTeca_JoseGasca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sena.biblioTeca_JoseGasca.interfaces.I_Libro;
import com.sena.biblioTeca_JoseGasca.interfacesService.I_LibroService;
import com.sena.biblioTeca_JoseGasca.models.libro;

@Service
public class libroService implements I_LibroService {
    

    @Autowired
    private I_Libro data;

    @Override
    public String save(libro libro) {
        try {
            data.save(libro);
            return libro.getId();
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("El ISBN ya existe");
        }
    }

    @Override
    public List<libro> findAll() {
        List<libro> listaLibro = (List<libro>) data.findAll();
        
        return listaLibro;
    }

    @Override
    public Optional<libro> findOne(String id) {
        Optional<libro> libro = data.findById(id);
        
        return libro;
    }

    @Override
    public int delete(String id) {
        data.deleteById(id);
        return 1;
    }

    @Override
    public List<libro> filtroLibro(String filtro) {
        return data.filtroLibro(filtro);
    }



    @Override
    public boolean updateLibro(libro libroUpdate) {
        Optional<libro> optLibro = data.findById(libroUpdate.getId());
        if (optLibro.isPresent()) {
            libro libro = optLibro.get();

            int totalEjemplares = libroUpdate.getNum_ejem_dis() + libroUpdate.getNum_ejem_ocup();
            if (totalEjemplares < libro.getNum_ejem_ocup()) {
                return false;
            }

            libro.setTitulo(libroUpdate.getTitulo());
            libro.setAutor(libroUpdate.getAutor());
            libro.setIsbn(libroUpdate.getIsbn());
            libro.setGenero(libroUpdate.getGenero());
            libro.setNum_ejem_dis(libroUpdate.getNum_ejem_dis());
            libro.setNum_ejem_ocup(libroUpdate.getNum_ejem_ocup());

            try {
                data.save(libro);
            } catch (DataIntegrityViolationException e) {
                throw new RuntimeException("El ISBN ya existe");
            }
            return true;
        } else {
            return false;
        }
    }


}
