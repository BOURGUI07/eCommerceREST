/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import main.dto.ProfileDTO;
import main.dto.UserDTO;
import main.entity.Product;
import main.entity.Profile;
import main.entity.User;
import main.handler.RessourceNotFoundException;
import main.mapper.ProfileMapper;
import main.mapper.UserMapper;
import main.repo.OrderRepo;
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
   private UserMapper userMapper;
   private ProfileMapper profileMapper;
   private OrderRepo orderRepo;
   private UserRepo userRepo;
   private ProfileRepo profileRepo;
   
   @PersistenceContext
   private EntityManager em;
   
   @Autowired
   public UserService(UserRepo u, ProfileRepo p, OrderRepo repo, UserMapper userMapper, ProfileMapper pm){
       this.profileRepo=p;
       this.userRepo=u;
       this.userMapper= userMapper;
       this.orderRepo=repo;
       this.profileMapper=pm;
   }
   
   @Transactional
   public UserDTO createUser(UserDTO x){
       var user = this.userMapper.toUser(x);
       var savedUser = this.userRepo.save(user);
       return this.userMapper.toDTO(savedUser);
   }
   
   @Transactional
   public ProfileDTO createProfile(ProfileDTO x){
       var profile = this.profileMapper.toProfile(x);
       var savedProfile = this.profileRepo.save(profile);
       return this.profileMapper.toDTO(savedProfile);
   }
   
   public UserDTO findUserById(Integer id){
       var user = this.userRepo.findById(id).orElse(null);
       return this.userMapper.toDTO(user);
   }
   
   public ProfileDTO findProfileById(Integer id){
       var p = this.profileRepo.findById(id).orElse(null);
       return this.profileMapper.toDTO(p);
   }
   
   @Transactional
   public UserDTO updateUser(Integer id, UserDTO x){
       var user = this.userRepo.findById(id).orElse(null);
       user.setName(x.getName());
       user.setProfile(this.profileRepo.findById(x.getProfileId()).orElse(null));  
       user.setOrders(this.orderRepo.findAllById(x.getOrdersId()));
       var savedUser = this.userRepo.save(user);
       return this.userMapper.toDTO(savedUser);
   }
   
   @Transactional
   public ProfileDTO updateProfile(Integer id, ProfileDTO x){
       var p = this.profileRepo.findById(id).orElse(null);
       p.setAddress(x.getAddress());
       p.setEmail(x.getEmail());
       p.setPhone(x.getPhone());
       var savedProfile = this.profileRepo.save(p);
       return this.profileMapper.toDTO(savedProfile);
   }
   
   
   
   @Transactional
   public void deleteUser(Integer id){
       this.userRepo.deleteById(id);
   }
   
   public List<UserDTO> getAllUsers(){
       return this.userRepo.findAll().stream().map(x -> this.userMapper.toDTO(x)).collect(Collectors.toList());
   }
   
   public List<ProfileDTO> getAllProfiles(){
       return this.profileRepo.findAll().stream().map(p -> this.profileMapper.toDTO(p)).collect(Collectors.toList());
   }
   
   //Retrieve all users with their profiles.
   public List<Object[]> usersWithTheirProfiles(){
       String query = "SELECT user.user_id, user.user_name, profile.address, profile.email, profile.phone FROM user JOIN profile ON(profile_id=profile_id)";
       return this.em.createNativeQuery(query).getResultList();
   }
   
   
   
   
}
