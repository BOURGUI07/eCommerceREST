/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.transaction.Transactional;
import java.util.List;
import main.dto.CategoryDTO;
import main.dto.ProductDTO;
import main.entity.Category;
import main.entity.Product;
import main.handler.RessourceNotFoundException;
import main.repo.CategoryRepo;
import main.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class ProductService {
    @Autowired
    public ProductService(CategoryRepo categoryRepo, ProductRepo productRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }
    private CategoryRepo categoryRepo;
    private ProductRepo productRepo;
    
    @Transactional
    public CategoryDTO createCategory(CategoryDTO x){
        var category = new Category();
        category.setDesc(x.getDesc());
        category.setName(x.getName());
        for(Integer id:x.getProductsId()){
            category.add(this.productRepo.findById(id).orElseThrow());
        }
        x.setId(category.getId());
        this.categoryRepo.save(category);
        return x;
    }
    
    @Transactional
    public ProductDTO createProduct(ProductDTO x){
        var product = new Product();
        product.setDesc(x.getDesc());
        product.setName(x.getName());
        product.setPrice(x.getPrice());
        product.setStock(x.getStock());
        product.setCategory(this.categoryRepo.findById(x.getCategoryId()).orElseThrow(() -> new RessourceNotFoundException("Category with id:  ProductDTO.getCategoryId() wasn't found")));
        this.productRepo.save(product);
        x.setId(product.getId());
        return x;
    }
    
    public CategoryDTO findCategory(Integer id){
        var category = this.categoryRepo.findById(id).orElseThrow(() -> new RessourceNotFoundException("Category with id: " + id + " wasn't found"));
        var x = new CategoryDTO();
        x.setId(category.getId());
        x.setName(category.getName());
        x.setDesc(category.getDesc());
        for(Product p:category.getProducts()){
            x.getProductsId().add(p.getId());
        }
        return x;
    }
    
    public ProductDTO findProduct(Integer id){
        var p = this.productRepo.findById(id).orElseThrow(() -> new RessourceNotFoundException("Product with id: " + id + " wasn't found"));
        var pd = new ProductDTO();
        pd.setCategoryId(p.getCategory().getId());
        pd.setDesc(p.getDesc());
        pd.setId(p.getId());
        pd.setStock(p.getStock());
        pd.setPrice(p.getPrice());
        return pd;
    }
    
    public List<Product> getAllProducts(){
        return this.productRepo.findAll();
    }
    
    public List<Category> getAllCategories(){
        return this.categoryRepo.findAll();
    }
    
    @Transactional
    public void deleteCategory(Integer id){
        var categ = this.categoryRepo.findById(id).orElseThrow(() -> new RessourceNotFoundException("Category with id: " + id + " wasn't found"));
        for(Product p:categ.getProducts()){
            p.setCategory(null);
        }
        this.categoryRepo.delete(categ);      
    }
    
    @Transactional
    public void deleteProduct(Integer id){
        this.productRepo.deleteById(id);
    }
    
    @Transactional
    public ProductDTO updateProduct(Integer id, ProductDTO x){
        var p = this.productRepo.findById(id).orElseThrow(() -> new RessourceNotFoundException("Product with id: " + id + " wasn't found"));
        p.setCategory(this.categoryRepo.findById(x.getCategoryId()).orElseThrow(() -> new RessourceNotFoundException("Category with id: ProductDTO.getCategoryId() wasn't found")));
        p.setDesc(x.getDesc());
        p.setName(x.getName());
        p.setPrice(x.getPrice());
        p.setStock(x.getStock());
        this.productRepo.save(p);
        return x;
    }
    
    @Transactional
    public CategoryDTO updateCategory(Integer id, CategoryDTO x){
        var categ = this.categoryRepo.findById(id).orElseThrow(() -> new RessourceNotFoundException("Category with id: " + id + " wasn't found"));
        categ.setDesc(x.getDesc());
        categ.setName(x.getName());
        for(Integer a:x.getProductsId()){
            categ.add(this.productRepo.findById(a).orElseThrow());
        }
        this.categoryRepo.save(categ);
        return x;
    }
}
