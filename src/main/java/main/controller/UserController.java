/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import java.util.List;
import main.dto.ProfileDTO;
import main.dto.UserDTO;
import main.entity.Profile;
import main.entity.User;
import main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }
    private UserService service;
    
    @PostMapping("/users/create")
    public UserDTO createUser(@RequestBody UserDTO x){
        return this.service.createUser(x);
    }
    
    @PostMapping("/profiles/create")
    public ProfileDTO createProfile(@RequestBody ProfileDTO x){
        return this.service.createProfile(x);
    }
    
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return this.service.getAllUsers();
    }
    
    @GetMapping("/profiles")
    public List<Profile> getAllProfiles(){
        return this.service.getAllProfiles();
    }
    
    @GetMapping("/users/{userID}")
    public UserDTO getUser(@PathVariable Integer userID){
        return this.service.findUserById(userID);
    }
    
    @GetMapping("/profiles/{profileID}")
    public ProfileDTO getProfile(@PathVariable Integer profileID){
        return this.service.findProfileById(profileID);
    }
    
    @PutMapping("/users/update/{userID}")
    public UserDTO updateUser(@PathVariable Integer userID, @RequestBody UserDTO x){
        return this.service.updateUser(userID, x);
    }
    
    @DeleteMapping("/users/delete/{x}")
    public void deleteUser(@PathVariable Integer x){
        this.service.deleteUser(x);
    }
    
    
}
