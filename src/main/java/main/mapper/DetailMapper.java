/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.mapper;

import main.dto.OrderDetailsDTO;
import main.entity.OrderDetails;
import main.repo.OrderRepo;
import main.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class DetailMapper {
    @Autowired
    public DetailMapper(OrderRepo orderRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }
    private OrderRepo orderRepo;
    private ProductRepo productRepo;
    
    public OrderDetails toDetail(OrderDetailsDTO x){
        var d = new OrderDetails();
        d.setQuantity(x.getQuantity());
        d.setOrder(this.orderRepo.findById(x.getOrderId()).orElse(null));
        d.setProduct(this.productRepo.findById(x.getProductId()).orElse(null));
        return d;
    }
    
    public OrderDetailsDTO toDTO(OrderDetails o){
        var x= new OrderDetailsDTO();
        x.setId(o.getId());
        x.setOrderId(o.getOrder().getId());
        x.setProductId(o.getProduct().getId());
        x.setQuantity(o.getQuantity());
        return x;
    }
}
