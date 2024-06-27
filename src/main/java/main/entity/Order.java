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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import main.baseEntities.BaseEntity;
/**
 *
 * @author hp
 */
@Entity
@Table(name="orders")
public class Order extends BaseEntity{

    public List<OrderDetails> getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(List<OrderDetails> orderdetails) {
        this.orderdetails = orderdetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Order() {
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="order_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    
    
    @OneToMany(mappedBy="order")
    private List<OrderDetails> orderdetails;
    
    public void addOrderDetail(OrderDetails o){
        if(this.orderdetails==null){
            orderdetails = new ArrayList<>();
        }
        this.orderdetails.add(o);
        o.setOrder(this);
    }
    
    public void removeOrderDetail(OrderDetails o){
        this.orderdetails.remove(o);
    }
}
