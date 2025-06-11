package com.project.speedroller.Controller;

import com.project.speedroller.Model.Clase;
import com.project.speedroller.Service.ClaseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(name = "Horario Controller", description = "Consulta y gestión de horarios de clases")
@Controller
@RequestMapping("/horarios")
public class HorarioController {

    private final ClaseService claseService;

    public HorarioController(ClaseService claseService) {
        this.claseService = claseService;
    }

    @Operation(
        summary = "Obtener horarios de clases para estudiantes",
        description = "Este servicio permite a los estudiantes ver los horarios de las clases a las que están asignados, incluyendo nivel, horario e instructor.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Horarios obtenidos correctamente",
                content = @Content(schema = @Schema(implementation = Clase.class))),
            @ApiResponse(responseCode = "500", description = "Error al obtener los horarios")
        }
    )
    @GetMapping("/estudiantes")
    public String mostrarHorarioEstudiantes(Model model) {
        List<Clase> clases = claseService.listarTodas();
        model.addAttribute("clases", clases);
        return "horarios/estudiantes";
    }

    @Operation(
        summary = "Obtener horarios de clases para instructores",
        description = "Este servicio permite a los instructores ver los horarios de clases asignados a ellos, incluyendo los estudiantes inscritos.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Horarios obtenidos correctamente",
                content = @Content(schema = @Schema(implementation = Clase.class))),
            @ApiResponse(responseCode = "500", description = "Error al obtener los horarios")
        }
    )
    @GetMapping("/instructores")
    public String mostrarHorarioInstructores(Model model) {
        List<Clase> clases = claseService.listarTodas();
        model.addAttribute("clases", clases);
        return "horarios/instructores";
    }
}