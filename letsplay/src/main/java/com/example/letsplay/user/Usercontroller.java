package com.example.letsplay.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@RestController
public class Usercontroller {


    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  Userservice userservice;
    

    @GetMapping("/users")
    public List<User> getMethodName() {
        return userservice.getUsers();
    }
    

    @PostMapping("/login")
    public ResponseEntity<String> login(@Validated(User.LoginInfo.class) @RequestBody User user ,  HttpSession session) {

        Optional<User>  us =  userservice.getuser(user.getEmail());
        
        if (us.isPresent()) {
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
    public ResponseEntity<String> register(@Valid @RequestBody User user) {


        if (userservice.getuser(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("email already exists");
        }
       
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            userservice.insert(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body("ok");
    }


    @PutMapping("/changepassword")
    public ResponseEntity<String> updatePassword(HttpSession session ,@Validated(User.PasswordInfo.class) @RequestBody User newuser) {

        Object userId =  session.getAttribute("userid");
        if (userId == null) {
            return ResponseEntity.status(403).body("access denied");
        } 
        
        Optional<User> userObj = userservice.getuserById(userId.toString());
       
        if (userObj.isPresent()) {
            User user = userObj.get();
            newuser.setPassword(passwordEncoder.encode(newuser.getPassword()));
            try {
                userservice.upate(user , newuser);
                return ResponseEntity.ok().body("password changed");
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().body("bad request");
    }
    
    @PreAuthorize("@userservice.Connected(#session)")
    @DeleteMapping("/deletemyaccount")
    public ResponseEntity<String> deleteUser(@RequestBody String entity , HttpSession session) {
        Object userId =  session.getAttribute("userid");
        Optional<User> userObj = userservice.getuserById(userId.toString());

        if (userObj.isPresent()) {
            User user = userObj.get();
            try {
                userservice.delete(user);
                session.invalidate();
                return ResponseEntity.ok().body("account deleted");
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().body("bad request");
    }
    
}
