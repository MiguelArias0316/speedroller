package com.project.speedroller.Controller;

import com.project.speedroller.Model.Estudiante;
import com.project.speedroller.Model.Instructor;
import com.project.speedroller.Model.Clase;
import com.project.speedroller.Service.EstudianteService;
import com.project.speedroller.Service.InstructorService;
import com.project.speedroller.Service.ClaseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Set;

@Tag(name = "Clase Controller", description = "Manejo de clases, asignación de instructores y estudiantes")
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

    @Operation(
        summary = "Obtener lista de clases",
        description = "Este servicio devuelve todas las clases registradas, incluyendo su instructor y estudiantes asignados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de clases obtenida correctamente",
                content = @Content(schema = @Schema(implementation = Clase.class))),
            @ApiResponse(responseCode = "500", description = "Error al obtener las clases")
        }
    )
    @GetMapping
    public String listarClases(Model model) {
        List<Clase> clases = claseService.listarTodas();
        model.addAttribute("clases", clases);
        return "clases/lista";
    }

    @Operation(
        summary = "Mostrar formulario para registrar nueva clase",
        description = "Este servicio muestra el formulario para registrar una nueva clase, permitiendo la selección de instructores y estudiantes.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Formulario de registro mostrado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al mostrar el formulario de registro")
        }
    )
    @GetMapping("/nueva")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("clase", new Clase());
        model.addAttribute("instructores", instructorService.listarTodos());
        model.addAttribute("estudiantes", estudianteService.listarTodos());
        return "clases/registro";
    }

    @Operation(
        summary = "Guardar nueva clase",
        description = "Este servicio guarda una nueva clase en el sistema, asignando un instructor y los estudiantes seleccionados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Clase registrada correctamente",
                content = @Content(schema = @Schema(implementation = Clase.class))),
            @ApiResponse(responseCode = "500", description = "Error al registrar clase")
        }
    )
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

    @Operation(
        summary = "Mostrar formulario para editar clase",
        description = "Este servicio muestra el formulario para editar los detalles de una clase existente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Formulario de edición mostrado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al mostrar el formulario de edición")
        }
    )
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Clase clase = claseService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
        model.addAttribute("clase", clase);
        model.addAttribute("instructores", instructorService.listarTodos());
        model.addAttribute("estudiantes", estudianteService.listarTodos());
        return "clases/editar";
    }

    @Operation(
        summary = "Actualizar clase",
        description = "Este servicio actualiza los detalles de una clase existente, incluyendo los estudiantes asignados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Clase actualizada correctamente",
                content = @Content(schema = @Schema(implementation = Clase.class))),
            @ApiResponse(responseCode = "500", description = "Error al actualizar clase")
        }
    )
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

    @Operation(
        summary = "Eliminar clase",
        description = "Este servicio elimina una clase existente del sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Clase eliminada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al eliminar clase")
        }
    )
    @GetMapping("/eliminar/{id}")
    public String eliminarClase(@PathVariable Long id) {
        claseService.eliminarPorId(id);
        return "redirect:/clases";
    }
}
