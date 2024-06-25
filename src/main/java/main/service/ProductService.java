/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import main.dto.CategoryDTO;
import main.dto.ProductDTO;
import main.entity.Category;
import main.entity.Product;
import main.handler.RessourceNotFoundException;
import main.mapper.CategoryMapper;
import main.mapper.ProductMapper;
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
    @PersistenceContext
    private EntityManager em;
    
 
    
    @Autowired
    public ProductService(CategoryRepo categoryRepo, ProductRepo productRepo, ProductMapper pm, CategoryMapper cm) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
        this.categMapper=cm;
        this.productMapper=pm;
    }
    private CategoryRepo categoryRepo;
    private ProductRepo productRepo;
    private ProductMapper productMapper;
    private CategoryMapper categMapper;
    
    @Transactional
    public CategoryDTO createCategory(CategoryDTO x){
        var c = this.categMapper.toCategory(x);
        var savedCategory = this.categoryRepo.save(c);
        return this.categMapper.toDTO(savedCategory);
    }
    
    @Transactional
    public ProductDTO createProduct(ProductDTO x){
        var p = this.productMapper.toProduct(x);
        var savedProduct = this.productRepo.save(p);
        return this.productMapper.toDTO(savedProduct);
        
    }
    
    public CategoryDTO findCategory(Integer id){
        var c = this.categoryRepo.findById(id).orElse(null);
        return this.categMapper.toDTO(c);
    }
    
    public ProductDTO findProduct(Integer id){
        var p = this.productRepo.findById(id).orElse(null);
        return this.productMapper.toDTO(p);
    }
    
    public List<ProductDTO> getAllProducts(){
        return this.productRepo.findAll().stream().map(x -> this.productMapper.toDTO(x)).collect(Collectors.toList());
    }
    
    public List<CategoryDTO> getAllCategories(){
        return this.categoryRepo.findAll().stream().map(x -> this.categMapper.toDTO(x)).collect(Collectors.toList());
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
        var p = this.productRepo.findById(id).orElse(null);
        p.setCategory(this.categoryRepo.findById(x.getCategoryId()).orElse(null));
        p.setDesc(x.getDesc());
        p.setName(x.getName());
        p.setPrice(x.getPrice());
        p.setStock(x.getStock());
        var savedProduct = this.productRepo.save(p);
        return this.productMapper.toDTO(savedProduct);
    }
    
    @Transactional
    public CategoryDTO updateCategory(Integer id, CategoryDTO x){
        var categ = this.categoryRepo.findById(id).orElse(null);
        categ.setDesc(x.getDesc());
        categ.setName(x.getName());
        for(Integer a:x.getProductsId()){
            categ.add(this.productRepo.findById(a).orElse(null));
        }
        var saved = this.categoryRepo.save(categ);
        return this.categMapper.toDTO(saved);
    }
    
    //Find all products in a specific category
   public List<Product> productsWithSpecificCategory(String categoryName){
       String query = "SELECT p.product_id, p.product_name FROM product p JOIN category c ON(p.category_id=c.category_id) WHERE c.category_name= :category";
       return this.em.createNativeQuery(query, Product.class).setParameter("category",categoryName ).getResultList();
   }
   
   //Get the list of products along with their categories
   public List<Object[]> productsWithTheirCategoies(){
       String query = "SELECT p.product_id, p.product_name, c.category_name FROM product p JOIN category c ON(p.category_id=c.category_id)";
       return this.em.createNativeQuery(query).getResultList();
   }
   
   //Determine which products are out of stock.
   public List<Object[]> productsLowerThanValue(int value){
      var query = "SELECT product_id, product_name FROM product WHERE product_stock< :stock";
      return this.em.createNativeQuery(query).setParameter("stock", value).getResultList();
   }
   
   //List all categories and the number of products in each.
   public List<Object[]> categoriesAndNumberOfProducts(){
       var query = "SELECT c.category_name, COUNT(p.product-name) num_products FROM product p JOIN category c ON(p.category_id=c.category_id) GROUP BY c.category_name";
       return this.em.createNativeQuery(query).getResultList();
   }
   
   
   
}
