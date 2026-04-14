package com.tBaronDar.springSecurity;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {

    @GetMapping("/")
    public String home(HttpServletRequest request){

        return "Welcome, session id: "+ request.getSession().getId();
    }
}
