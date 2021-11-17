package com.makroapp.makroapp.dish;

import com.makroapp.makroapp.product.Product;
import com.makroapp.makroapp.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/dania")
public class DishController {
    private final DishService dishService;
    private final ProductService productService;

    @Autowired
    public DishController(DishService dishService, ProductService productService) {
        this.dishService = dishService;
        this.productService = productService;
    }

    @GetMapping
    public List<Dish> getDania() {
        List<Dish> dania = dishService.getDania();
        dania.sort(Comparator.comparing(Dish::getName));
        return dania;
    }

    @PostMapping("/dodajDanie")
    public Dish dodajDanie(@RequestBody DodajDanieRequest dodajDanieRequest) {
        List<Product> produkty = dodajDanieRequest
                .getProducts()
                .stream().map(productService::getProduct)
                .collect(Collectors.toList());

        Dish dish = new Dish(dodajDanieRequest.getName(), produkty);
        return dishService.saveDish(dish);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteDish(@PathVariable String id) {
        dishService.deleteDish(id);
    }

}
