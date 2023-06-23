package com.example.spring_boot_test.service;

import com.example.spring_boot_test.entity.Products;

import java.util.List;

public interface ProductService {
    Products save(Products products);

    Products findByName(String name);

    Products update(Products products,int id);

    Products findById(int id);

    List<Products> findall();

    String delete(int id);
}
