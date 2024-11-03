package com.example.letsplay.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.letsplay.user.Role;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PutMapping;






@RestController
@RequestMapping("/products")
public class Productcontroller {
    
    private final Productservice productservice;

    @Autowired
    public Productcontroller(Productservice productservice) {
        this.productservice = productservice;
    }

    @GetMapping
    public ResponseEntity<?> getMethodName() {
        return ResponseEntity.ok().body(productservice.getAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product , HttpSession session) {
       
        Object userId = session.getAttribute("userid");


        if (product.isValid() && isSeller(session) && userId != null ) {
            product.setUserId(userId.toString());
            productservice.insert(product);
            return ResponseEntity.ok().body( userId);
        }

        
        return ResponseEntity.badRequest().body("bad request");
    }
    
    @GetMapping("/{pId}")
    public Optional<Product> getProductsById(@PathVariable String pId) {
        return productservice.getProduct(pId);
    }

    @GetMapping("/seller/{sellerId}")
    public List<Product> getProductsBySeller(@PathVariable String sellerId) {
        return productservice.getBySeller(sellerId);
    } 



    @PutMapping("/update/{pId}")
    public ResponseEntity<String> updateProduct(@PathVariable String pId ,  HttpSession session , @RequestBody Product newproduct) {

        if (!newproduct.isValid()) {
            System.out.println("correct prod");
            return ResponseEntity.badRequest().body("bad request");
        }
        
        Optional<Product> productObj = productservice.getProduct(pId);
        
        if (productObj.isPresent()) {
            Product product = productObj.get();
            System.out.println("present prod");


            if (IsProductOwner(session, product.getUserId())) {
                System.out.println("owned prod");
                newproduct.setId(session.getAttribute("userid").toString());
                productservice.update(product ,newproduct);
                return ResponseEntity.ok().body("product updated");
            }
        }

        return ResponseEntity.badRequest().body("bad request");
    }


    @DeleteMapping("/delete/{pId}")
    public ResponseEntity<String> DeleteProduct(@PathVariable String pId ,  HttpSession session  ) {

        Optional<Product> productObj = productservice.getProduct(pId);
        
        if (productObj.isPresent()) {
            Product product = productObj.get();
            if (IsProductOwner(session, product.getUserId())) {
                productservice.remove(product);
                return ResponseEntity.ok().body("product deleted");
            }
        }

        return ResponseEntity.badRequest().body("bad request");
    }


    private  boolean  isSeller(HttpSession session) {
        Object role = session.getAttribute("role");
        if (role != null) {
            return Role.valueOf(role.toString()) == Role.SELLER;
        }
        return false;
    }

    private boolean  IsProductOwner(HttpSession session , String ownerId) {
        Object userId = session.getAttribute("userid");
        if(userId != null) {
            return userId.toString().equals(ownerId);
        }
        return false;
    }
    
}
