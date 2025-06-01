package com.project.speedroller.Service;

import com.project.speedroller.Model.Instructor;
import com.project.speedroller.Repository.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public Instructor guardarInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public List<Instructor> listarTodos() {
        return instructorRepository.findAll();
    }

    public Optional<Instructor> buscarPorId(Long id) {
        return instructorRepository.findById(id);
    }

    public void eliminarPorId(Long id) {
        instructorRepository.deleteById(id);
    }
}