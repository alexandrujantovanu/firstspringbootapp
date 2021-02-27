package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    @Resource
    private StudentRepository studentRepository;

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()) {
            throw new IllegalStateException("Email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists) {
            throw new IllegalStateException("Student with id " + studentId + " does not exist");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long id, String name, String email) {
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("student with id " + id + " does not exist!"));

        if(name != null && name.length() > 0 && !Objects.equals(name, student.getName())) {
            student.setName(name);
        }

        if(email != null && email.length() > 0 && !Objects.equals(email, student.getEmail())) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);

            if(studentOptional.isPresent()) {
                throw new IllegalStateException("Email is already taken");
            }
            student.setEmail(email);
        }
    }
}
