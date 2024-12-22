# Spring Boot MongoDB CRUD API

A RESTful API built with Spring Boot and MongoDB that implements user and product management with authentication, authorization, and security measures.

## Features

### Core Functionality
- Complete CRUD operations for Users and Products
- Token-based Authentication
- Role-based Authorization (seller/client)
- Password hashing and salting
- Input validation and sanitization
- Exception handling with appropriate HTTP status codes

### Security Measures
- Password encryption
- MongoDB injection prevention
- Protected sensitive data
- HTTPS support

## API Endpoints

### Authentication
- `POST /register` - Register a new user
- `POST /login` - User login and get token



### Users
- `GET /users` - Get all users 
- `PUT changepassword` - Change user's password
- `DELETE deletemyaccount` - Delete user

### Products
- `GET /products` - Get all products (Public)
- `GET /products/get/{id}` - Get product by ID
- `POST /products/add` - Create new product (Authenticated)
- `GET /products/seller/{seller_id}` - Get products of single seller
- `PUT /products/update/{id}` - Update product (Owner)
- `DELETE /products/delete/{id}` - Delete product (Owner)

## Prerequisites

- Java 11 or higher
- Maven
- MongoDB

## Configuration

Check `application.properties`:

properties
# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/your_database

# Server Configuration
server.port=8443```

## Installation & Running
git clone https://github.com/laye-diop/lets-play.git
cd spring-boot-mongodb-api