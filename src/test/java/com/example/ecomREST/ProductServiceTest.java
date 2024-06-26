/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.example.ecomREST;

import java.util.Arrays;
import java.util.Optional;
import main.dto.CategoryDTO;
import main.dto.ProductDTO;
import main.entity.Category;
import main.entity.Product;
import main.mapper.CategoryMapper;
import main.mapper.ProductMapper;
import main.repo.CategoryRepo;
import main.repo.ProductRepo;
import main.service.ProductService;
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
public class ProductServiceTest {
    @Mock
    private ProductMapper productMapper;
    @Mock
    private CategoryMapper categMapper;
    @Mock
    private ProductRepo productRepo;
    @Mock
    private CategoryRepo categRepo;
    
    @InjectMocks
    private ProductService service;
    
    private Category c;
    private Product p;
    private CategoryDTO cx;
    private CategoryDTO cx2;
    private ProductDTO px;
    
    public ProductServiceTest() {
        c = new Category();
        c.setName("clothes");
        c.setDesc("desc");
        c.setId(1);
        
        cx = new CategoryDTO();
        cx.setName("clothes");
        cx.setDesc("desc");
        cx.setId(1);
        
        cx2 = new CategoryDTO();
        cx2.setName("clothess");
        cx2.setDesc("desc2");
        cx2.setId(1);
        
        p = new Product();
        p.setCategory(c);
        p.setName("name");
        p.setDesc("desc");
        p.setPrice(5.2);
        p.setStock(50);
        
        px = new ProductDTO();
        px.setCategoryId(1);
        px.setName("name");
        px.setDesc("desc");
        px.setPrice(5.2);
        px.setStock(50);
    }
    
    @BeforeAll
    public static void setUpClass() {
        
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testCreateCategory(){
        when(categMapper.toCategory(cx)).thenReturn(c);
        when(categRepo.save(c)).thenReturn(c);
        when(categMapper.toDTO(c)).thenReturn(cx);
        var result = service.createCategory(cx);
        assertEquals(cx, result);
        verify(categMapper, times(1)).toCategory(cx);
        verify(categRepo, times(1)).save(c);
        verify(categMapper, times(1)).toDTO(c);
    }
    
    @Test
    public void testFindCategory(){
        when(categRepo.findById(1)).thenReturn(Optional.of(c));
        when(categMapper.toDTO(c)).thenReturn(cx);
        
        var result = service.findCategory(1);
        assertEquals(cx, result);
        verify(categRepo, times(1)).findById(1);
        verify(categMapper, times(1)).toDTO(c);
    }
    
    @Test
    public void testUpdateCategory(){
        when(categRepo.findById(1)).thenReturn(Optional.of(c));
        when(categRepo.save(c)).thenReturn(c);
        when(categMapper.toDTO(c)).thenReturn(cx2);
        
        var result = service.updateCategory(1, cx2);
        assertEquals(cx2, result);
        verify(categRepo, times(1)).save(c);
    }
    
    @Test 
    public void testGetAllCategs(){
        when(categRepo.findAll()).thenReturn(Arrays.asList(c));
        when(categMapper.toDTO(c)).thenReturn(cx2);
        var s = service.getAllCategories().size();
        assertEquals(1,s);
    }
    
    @Test
    public void testDeleteCateg(){
        when(categRepo.findById(1)).thenReturn(Optional.of(c));
        doNothing().when(categRepo).delete(c);
        service.deleteCategory(1);
        verify(categRepo, times(1)).delete(c);
    }
    
    @Test
    public void testCreateProduct(){
        when(productMapper.toProduct(px)).thenReturn(p);
        when(productRepo.save(p)).thenReturn(p);
        when(productMapper.toDTO(p)).thenReturn(px);
        
        var r = service.createProduct(px);
        assertEquals(px, r);
        verify(productRepo, times(1)).save(p);
    }
    
    @Test
    public void testFindAllProducts(){
        when(productRepo.findAll()).thenReturn(Arrays.asList(p));
        when(productMapper.toDTO(p)).thenReturn(px);
        
        var s = service.getAllProducts().size();
        
        assertEquals(1,s);
    }
    
    @Test
    public void testFindProduct(){
        when(productRepo.findById(1)).thenReturn(Optional.of(p));
        when(productMapper.toDTO(p)).thenReturn(px);
        
        var r= service.findProduct(1);
        assertEquals(px, r);
    }
    
    @Test
    public void testUpdateProduct(){
        when(productRepo.findById(1)).thenReturn(Optional.of(p));
        when(productRepo.save(p)).thenReturn(p);
        when(productMapper.toDTO(p)).thenReturn(px);
        
        var r = service.updateProduct(1, px);
        assertEquals(px, r);
        verify(productRepo, times(1)).save(p);
    }
    
    @Test
    public void deleteProduct(){
        doNothing().when(productRepo).deleteById(1);
        service.deleteProduct(1);
        verify(productRepo, times(1)).deleteById(1);
    }
   
}
