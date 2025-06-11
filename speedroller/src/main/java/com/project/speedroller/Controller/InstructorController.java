package com.project.speedroller.Controller;

import com.project.speedroller.Model.Instructor;
import com.project.speedroller.Service.InstructorService;

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

@Tag(name = "Instructor Controller", description = "Manejo de instructores en la plataforma")
@Controller
@RequestMapping("/instructores")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @Operation(
        summary = "Obtener lista de instructores",
        description = "Este servicio devuelve todos los instructores registrados en la plataforma.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de instructores obtenida correctamente",
                content = @Content(schema = @Schema(implementation = Instructor.class))),
            @ApiResponse(responseCode = "500", description = "Error al obtener los instructores")
        }
    )
    @GetMapping
    public String listarInstructores(Model model) {
        List<Instructor> instructores = instructorService.listarTodos();
        model.addAttribute("instructores", instructores);
        return "instructores/lista";
    }

    @Operation(
        summary = "Mostrar formulario para registrar instructor",
        description = "Este servicio muestra el formulario para agregar un nuevo instructor a la plataforma.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Formulario mostrado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al mostrar formulario")
        }
    )
    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructores/registro";
    }

    @Operation(
        summary = "Guardar nuevo instructor",
        description = "Este servicio guarda un nuevo instructor en la plataforma.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Instructor guardado correctamente",
                content = @Content(schema = @Schema(implementation = Instructor.class))),
            @ApiResponse(responseCode = "500", description = "Error al guardar instructor")
        }
    )
    @PostMapping("/guardar")
    public String guardarInstructor(@Valid @ModelAttribute("instructor") Instructor instructor,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "instructores/registro";
        }
        instructorService.guardarInstructor(instructor);
        return "redirect:/instructores";
    }

    @Operation(
        summary = "Mostrar formulario de edición de instructor",
        description = "Este servicio muestra el formulario para editar los detalles de un instructor existente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Formulario de edición mostrado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al mostrar formulario de edición")
        }
    )
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Instructor instructor = instructorService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
        model.addAttribute("instructor", instructor);
        return "instructores/editar";
    }

    @Operation(
        summary = "Actualizar instructor",
        description = "Este servicio actualiza los detalles de un instructor en la plataforma.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Instructor actualizado correctamente",
                content = @Content(schema = @Schema(implementation = Instructor.class))),
            @ApiResponse(responseCode = "500", description = "Error al actualizar instructor")
        }
    )
    @PostMapping("/actualizar/{id}")
    public String actualizarInstructor(@PathVariable Long id,
                                       @Valid @ModelAttribute("instructor") Instructor instructor,
                                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            instructor.setId(id);
            return "instructores/editar";
        }
        instructorService.guardarInstructor(instructor);
        return "redirect:/instructores";
    }

    @Operation(
        summary = "Eliminar instructor",
        description = "Este servicio elimina a un instructor de la plataforma.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Instructor eliminado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error al eliminar instructor")
        }
    )
    @GetMapping("/eliminar/{id}")
    public String eliminarInstructor(@PathVariable Long id) {
        instructorService.eliminarPorId(id);
        return "redirect:/instructores";
    }
}