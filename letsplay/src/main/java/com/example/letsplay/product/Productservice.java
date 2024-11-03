package com.example.letsplay.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Productservice {
    private final ProductRepository repository;

    @Autowired
    public Productservice(ProductRepository repository) {
        this.repository = repository;
    }


    public List<Product> getAll() {
        return  repository.findAll();
    }

    public void insert(Product p) {
        repository.insert(p);
    }

    public Optional<Product> getProduct(String pid) {
        return  repository.findById(pid);
    }
    
    public List<Product> getBySeller(String sellerId) {
        return repository.findByUserId(sellerId);
    }


    public void update(Product product , Product newproduct) {
        product.setName(newproduct.getName());
        product.setPrice(newproduct.getPrice());
        product.setDescription(newproduct.getDescription());
        repository.save(product);
    }


    public void remove(Product product) {
        repository.delete(product);    
    }
    
}
