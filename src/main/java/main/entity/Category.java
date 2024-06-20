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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */

@Entity
@Table(name="category")
public class Category {

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public Category(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public Category() {
    }
    @OneToMany(mappedBy="category", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Product> products;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="category_id")
    private Integer id;
    
    @Column(name="category_name")
    private String name;
    
    @Column(name="category_desc")
    private String desc;
    
    
    public void add(Product product){
        if(products==null){
            products=new ArrayList<>();
        }
        products.add(product);
        product.setCategory(this);
    }
}
