package com.example.letsplay.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document("User")
public class User {
    
    @Id
    private String id;

    @NotNull(message = "the name is required")
    @Size(min = 3, message = "Le nom de passe doit contenir au moins 3 caractères")
    private String name;

    @NotNull(message = "the email is required")
    @Email(groups = LoginInfo.class  ,message = "Veuillez entrer une adresse email valide")
    private String email;

    @NotNull(groups = {LoginInfo.class , PasswordInfo.class}, message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
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

    public interface LoginInfo {}
    public interface PasswordInfo {}

}
