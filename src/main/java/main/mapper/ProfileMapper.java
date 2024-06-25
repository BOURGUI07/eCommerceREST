/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.mapper;

import main.dto.ProfileDTO;
import main.entity.Profile;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class ProfileMapper {
    public ProfileDTO toDTO(Profile p){
        var x = new ProfileDTO();
        x.setAddress(p.getAddress());
        x.setEmail(p.getEmail());
        x.setPhone(p.getPhone());
        x.setId(p.getId());
        return x;
    }
    
    public Profile toProfile(ProfileDTO x){
        var p = new Profile();
        p.setAddress(x.getAddress());
        p.setEmail(x.getEmail());
        p.setPhone(x.getPhone());
        return p;
    }
}
