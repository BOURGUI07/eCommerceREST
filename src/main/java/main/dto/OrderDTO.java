/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;

import java.util.List;

/**
 *
 * @author hp
 */
public class OrderDTO {

    public List<Integer> getProductsId() {
        return orderDetailIds;
    }

    public void setProductsId(List<Integer> productsId) {
        this.orderDetailIds = productsId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public OrderDTO() {
    }
    private Integer id;
    private Integer userId;
    private List<Integer> orderDetailIds;
    
    
}
