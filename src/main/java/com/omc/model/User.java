package com.omc.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String username;

    private String password;

    @Embedded
    Address address;

    public User(){}

    public User(String name, String username, String password, Address address) {
        setName(name);
        setUsername(username);
        setPassword(password);
        setAddress(address);
    }

}
