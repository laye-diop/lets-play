package com.example.letsplay.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
public class Userservice {

    @Autowired
    private   UserRepository repository;


    public List<User> getAll() {
        return repository.findAll();
    }
    public void insert(User u) {
        repository.insert(u);
    }
    public Optional<User> getuser(String email) {
        return repository.findByEmail(email);
    }
    public Optional<User> getuserById(String id) {
        return repository.findById(id);
    }

    public void upate(User user, User newuser) {
        user.setPassword(newuser.getPassword());
        repository.save(user);
    }

    public void delete(User user) {
       repository.delete(user);
    }
    public List<User> getUsers() {
        List<User> users = repository.findAll();

        users.forEach((u) -> {
            u.setPassword("");
        });

        return users;
    }
    public boolean Connected(HttpSession session)  {
        return  session.getAttribute("userid") != null;
    }
   
}
