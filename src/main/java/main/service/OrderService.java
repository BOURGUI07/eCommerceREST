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
    
    //Find users who have not placed any orders:
    public List<Object[]> usersWhoHaven_tPlacedAnyOrders(){
        String query = "SELECT u.user_id, u.user_name FROM user u LEFT JOIN orders o ON (u.user_id=o.user_id) WHERE o.order_id IS NULL";
        return this.em.createNativeQuery(query).getResultList();
    }
    
    //List all products with their respective category names and the number of times each product has been ordered:
    public List<Object[]> numberOfTimesEachProductHasBeenOrdered(){
        String query = "SELECT p.product_id, p.product_name, c.category_name, COALESCE(SUM(d.quantity),0) num_of_times FROM product p JOIN category c ON (p.category_id=c.category_id) LEFT JOIN orders_items d ON (p.product_id=d.product_id) GROUP BY p.product_id, p.product_name, c.category_name ORDER BY num_of_times DESC";
        return this.em.createNativeQuery(query).getResultList();
    }
    
    //Find the most popular product (the product ordered the most times).
    public List<Object[]> mostPopularProduct(){
        var query = "SELECT p.product_id, p.product_name, SUM(d.quantity) FROM product p JOIN orders_items d ON(p.product_id=d.product_id) GROUP BY p.product_id, p.product_name ORDER BY SUM(d.quantity) DESC LIMIT 1";
        return this.em.createNativeQuery(query).getResultList();
    }
    
    //Get a list of users along with the total number of products they have ordered, including users who haven't placed any orders.
    public List<Object[]> usersWithTotalOrderedProducts(){
        var query = "SELECT u.user_id, u.user_name, COALESCE(SUM(d.quanity),0) total FROM users u LEFT JOIN orders o ON(u.user_id=o.user_id) LEFT JOIN orders_items d ON (o.order_id=d.order_id) GROUP BY u.user_id, u.user_name ORDER BY total DESC";
        return this.em.createNativeQuery(query).getResultList();
    }
    
    //Get the average number of products per order.
    public List<Object[]> avgProductsPerOrder(){
        var q = "SELECT order_id, AVG(quantity) avg FROM orders_items GROUP BY order_id ORDER BY avg DESC";
        return this.em.createNativeQuery(q).getResultList();
    }
    
    //Find the category with the highest total sales (sum of price * quantity for all products in that category).
    public List<Object[]> categoryWithHighestSales(){
        var q = "SELECT c.category_id, c.category_name, SUM(p.product_price*d.quantity) total_sales FROM product p JOIN category c JOIN orders_items d ON (p.product_id=d.product_id AND c.category_id=p.category_id) GROUP BY c.category_id, c.category_name ORDER BY total_sales DESC LIMIT 1";
        return this.em.createNativeQuery(q).getResultList();
    }
    
    //Retrieve the list of users along with their profile information and the total number of orders they have placed.
    public List<Object[]> usersWithNumOrders(){
        var q = "SELECT u.user_id, u.user_name, p.address, p.email, p.phone, COUNT(o.order_id) num_orders FROM user u JOIN profile p JOIN orders o ON(u.profile_id=p.profile_id AND u.user_id=o.user_id) GROUP BY u.user_id, u.user_name, p.address, p.email, p.phone ORDER BY num_orders DESC";
        return this.em.createNativeQuery(q).getResultList();
    }
}
