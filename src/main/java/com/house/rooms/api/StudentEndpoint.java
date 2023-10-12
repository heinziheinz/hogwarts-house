package com.house.rooms.api;


import com.house.rooms.data.Room;
import com.house.rooms.data.Student;
import com.house.rooms.data.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("students")
public class StudentEndpoint {
    private final StudentRepository studentRepository;

    public StudentEndpoint(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @GetMapping
    List<Student> findAll() {
        return studentRepository.findAll();
    }
    @PostMapping
    Student save(@RequestBody Student student) {
        return studentRepository.save(student);
    }

}
