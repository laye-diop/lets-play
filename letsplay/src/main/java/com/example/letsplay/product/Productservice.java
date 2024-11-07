package com.example.letsplay.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.letsplay.user.Role;

import jakarta.servlet.http.HttpSession;

@Service
public class Productservice {
    @Autowired
    private  ProductRepository repository;


    public List<Product> getAll() {
        return  repository.findAll();
    }
        
    public void insert(Product p) {
        p.setId(null);
        repository.insert(p);
    }
    public boolean  ConnectedAndSeller(HttpSession session) {
        Object userId = session.getAttribute("userid");
        boolean connected = userId != null;

        Object role = session.getAttribute("role");
        boolean seller = role != null && Role.valueOf(role.toString()) == Role.SELLER;

        return connected && seller;
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
