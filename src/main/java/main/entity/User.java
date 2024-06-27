/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.entity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import main.baseEntities.BaseEntity;

/**
 *
 * @author hp
 */
@Entity
@Table(name="user")
public class User extends BaseEntity{

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
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


    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public User(String name) {
        this.name = name;
    }

    public User() {
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer id;
    
    @Column(name="user_name")
    private String name;
    
    
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="profile_id")
    private Profile profile;
    
    @OneToMany(mappedBy="user", cascade={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Order> orders;
    
    public void add(Order order){
        if(orders==null){
            orders=new ArrayList<>();
        }
        orders.add(order);
        order.setUser(this);
    }
    
    public void removeOrder(Order o){
        this.orders.remove(o);
    }
}
