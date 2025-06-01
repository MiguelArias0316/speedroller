package com.project.speedroller.Service;

import com.project.speedroller.Model.Estudiante;
import com.project.speedroller.Repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public Estudiante guardarEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    public List<Estudiante> listarTodos() {
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> buscarPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    public void eliminarPorId(Long id) {
        estudianteRepository.deleteById(id);
    }
}