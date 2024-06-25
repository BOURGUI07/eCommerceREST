/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;

import java.util.ArrayList;
import java.util.List;
import main.entity.OrderDetailsId;

/**
 *
 * @author hp
 */
public class ProductDTO {

    public List<OrderDetailsId> getOrdersId() {
        return orderDetailIds;
    }

    public void setOrdersId(List<OrderDetailsId> ordersId) {
        this.orderDetailIds = ordersId;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public ProductDTO() {
    }
    private Integer id;
    private String name;
    private String desc;
    private double price;
    private int stock;
    private Integer categoryId;
    private List<OrderDetailsId> orderDetailIds;
    
    public void addorderDetailId(OrderDetailsId id){
        if(this.orderDetailIds==null){
            this.orderDetailIds = new ArrayList<>();
        }
        this.orderDetailIds.add(id);
    }
}
