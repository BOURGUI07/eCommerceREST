/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class UserDTO {

    public List<Integer> getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(List<Integer> ordersId) {
        this.ordersId = ordersId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public UserDTO() {
    }
    private Integer id;
    private String name;
    private Integer profileId;
    private List<Integer> ordersId;
    
    public void addOrderID(Integer orderID){
        if(this.ordersId==null){
            this.ordersId = new ArrayList<>();
        }
        this.ordersId.add(orderID);
    }
}
