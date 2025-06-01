package com.project.speedroller.Controller;

import com.project.speedroller.Model.Clase;
import com.project.speedroller.Service.ClaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/horarios")
public class HorarioController {

    private final ClaseService claseService;

    public HorarioController(ClaseService claseService) {
        this.claseService = claseService;
    }

    @GetMapping("/estudiantes")
    public String mostrarHorarioEstudiantes(Model model) {
        List<Clase> clases = claseService.listarTodas();
        model.addAttribute("clases", clases);
        return "horarios/estudiantes";
    }

    @GetMapping("/instructores")
    public String mostrarHorarioInstructores(Model model) {
        List<Clase> clases = claseService.listarTodas();
        model.addAttribute("clases", clases);
        return "horarios/instructores";
    }
}

