package com.tBaronDar.springSecurity.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Step 1a:
 * create an entity and link
 * it with the SQL table
 */
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
}
