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

/**
 *
 * @author hp
 */
@Entity
@Table(name="product")
public class Product {

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Product(String name, String desc, double price, int stock) {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.stock = stock;
    }

    public Product() {
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="product_id")
    private Integer id;
    
    @Column(name="product_name")
    private String name;
    
    @Column(name="product_desc")
    private String desc;
    
    @Column(name="product_price")
    private double price;
    
    @Column(name="product_stock")
    private int stock;
    
    
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    
    @OneToMany(mappedBy="product")
    private List<OrderDetails> orderDetails;
    
    public void addOrderDetail(OrderDetails o){
        if(this.orderDetails==null){
            orderDetails = new ArrayList<>();
        }
        this.orderDetails.add(o);
        o.setProduct(this);
    }
    
    public void removeOrderDetail(OrderDetails o){
        this.orderDetails.remove(o);
    }
}
