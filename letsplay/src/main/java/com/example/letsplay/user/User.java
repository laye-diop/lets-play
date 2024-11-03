package com.example.letsplay.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("User")
public class User {
    
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private Role role;

    public User() {}

    public User(String id, String name, String email, Role role ,  String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public User(String name, String email, Role role ,  String password) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public User(String string, Object object, String string2) {
        //TODO Auto-generated constructor stub
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public boolean Isvalid() {
        return  this.name != null  && this.email != null && this.password != null;
    }

    public boolean Isvalidpassword() {
        return  this.password != null && this.password.length() > 5;
    }

}
