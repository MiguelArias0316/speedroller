package com.project.speedroller.Controller;

import com.project.speedroller.Model.Instructor;
import com.project.speedroller.Service.InstructorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/instructores")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public String listarInstructores(Model model) {
        List<Instructor> instructores = instructorService.listarTodos();
        model.addAttribute("instructores", instructores);
        return "instructores/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructores/registro";
    }

    @PostMapping("/guardar")
    public String guardarInstructor(@Valid @ModelAttribute("instructor") Instructor instructor,
                                   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "instructores/registro";
        }
        instructorService.guardarInstructor(instructor);
        return "redirect:/instructores";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Instructor instructor = instructorService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Id inválido: " + id));
        model.addAttribute("instructor", instructor);
        return "instructores/editar";
    }

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

    @GetMapping("/eliminar/{id}")
    public String eliminarInstructor(@PathVariable Long id) {
        instructorService.eliminarPorId(id);
        return "redirect:/instructores";
    }
}