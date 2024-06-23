/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import java.util.List;
import main.dto.ProfileDTO;
import main.dto.UserDTO;
import main.entity.Profile;
import main.entity.User;
import main.handler.RessourceNotFoundException;
import main.repo.ProfileRepo;
import main.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service

public class UserService {
   private UserRepo userRepo;
   private ProfileRepo profileRepo;
   
   @Autowired
   public UserService(UserRepo u, ProfileRepo p){
       this.profileRepo=p;
       this.userRepo=u;
   }
   
   @Transactional
   public UserDTO createUser(UserDTO x){
       var user = new User();
       user.setName(x.getName());
       
       if(x.getProfileId()!=null){
           var profile = this.profileRepo.findById(x.getProfileId()).orElseThrow(() -> new RessourceNotFoundException("Profile with id: UserDTO.getProfileId() wasn't found"));
           user.setProfile(profile);
       }
       x.setId(user.getId());
       this.userRepo.save(user);
       return x;
   }
   
   @Transactional
   public ProfileDTO createProfile(ProfileDTO x){
       var profile = new Profile();
       profile.setAddress(x.getAddress());
       profile.setEmail(x.getEmail());
       profile.setPhone(x.getPhone());
       this.profileRepo.save(profile);
       x.setId(profile.getId());
       return x;
   }
   
   public UserDTO findUserById(Integer id){
       var user = this.userRepo.findById(id).orElseThrow(() -> new RessourceNotFoundException("User with id: " + id + " wasn't found"));
       var x = new UserDTO();
       x.setId(user.getId());
       x.setName(user.getName());
       if(user.getProfile()!=null){
           x.setProfileId(user.getProfile().getId());
       }
       return x;
   }
   
   public ProfileDTO findProfileById(Integer id){
       var profile = this.profileRepo.findById(id).orElseThrow(() -> new RessourceNotFoundException("Profile with id: " + id + " wasn't found"));
       var x = new ProfileDTO();
       x.setId(profile.getId());
       x.setAddress(profile.getAddress());
       x.setEmail(profile.getEmail());
       x.setPhone(profile.getPhone());
       return x;
   }
   
   @Transactional
   public UserDTO updateUser(Integer id, UserDTO x){
       var user = this.userRepo.findById(id).orElseThrow(() -> new RessourceNotFoundException("User with id: " + id + " wasn't found"));
       user.setName(x.getName());
       user.setProfile(this.profileRepo.findById(x.getProfileId()).orElseThrow(() -> new RessourceNotFoundException("Profile with id: UserDTO.getProfileId() wasn't found")));
       this.userRepo.save(user);
       return x;
   }
   
   
   
   @Transactional
   public void deleteUser(Integer id){
       this.userRepo.deleteById(id);
   }
   
   public List<User> getAllUsers(){
       return this.userRepo.findAll();
   }
   
   public List<Profile> getAllProfiles(){
       return this.profileRepo.findAll();
   }
}
