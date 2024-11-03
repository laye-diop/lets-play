package com.example.letsplay.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import jakarta.servlet.http.HttpSession;


@RestController
public class Usercontroller {


    private final PasswordEncoder passwordEncoder;
    private final Userservice userservice;
    
    @Autowired
    public Usercontroller(Userservice userservice , PasswordEncoder passwordEncoder) {
        this.userservice = userservice;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user ,  HttpSession session) {

        Optional<User>  us =  userservice.getuser(user.getEmail());
        
        if (us.isPresent() && user.Isvalid()) {
            User newuser  = us.get();
            if (passwordEncoder.matches(user.getPassword(), newuser.getPassword())) {

                session.setAttribute("userid", newuser.getId());
                session.setAttribute("role", newuser.getRole());

                return ResponseEntity.ok().body("succesfuly connected");
            }
        }
        return ResponseEntity.badRequest().body("incorrect informations");
    }
    


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {


        if (!user.Isvalid() || userservice.getuser(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("bad reqest");
        }
       
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userservice.insert(user);
        return ResponseEntity.ok().body("ok");
    }


    @PutMapping("/changepassword")
    public ResponseEntity<String> updatePassword(HttpSession session , @RequestBody User newuser) {

        Object userId =  session.getAttribute("userid");
        if (!newuser.Isvalidpassword() || userId == null) {
            return ResponseEntity.badRequest().body("bad request");
        }
        
        Optional<User> userObj = userservice.getuserById(userId.toString());
       
        if (userObj.isPresent()) {
            User user = userObj.get();
            newuser.setPassword(passwordEncoder.encode(newuser.getPassword()));
            userservice.upate(user , newuser);
            return ResponseEntity.ok().body("password changed");
        }
        return ResponseEntity.badRequest().body("bad request");
    }

    @DeleteMapping("/deletemyaccount")
    public ResponseEntity<String> deleteUser(@RequestBody String entity , HttpSession session) {
        Object userId =  session.getAttribute("userid");
        Optional<User> userObj = userservice.getuserById(userId.toString());

        if (userObj.isPresent()) {
            User user = userObj.get();
            userservice.delete(user);
            session.invalidate();
            return ResponseEntity.ok().body("account deleted");
        }

        return ResponseEntity.badRequest().body("bad request");
    }
    
}
