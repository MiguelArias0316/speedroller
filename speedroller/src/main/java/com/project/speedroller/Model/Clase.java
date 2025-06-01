package com.project.speedroller.Model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clase")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nivel;

    private String horario;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToMany
    @JoinTable(
        name = "clase_estudiante",
        joinColumns = @JoinColumn(name = "clase_id"),
        inverseJoinColumns = @JoinColumn(name = "estudiante_id")
    )
    private Set<Estudiante> estudiantes;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }
    public Set<Estudiante> getEstudiantes() { return estudiantes; }
    public void setEstudiantes(Set<Estudiante> estudiantes) { this.estudiantes = estudiantes; }
}