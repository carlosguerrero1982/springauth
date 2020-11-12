package com.example.demo.student;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {


    private static final List<Student> STUDENTS = Arrays.asList(

            new Student(1, "Carlos Guerrero"),
            new Student(2, "Pedro Gomez"),
            new Student(3, "Juan Garcia")
    );

    List<Student> list = new ArrayList<Student>(STUDENTS);


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents(){

        return list;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void registerNewStudent(@RequestBody Student student){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        System.out.println(authentication.getAuthorities());
        System.out.println("registerNewStudent");
            System.out.println(student);

            list.add(new Student(student.getStudentId(),student.getStudentName()));


    }

    @DeleteMapping(path="{studentId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public int deleteStudent(@PathVariable("studentId") Integer studentId){

        System.out.println("deleteStudent");
            System.out.println(studentId);

        Optional<Student> studentMaybe = selectStudentById(studentId);
        if (studentMaybe.isEmpty()){
            return 0;
        }
        list.remove(studentMaybe.get());
        return 1;


    }

    @PutMapping(path="{studentId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student studentUpdate){

        System.out.println("updateStudent");
        System.out.println(String.format("%s %s",studentId,studentUpdate));

            selectStudentById(studentId)

                    .map(student -> {

                        int indexPersonDelete = list.indexOf(student);

                        if (indexPersonDelete >= 0) {

                            list.set(indexPersonDelete, new Student(studentId, studentUpdate.getStudentName()));
                            return 1;
                        }
                        return 0;
                    })
                    .orElse(0);
        }



    @GetMapping(path="{studentId}")
    public Optional<Student> selectStudentById(@PathVariable("studentId") Integer studentId) {
        return list.stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst();
    }



}
