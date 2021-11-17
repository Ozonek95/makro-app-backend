package com.makroapp.makroapp.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(String productName) {
        return productRepository.findByName(productName);
    }

    public Product addProduct(Product product) {
        if(product.getName()==null || product.getName().equals("")) {
            throw new IllegalArgumentException("Nie udało się zapisać. Podaj nazwę produktu!");
        }
        Product byName = productRepository.findByName(product.getName());
        if (byName != null) {
            throw new IllegalArgumentException("Produkt "+ product.getName() + " już istnieje!");
        }
        return productRepository.save(product);
    }

    public List<Product> getProdukty() {
        return productRepository.findAll();
    }

    public void deleteProduct(String id) {
         productRepository.deleteById(Long.valueOf(id));
    }
}
