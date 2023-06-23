package com.example.spring_boot_test.service;

import com.example.spring_boot_test.entity.Products;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    List<Products> productsList=new ArrayList<Products>();;
    @Override
    public Products save(Products products) {
//        Products products1=new Products();
//        products1.setId(products.getId());
//        products1.setName(products.getName());
//        products1.setDescription(products.getDescription());
//        products1.setPrice(products.getPrice());
        productsList.add(products);
        System.out.println(products);
        return products;
    }

    @Override
    public Products findByName(String name) {
        return productsList.stream().filter(p->p.getName().equals(name)).findFirst().get();
    }

    @Override
    public Products update(Products products,int id) {
        Products products1=productsList.stream().filter(p->p.getId()==id).findFirst().get();
        products1.setName(products.getName());
        products1.setDescription(products.getDescription());
        products1.setPrice(products.getPrice());
        return products1;
    }

    @Override
    public Products findById(int id) {
        Products products=productsList.stream().filter(p -> p.getId() == id).findAny().get();
        return products;
    }

    @Override
    public List<Products> findall() {
    return productsList;
    }

    @Override
    public String delete(int id) {
        productsList.removeIf(p->p.getId()==id);
        return productsList.get(id)+"deleted";
    }
}
