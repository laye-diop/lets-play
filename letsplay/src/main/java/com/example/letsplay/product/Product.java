package com.example.letsplay.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document("Product")
public class Product {

    @Id
    private String id;
    
    @NotNull(message = "the name is required")
    @Size(min = 2, message = "Le nom de passe doit contenir au moins 2 caractères")
    private String name;

    @NotNull(message = "the description is required")
    @Size(min = 2, message = "La nom description doit contenir au moins 2 caractères")
    private String description;

    @NotNull(message = "the price is required")
    private Double price;


    @Field("owner")
    private String userId;

    public Product() {}

    public Product(String description, String id, String name, Double price, String userId) {
        this.description = description;
        this.id = id;
        this.name = name;
        this.price = price;
        this.userId = userId;
    }

    public Product(String description, String name, Double price, String userId) {
        this.description = description;
        this.name = name;
        this.price = price;
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
