/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.mapper;

import main.dto.OrderDTO;
import main.entity.Order;
import main.repo.OrderDetailRepo;
import main.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class OrderMapper {
    @Autowired
    public OrderMapper(UserRepo userRepo, OrderDetailRepo detailRepo) {
        this.userRepo = userRepo;
        this.detailRepo = detailRepo;
    }
    private UserRepo userRepo;
    private OrderDetailRepo detailRepo;
    
    
    public Order toOrder(OrderDTO x){
        var o = new Order();
        o.setUser(this.userRepo.findById(x.getUserId()).orElse(null));
        o.setOrderdetails(this.detailRepo.findAllById(x.getDetailIDs()));
        return o;
    }
    
    public OrderDTO toDTO(Order o){
        var x = new OrderDTO();
        x.setId(o.getId());
        x.setUserId(o.getUser().getId());
        for(var detail:o.getOrderdetails()){
            x.addOrderDetailID(detail.getId());
        }
        return x;
    }
}
