package com.tBaronDar.springSecurity.service;

import com.tBaronDar.springSecurity.model.User;
import com.tBaronDar.springSecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo ur;

    public User saveUser(User user){
        return ur.save(user);
    }
}
