/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;
import main.dto.OrderDetailsDTO;
import main.entity.Order;
import main.entity.OrderDetails;
import main.entity.OrderDetailsId;
import main.handler.RessourceNotFoundException;
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
    @PersistenceContext
    private EntityManager em;
    
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
        return this.orderRepo.findById(id).orElseThrow(() -> new RessourceNotFoundException("Order with id: " + id + " wasn't found"));
    }
    
    public List<Order> getAllOrders(){
        return this.orderRepo.findAll();
    }
    
    public List<OrderDetails> getAllOrderDetails(){
        return this.detailRepo.findAll();
    }
    
    public OrderDetails getDetail(Integer orderID, Integer productID){
        return this.detailRepo.findById(new OrderDetailsId(orderID,productID)).orElseThrow(() -> new RessourceNotFoundException("Detail with ids: " + productID + ", " + orderID + " wasn't found"));
    }
    
    @Transactional
    public Order createOrder(Integer userID, OrderDetailsDTO x){
        var order = new Order();
        var user = this.userRepo.findById(userID).orElseThrow(() -> new RessourceNotFoundException("User with id: " + userID + " wasn't found"));
        user.add(order);
        var detail = new OrderDetails();
        detail.setOrder(order);
        var product = this.productRepo.findById(x.getProductId()).orElseThrow(() -> new RessourceNotFoundException("Product with id: OrderDetailsDTO.getProductId() wasn't found"));
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
        var order = this.orderRepo.findById(orderID).orElseThrow(() -> new RessourceNotFoundException("Order with id: " + orderID + " wasn't found"));
        for(var d:order.getOrderdetails()){
            order.removeOrderDetail(d);          
            this.detailRepo.delete(d);
        }
        var user = order.getUser();
        user.removeOrder(order);
        this.orderRepo.delete(order);
    }
    
    //Find users who have placed orders.
    public List<Object[]> usersWhoPlacedOrders(){
        String query = "SELECT DISTINCT u.user_id, u.user_name, o.order_id FROM user u JOIN orders o ON(u.user_id=o.user_id)";
        return this.em.createNativeQuery(query).getResultList();
    }
    
    //Count how many orders each user has placed.
    public List<Object[]> countOrdersForUsers(){
        String query = "SELECT user_id, order_id, COUNT(order_id) num_of_orders FROM orders GROUP BY user_id";
        return this.em.createNativeQuery(query).getResultList();
    }
    
    //Find orders that contain a specific product
    public List<Integer> ordersThatContainsProduct(String productName){
        var query = "SELECT d.order_id FROM product p JOIN orders_items d ON(d.product_id=p.product_id) WHERE p.product_name= :name";
        return this.em.createNativeQuery(query, Integer.class).setParameter("name", productName).getResultList();
    }
    
    //Identify the top-selling products (by quantity).
    public List<Object[]> topSellingProductsByQuantity(){
        var query = "SELECT p.product_name, SUM(d.quantity) sum_quantity FROM product p JOIN orders_items d ON(p.product_id=d.product_id) GROUP BY p.product_name ORDER BY sum_quantity DESC LIMIT 10";
        return this.em.createNativeQuery(query).getResultList();
    }
}
