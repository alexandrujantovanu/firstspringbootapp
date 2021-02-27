package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    @Resource(name = "studentService")
    private StudentService studentService;

    @GetMapping()
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping()
    public void registerNewStudent(@RequestBody Student student) {
        studentService.addNewStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id) {
        studentService.deleteStudent(id);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Long id, @RequestParam("name") String name,
                              @RequestParam("email") String email) {

        studentService.updateStudent(id, name, email);
    }
}
