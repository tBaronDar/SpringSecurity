package com.tBaronDar.springSecurity.service;

import com.tBaronDar.springSecurity.model.User;
import com.tBaronDar.springSecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo ur;
    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder(12);

    public User saveUser(User user){
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        return ur.save(user);
    }
}
