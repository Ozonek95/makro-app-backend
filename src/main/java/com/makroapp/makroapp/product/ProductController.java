package com.makroapp.makroapp.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/produkty")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        List<Product> products = productService.getProdukty();
        products.sort(Comparator.comparing(Product::getName));
        return products;
    }

    @PostMapping("/dodajProdukt")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteProduct(@PathVariable String id) {
         productService.deleteProduct(id);
    }
}
