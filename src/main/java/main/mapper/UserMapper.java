/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.mapper;

import main.dto.UserDTO;
import main.entity.User;
import main.repo.OrderRepo;
import main.repo.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class UserMapper {
    @Autowired
    public UserMapper(ProfileRepo profileRepo, OrderRepo orderRep) {
        this.profileRepo = profileRepo;
        this.orderRep = orderRep;
    }
    
    private ProfileRepo profileRepo;
    private OrderRepo orderRep;
    
    public UserDTO toDTO(User user){
        var x = new UserDTO();
        x.setId(user.getId());
        x.setName(user.getName());
        x.setProfileId(user.getProfile().getId());
        for(var o:user.getOrders()){
            x.addOrderID(o.getId());
        }
        return x;
    }
    
    public User toUser(UserDTO x){
        var user = new User();
        user.setName(x.getName());
        user.setProfile(this.profileRepo.findById(x.getProfileId()).orElse(null));
        user.setOrders(this.orderRep.findAllById(x.getOrdersId()));
        return user;
    }
}
