package com.project.speedroller.Controller;

import com.project.speedroller.Model.Estudiante;
import com.project.speedroller.Model.Instructor; 
import com.project.speedroller.Model.Clase;
import com.project.speedroller.Service.EstudianteService;
import com.project.speedroller.Service.InstructorService;
import com.project.speedroller.Service.ClaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/clases")
public class ClaseController {

    private final ClaseService claseService;
    private final InstructorService instructorService;
    private final EstudianteService estudianteService;

    public ClaseController(ClaseService claseService, InstructorService instructorService, EstudianteService estudianteService) {
        this.claseService = claseService;
        this.instructorService = instructorService;
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public String listarClases(Model model) {
        List<Clase> clases = claseService.listarTodas();
        model.addAttribute("clases", clases);
        return "clases/lista";
    }

    @GetMapping("/nueva")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("clase", new Clase());
        model.addAttribute("instructores", instructorService.listarTodos());
        model.addAttribute("estudiantes", estudianteService.listarTodos());
        return "clases/registro";
    }

    @PostMapping("/guardar")
    public String guardarClase(@Valid @ModelAttribute("clase") Clase clase,
                              BindingResult result,
                              @RequestParam(required = false) List<Long> estudiantesSeleccionados,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("instructores", instructorService.listarTodos());
            model.addAttribute("estudiantes", estudianteService.listarTodos());
            return "clases/registro";
        }

        // Asignar estudiantes seleccionados a la clase
        if (estudiantesSeleccionados != null) {
            List<Estudiante> estudiantes = estudianteService.listarTodos().stream()
                    .filter(e -> estudiantesSeleccionados.contains(e.getId()))
                    .toList();
            clase.setEstudiantes(Set.copyOf(estudiantes));
        }

        claseService.guardarClase(clase);
        return "redirect:/clases";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Clase clase = claseService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
        model.addAttribute("clase", clase);
        model.addAttribute("instructores", instructorService.listarTodos());
        model.addAttribute("estudiantes", estudianteService.listarTodos());
        return "clases/editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarClase(@PathVariable Long id,
                                 @Valid @ModelAttribute("clase") Clase clase,
                                 BindingResult result,
                                 @RequestParam(required = false) List<Long> estudiantesSeleccionados,
                                 Model model) {
        if (result.hasErrors()) {
            clase.setId(id);
            model.addAttribute("instructores", instructorService.listarTodos());
            model.addAttribute("estudiantes", estudianteService.listarTodos());
            return "clases/editar";
        }

        // Asignar estudiantes seleccionados a la clase
        if (estudiantesSeleccionados != null) {
            List<Estudiante> estudiantes = estudianteService.listarTodos().stream()
                    .filter(e -> estudiantesSeleccionados.contains(e.getId()))
                    .toList();
            clase.setEstudiantes(Set.copyOf(estudiantes));
        } else {
            clase.setEstudiantes(Set.of());
        }

        claseService.guardarClase(clase);
        return "redirect:/clases";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarClase(@PathVariable Long id) {
        claseService.eliminarPorId(id);
        return "redirect:/clases";
    }
}