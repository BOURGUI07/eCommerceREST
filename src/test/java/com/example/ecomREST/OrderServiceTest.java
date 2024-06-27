/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.ecomREST;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import main.dto.OrderDTO;
import main.dto.OrderDetailsDTO;
import main.entity.Order;
import main.entity.OrderDetails;
import main.entity.OrderDetailsId;
import main.entity.Product;
import main.entity.User;
import main.mapper.DetailMapper;
import main.mapper.OrderMapper;
import main.repo.OrderDetailRepo;
import main.repo.OrderRepo;
import main.repo.ProductRepo;
import main.repo.UserRepo;
import main.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author hp
 */
public class OrderServiceTest {
    @Mock
    private OrderRepo orderRepo;
    @Mock
    private OrderDetailRepo detailRepo;
    @Mock
    private ProductRepo productRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private DetailMapper detailMapper;
    
    @InjectMocks
    private OrderService service;
    
    private Order o;
    private Product p;
    private User u;
    private OrderDTO ox;
    private OrderDetails d;
    private OrderDetailsDTO dx;
    private OrderDetailsId y;
    
    public OrderServiceTest() {
        y = new OrderDetailsId(1,1);
        p = new Product();
        p.setId(1);
        u = new User();
        u.setId(1);
        
        o = new Order();
        o.setId(1);
        o.setUser(u);
        o.setCreatedBy("youness");
        
        
        ox = new OrderDTO();
        ox.setId(1);
        ox.setUserId(1);
        ox.setCreatedBy("youness");
        
        
        d = new OrderDetails();
        d.setId(y);
        d.setOrder(o);
        d.setProduct(p);
        d.setQuantity(40);
        d.setCreatedBy("youness");
        
        
        dx = new OrderDetailsDTO();
        dx.setId(y);
        dx.setOrderId(1);
        dx.setProductId(1);
        dx.setQuantity(40);
        dx.setCreatedBy("youness");
        
        o.addOrderDetail(d);
        ox.addOrderDetailID(y);
        
    }
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testCreateDetail(){
        when(detailMapper.toDetail(dx)).thenReturn(d);
        when(detailRepo.save(d)).thenReturn(d);
        when(detailMapper.toDTO(d)).thenReturn(dx);
        var r = service.createDetail(dx);
        assertEquals(dx, r);
        verify(detailRepo,times(1)).save(d);
    }
    
    @Test
    public void testCreateOrder(){
        when(orderMapper.toOrder(ox)).thenReturn(o);
        when(orderRepo.save(o)).thenReturn(o);
        when(orderMapper.toDTO(o)).thenReturn(ox);
        var r = service.createOrder(ox);
        assertEquals(ox, r);
        verify(orderRepo, times(1)).save(o);
    }
    
    @Test
    public void testFindDetail(){
        when(detailRepo.findById(y)).thenReturn(Optional.of(d));
        when(detailMapper.toDTO(d)).thenReturn(dx);
        var r = service.getDetail(1, 1);
        assertEquals(dx, r);
    }
    
    @Test
    public void testFindOrder(){
        when(orderRepo.findById(1)).thenReturn(Optional.of(o));
        when(orderMapper.toDTO(o)).thenReturn(ox);
        var r = service.findOrderById(1);
        assertEquals(ox,r);
    }
    
    @Test
    public void testFindAllOrders(){
        when(orderRepo.findAll()).thenReturn(Arrays.asList(o));
        when(orderMapper.toDTO(o)).thenReturn(ox);
        assertEquals(1, service.getAllOrders().size());
    }
    
    @Test
    public void testFindAllDetails(){
        when(detailRepo.findAll()).thenReturn(Arrays.asList(d));
        when(detailMapper.toDTO(d)).thenReturn(dx);
        assertEquals(1, service.getAllOrderDetails().size());
    }
    
    @Test
    public void testRemoveOrder(){
        when(orderRepo.findById(1)).thenReturn(Optional.of(o));
        assertNotNull(o.getOrderdetails());
        doNothing().when(orderRepo).delete(o);
        verify(orderRepo,times(1)).delete(o);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
