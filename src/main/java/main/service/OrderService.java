/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import java.util.List;
import main.dto.OrderDetailsDTO;
import main.entity.Order;
import main.entity.OrderDetails;
import main.entity.OrderDetailsId;
import main.repo.OrderDetailRepo;
import main.repo.OrderRepo;
import main.repo.ProductRepo;
import main.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class OrderService {
    
    @Autowired
    public OrderService(OrderRepo orderRepo, ProductRepo productRepo, UserRepo userRepo, OrderDetailRepo detailRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.detailRepo= detailRepo;
    }
    private OrderRepo orderRepo;
    private ProductRepo productRepo;
    private UserRepo userRepo;
    private OrderDetailRepo detailRepo;
    
    public Order findOrderById(Integer id){
        return this.orderRepo.findById(id).orElseThrow();
    }
    
    public List<Order> getAllOrders(){
        return this.orderRepo.findAll();
    }
    
    public List<OrderDetails> getAllOrderDetails(){
        return this.detailRepo.findAll();
    }
    
    public OrderDetails getDetail(Integer orderID, Integer productID){
        return this.detailRepo.findById(new OrderDetailsId(orderID,productID)).orElseThrow();
    }
    
    @Transactional
    public Order createOrder(Integer userID, OrderDetailsDTO x){
        var order = new Order();
        var user = this.userRepo.findById(userID).orElseThrow();
        user.add(order);
        var detail = new OrderDetails();
        detail.setOrder(order);
        var product = this.productRepo.findById(x.getProductId()).orElseThrow();
        detail.setProduct(product);
        detail.setId(new OrderDetailsId(order.getId(), product.getId()));
        product.addOrderDetail(detail);
        detail.setQuantity(x.getQuantity());
        order.addOrderDetail(detail);
        this.detailRepo.save(detail);
        return this.orderRepo.save(order);
    }
    
    
    
    @Transactional
    public void removeOrder(Integer orderID){
        var order = this.orderRepo.findById(orderID).orElseThrow();
        for(var d:order.getOrderdetails()){
            order.removeOrderDetail(d);          
            this.detailRepo.delete(d);
        }
        var user = order.getUser();
        user.removeOrder(order);
        this.orderRepo.delete(order);
    }
}
