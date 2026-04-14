package com.tBaronDar.springSecurity.service;

import com.tBaronDar.springSecurity.model.User;
import com.tBaronDar.springSecurity.model.UserPrincipal;
import com.tBaronDar.springSecurity.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo ur;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u =ur.findByUsername(username);

        if (u==null){
            throw new UsernameNotFoundException("User 404");
        }

        return new UserPrincipal(u);
    }
}
