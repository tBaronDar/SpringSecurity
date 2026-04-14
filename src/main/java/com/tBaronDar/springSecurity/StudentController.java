package com.tBaronDar.springSecurity;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    List<Student> students = new ArrayList<>(List.of(
            new Student(1, "Themis", "Java"),
            new Student(2,"Loula","Rouries")
    ));

    @GetMapping("/students")
    public List<Student> getStudents(){
        return students;
    }

    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest req){

        return (CsrfToken) req.getAttribute("_csrf");
    }

    @PostMapping("/students")
    public void addStudent(@RequestBody Student st){
        students.add(st);
        System.out.println("student "+st.toString()+" added");
    }
}
