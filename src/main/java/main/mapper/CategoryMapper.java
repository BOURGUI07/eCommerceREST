/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.mapper;

import main.dto.CategoryDTO;
import main.entity.Category;
import main.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class CategoryMapper {
    
    private ProductRepo productRepo;
    
    @Autowired
    public CategoryMapper(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Category toCategory(CategoryDTO x){
        var c = new Category();
        c.setName(x.getName());
        c.setDesc(x.getDesc());
        c.setCreatedBy(x.getCreatedBy());
        c.setProducts(this.productRepo.findAllById(x.getProductsId()));
        return c;
    }
    
    public CategoryDTO toDTO(Category c){
        var x = new CategoryDTO();
        x.setId(c.getId());
        x.setName(c.getName());
        x.setDesc(c.getDesc());
        x.setCreatedBy(c.getCreatedBy());
        x.setCreatedAT(c.getCreatedAt());
        for(var p:c.getProducts()){
            x.addProductID(p.getId());
        }
        return x;
    }
}
