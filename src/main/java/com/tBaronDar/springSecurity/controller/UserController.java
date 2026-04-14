package com.tBaronDar.springSecurity.controller;

import com.tBaronDar.springSecurity.model.User;
import com.tBaronDar.springSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService us;

    @PostMapping("register")
    public User register(@RequestBody User user){
        return us.saveUser(user);
    }
}
