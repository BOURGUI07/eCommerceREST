/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import java.util.List;
import main.dto.OrderDTO;
import main.dto.OrderDetailsDTO;
import main.entity.Order;
import main.entity.OrderDetails;
import main.handler.RessourceNotFoundException;
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
    
    
    @PostMapping("/orders/create")
    public OrderDTO createOrder(@RequestBody OrderDTO x){   
        return this.service.createOrder(x);
    }
    
    @PostMapping("/details/create")
    public OrderDetailsDTO createDetail(@RequestBody OrderDetailsDTO x){
        return this.service.createDetail(x);
    }
    
    @GetMapping("/orders/{x}")
    public OrderDTO getOrder(@PathVariable Integer x){
        if(x<0){
            throw new RessourceNotFoundException("Ressource not found");
        }
        return this.service.findOrderById(x);
    }
    
    @GetMapping("/details/{orderID}/{productID}")
    public OrderDetailsDTO getOrderDetail(@PathVariable Integer orderID, @PathVariable Integer productID){
        if(orderID<0 || productID<0){
            throw new RessourceNotFoundException("Ressource not found");
        }
        return this.service.getDetail(orderID, productID);
    }
    
    @GetMapping("/orders")
    public List<OrderDTO> getOrders(){
        return this.service.getAllOrders();
    }
    
    @GetMapping("/details")
    public List<OrderDetailsDTO> getOrderDetails(){
        return this.service.getAllOrderDetails();
    }
    
    @DeleteMapping("/orders/delete/{x}")
    public void deleteOrder(@PathVariable Integer x){
        if(x<0){
            throw new RessourceNotFoundException("Ressource not found");
        }
        this.service.removeOrder(x);
    }
    
    
}
