package com.tBaronDar.springSecurity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Integer id;
    private String username;
    private String password;
}
