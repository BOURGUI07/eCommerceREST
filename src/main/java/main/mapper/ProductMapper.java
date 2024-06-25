/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.mapper;

import main.dto.ProductDTO;
import main.entity.Product;
import main.repo.CategoryRepo;
import main.repo.OrderDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hp
 */
@Service
public class ProductMapper {
    @Autowired
    public ProductMapper(CategoryRepo categRepo,OrderDetailRepo detailRepo) {
        this.detailRepo=detailRepo;
        this.categRepo = categRepo;
    }
    
    private CategoryRepo categRepo;
    private OrderDetailRepo detailRepo;
    
    public Product toProduct(ProductDTO x){
        var p = new Product();
        p.setName(x.getName());
        p.setDesc(x.getDesc());
        p.setCategory(this.categRepo.findById(x.getCategoryId()).orElse(null));
        p.setPrice(x.getPrice());
        p.setStock(x.getStock());
        p.setOrderDetails(this.detailRepo.findAllById(x.getOrdersId()));
        return p;
    }
    
    public ProductDTO toDTO(Product p ){
        var x = new ProductDTO();
        x.setCategoryId(p.getCategory().getId());
        x.setDesc(p.getDesc());
        x.setId(p.getId());
        x.setName(p.getName());
        x.setPrice(p.getPrice());
        x.setStock(p.getStock());
        for(var detail:p.getOrderDetails()){
            x.addorderDetailId(detail.getId());
        }
        return x;
    }
}
