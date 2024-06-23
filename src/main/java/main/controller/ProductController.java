/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.controller;

import java.util.List;
import main.dto.CategoryDTO;
import main.dto.ProductDTO;
import main.entity.Category;
import main.entity.Product;
import main.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author hp
 */
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }
    private ProductService service;
    
    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return this.service.getAllCategories();
    }
    
    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return this.service.getAllProducts();
    }
    
    @GetMapping("/categories/{x}")
    public CategoryDTO getCategory(@PathVariable Integer x){
        return this.service.findCategory(x);
    }
    
    @GetMapping("/products/{x}")
    public ProductDTO getProduct(@PathVariable Integer x){
        return this.service.findProduct(x);
    }
    
    
    @PostMapping("/categories/create")
    public CategoryDTO createCateg(@RequestBody CategoryDTO x){
        return this.service.createCategory(x);
    }
    
    @PostMapping("/products/create")
    public ProductDTO createProduct(@RequestBody ProductDTO x){
        return this.service.createProduct(x);
    }
    @PutMapping("/categories/update/{x}")
    public CategoryDTO updateCateg(@PathVariable Integer x, @RequestBody CategoryDTO y){
        return this.service.updateCategory(x, y);
    }
    
    @PutMapping("/products/update/{x}")
    public ProductDTO updateProduct(@PathVariable Integer x, @RequestBody ProductDTO y){
        return this.service.updateProduct(x, y);
    }
    
    @DeleteMapping("/categories/delete/{x}")
    public void deleteCateg(@PathVariable Integer x){
        this.service.deleteCategory(x);
    }
    
    @DeleteMapping("/products/delete/{x}")
    public void deleteProduct(@PathVariable Integer x){
        this.service.deleteProduct(x);
    }
    
}
