/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import main.baseEntities.BaseEntity;

/**
 *
 * @author hp
 */
@Entity
@Table(name="profile")
public class Profile extends BaseEntity{

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Profile(String address, String email, String phone) {
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public Profile() {
    }
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="profile_id")
    private Integer id;
    
    @Column(name="address")
    private String address;
    
    @Column(name="email")
    private String email;
    
    @Column(name="phone")
    private String phone;
    
}
