package com.example.spring_boot_test.controller;

import com.example.spring_boot_test.entity.Products;
import com.example.spring_boot_test.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;

    @GetMapping("/byid/{id}")
    public Products getProductById(@PathVariable int id){
        Products products=productService.findById(id);
        return products;
    }

    @PostMapping("/save")
    public Products saveProduct(@RequestBody Products products){
        return productService.save(products);
    }

    @PutMapping("/update/{id}")
    public Products updateProduct(@RequestBody Products products,@PathVariable int id){
        return productService.update(products,id);
    }

    @GetMapping("/byname/{name}")
    public Products getProductByName(@PathVariable String name){
        return productService.findByName(name);
    }

    @GetMapping("/all")
    public List<Products> getAllProducts(){
        return productService.findall();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id){
        productService.delete(id);
        return "Product deleted with id "+id;
    }
}
