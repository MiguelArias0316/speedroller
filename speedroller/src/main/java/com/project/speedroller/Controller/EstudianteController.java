package com.project.speedroller.Controller;

import com.project.speedroller.Model.Estudiante;
import com.project.speedroller.Service.EstudianteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    // Listar estudiantes
    @GetMapping
    public String listarEstudiantes(Model model) {
        List<Estudiante> estudiantes = estudianteService.listarTodos();
        model.addAttribute("estudiantes", estudiantes);
        return "estudiantes/lista";
    }

    // Formulario para nuevo estudiante
    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "estudiantes/registro";
    }

    // Procesar registro de estudiante
    @PostMapping("/guardar")
    public String guardarEstudiante(@Valid @ModelAttribute("estudiante") Estudiante estudiante,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "estudiantes/registro";
        }
        estudianteService.guardarEstudiante(estudiante);
        return "redirect:/estudiantes";
    }

    // Editar estudiante
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Estudiante estudiante = estudianteService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
        model.addAttribute("estudiante", estudiante);
        return "estudiantes/editar";
    }

    // Procesar edición
    @PostMapping("/actualizar/{id}")
    public String actualizarEstudiante(@PathVariable Long id,
                                      @Valid @ModelAttribute("estudiante") Estudiante estudiante,
                                      BindingResult result, Model model) {
        if (result.hasErrors()) {
            estudiante.setId(id);
            return "estudiantes/editar";
        }
        estudianteService.guardarEstudiante(estudiante);
        return "redirect:/estudiantes";
    }

    // Eliminar estudiante
    @GetMapping("/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable Long id) {
        estudianteService.eliminarPorId(id);
        return "redirect:/estudiantes";
    }
}