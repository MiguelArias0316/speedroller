package com.project.speedroller.Repository;

import com.project.speedroller.Model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}