package com.example.letsplay.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;






@RestController
@RequestMapping("/products")
public class Productcontroller {
    
    @Autowired
    private  Productservice productservice;


    @PermitAll
    @GetMapping
    public ResponseEntity<?> getMethodName() {
        return ResponseEntity.ok().body(productservice.getAll());
    }

    @PreAuthorize("@productservice.ConnectedAndSeller(#session)")
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product , HttpSession session) {
       
        Object userId = session.getAttribute("userid");
        try {
            product.setUserId(userId.toString());
            productservice.insert(product);
        } catch (Exception e) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body("product added");
    }
    
    @GetMapping("get/{pId}")
    public ResponseEntity<?> getProductsById(@PathVariable String pId) {

        Optional<Product> pr = productservice.getProduct(pId);
        if (pr.isPresent()) {
            return ResponseEntity.ok().body(pr.get());
        } 
        return ResponseEntity.notFound().build();
    }
    
    @PostAuthorize("@productservice.ConnectedAndSeller(#session)")
    @GetMapping("/seller/{sellerId}")
    public List<Product> getProductsBySeller(@PathVariable String sellerId ,  HttpSession session) {
        return productservice.getBySeller(sellerId);
    } 


    @PreAuthorize("@productservice.ConnectedAndSeller(#session)")
    @PutMapping("/update/{pId}")
    public ResponseEntity<String> updateProduct(@PathVariable String pId ,  HttpSession session , @Valid @RequestBody Product newproduct) {
        
        Optional<Product> productObj = productservice.getProduct(pId);
        
        if (productObj.isPresent()) {
            Product product = productObj.get();

            if (IsProductOwner(session, product.getUserId())) {
                try {
                    newproduct.setId(session.getAttribute("userid").toString());
                    productservice.update(product ,newproduct);
                    return ResponseEntity.ok().body("product updated");
                } catch (Exception e) {
                    return ResponseEntity.badRequest().build();
                }
            } else {
                return  ResponseEntity.status(403).body("you are not the owner");
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("@productservice.ConnectedAndSeller(#session)")
    @DeleteMapping("/delete/{pId}")
    public ResponseEntity<String> DeleteProduct(@PathVariable String pId ,  HttpSession session  ) {

        Optional<Product> productObj = productservice.getProduct(pId);
        
        if (productObj.isPresent()) {
            Product product = productObj.get();
            if (IsProductOwner(session, product.getUserId())) {
                try {
                    productservice.remove(product);
                    return ResponseEntity.ok().body("product deleted");
                } catch (Exception e) {
                    return ResponseEntity.badRequest().build();
                }
            } else {
                return ResponseEntity.status(403).body("you are not the owner");
            }
        }

        return ResponseEntity.notFound().build();
    }



    private boolean  IsProductOwner(HttpSession session , String ownerId) {
        Object userId = session.getAttribute("userid");
        if(userId != null) {
            return userId.toString().equals(ownerId);
        }
        return false;
    }
    
}
