package com.makroapp.makroapp.calculation;

import com.makroapp.makroapp.dish.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GetMakroForDishesRequest {
    private final List<Dish> dishes;
    private final Makro makro;
    private final List<DishRule> rules;
}
