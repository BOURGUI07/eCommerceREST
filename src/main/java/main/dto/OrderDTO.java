/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import main.entity.OrderDetailsId;

/**
 *
 * @author hp
 */
public class OrderDTO {
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAT() {
        return createdAT;
    }

    public void setCreatedAT(LocalDateTime createdAT) {
        this.createdAT = createdAT;
    }
    private String createdBy;
    private LocalDateTime createdAT;

    public List<OrderDetailsId> getDetailIDs() {
        return orderDetailIds;
    }

    public void setDetailIDs(List<OrderDetailsId> detailsIDs) {
        this.orderDetailIds = detailsIDs;
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
    private List<OrderDetailsId> orderDetailIds;
    
    public void addOrderDetailID(OrderDetailsId id){
        if(this.orderDetailIds==null){
            this.orderDetailIds=new ArrayList<>();
        }
        this.orderDetailIds.add(id);
    }
}
