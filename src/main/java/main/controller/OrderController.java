/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import java.util.List;
import main.dto.OrderDetailsDTO;
import main.entity.Order;
import main.entity.OrderDetails;
import main.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }
    private OrderService service;
    
    
    @PostMapping("/orders/create/{userID}")
    public Order createOrder(@PathVariable Integer userID, @RequestBody OrderDetailsDTO x){
        return this.service.createOrder(userID, x);
    }
    
    @GetMapping("/orders/{x}")
    public Order getOrder(@PathVariable Integer x){
        return this.service.findOrderById(x);
    }
    
    @GetMapping("/details/{orderID}/{productID}")
    public OrderDetails getOrderDetail(@PathVariable Integer orderID, @PathVariable Integer productID){
        return this.service.getDetail(orderID, productID);
    }
    
    @GetMapping("/orders")
    public List<Order> getOrders(){
        return this.service.getAllOrders();
    }
    
    @GetMapping("/details")
    public List<OrderDetails> getOrderDetails(){
        return this.service.getAllOrderDetails();
    }
    
    @DeleteMapping("/orders/delete/{x}")
    public void deleteOrder(@PathVariable Integer x){
        this.service.removeOrder(x);
    }
    
    
}
