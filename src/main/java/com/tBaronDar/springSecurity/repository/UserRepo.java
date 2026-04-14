package com.tBaronDar.springSecurity.repository;

import com.tBaronDar.springSecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Step 1b:
 * create a JpaRepository and link it
 * with the User model.
 * Add a dsl method to search for the users in
 * the database
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
