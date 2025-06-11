package com.project.speedroller.Controller;

import com.project.speedroller.Model.Estudiante;
import com.project.speedroller.Service.EstudianteService;

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

@Tag(name = "Estudiante Controller", description = "Manejo de estudiantes en la plataforma")
@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @Operation(
        summary = "Obtener lista de estudiantes",
        description = "Este servicio devuelve todos los estudiantes registrados en la plataforma.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de estudiantes obtenida correctamente",
                content = @Content(schema = @Schema(implementation = Estudiante.class))),
            @ApiResponse(responseCode = "500", description = "Error al obtener los estudiantes")
        }
    )
    @GetMapping
    public String listarEstudiantes(Model model) {
        List<Estudiante> estudiantes = estudianteService.listarTodos();
        model.addAttribute("estudiantes", estudiantes);
        return "estudiantes/lista";
    }

    @Operation(
        summary = "Mostrar formulario para registrar estudiante",
        description = "Este servicio muestra el formulario para agregar un nuevo estudiante a la plataforma.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Formulario mostrado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al mostrar formulario")
        }
    )
    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        return "estudiantes/registro";
    }

    @Operation(
        summary = "Guardar nuevo estudiante",
        description = "Este servicio guarda un nuevo estudiante en la plataforma.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Estudiante registrado correctamente",
                content = @Content(schema = @Schema(implementation = Estudiante.class))),
            @ApiResponse(responseCode = "500", description = "Error al guardar estudiante")
        }
    )
    @PostMapping("/guardar")
    public String guardarEstudiante(@Valid @ModelAttribute("estudiante") Estudiante estudiante,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "estudiantes/registro";
        }
        estudianteService.guardarEstudiante(estudiante);
        return "redirect:/estudiantes";
    }

    @Operation(
        summary = "Mostrar formulario de edición de estudiante",
        description = "Este servicio muestra el formulario para editar los detalles de un estudiante existente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Formulario de edición mostrado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al mostrar formulario de edición")
        }
    )
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Estudiante estudiante = estudianteService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
        model.addAttribute("estudiante", estudiante);
        return "estudiantes/editar";
    }

    @Operation(
        summary = "Actualizar estudiante",
        description = "Este servicio actualiza los detalles de un estudiante en la plataforma.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado correctamente",
                content = @Content(schema = @Schema(implementation = Estudiante.class))),
            @ApiResponse(responseCode = "500", description = "Error al actualizar estudiante")
        }
    )
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

    @Operation(
        summary = "Eliminar estudiante",
        description = "Este servicio elimina a un estudiante de la plataforma.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Estudiante eliminado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al eliminar estudiante")
        }
    )
    @GetMapping("/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable Long id) {
        estudianteService.eliminarPorId(id);
        return "redirect:/estudiantes";
    }
}
