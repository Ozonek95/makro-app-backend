package com.makroapp.makroapp.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    private final DishRepository dishRepository;

    public List<Dish> getDania() {
        return this.dishRepository.findAll();
    }

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public Dish saveDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public void deleteDish(String id) {
        dishRepository.deleteById(Long.valueOf(id));
    }
}
