package com.project.speedroller.Service;

import com.project.speedroller.Model.Clase;
import com.project.speedroller.Repository.ClaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaseService {

    private final ClaseRepository claseRepository;

    public ClaseService(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    public Clase guardarClase(Clase clase) {
        return claseRepository.save(clase);
    }

    public List<Clase> listarTodas() {
        return claseRepository.findAll();
    }

    public Optional<Clase> buscarPorId(Long id) {
        return claseRepository.findById(id);
    }

    public void eliminarPorId(Long id) {
        claseRepository.deleteById(id);
    }
}