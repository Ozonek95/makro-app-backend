package com.makroapp.makroapp.calculation;

import com.makroapp.makroapp.calculation.logic.GetMakroForDishes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "api/v1/calculation")
public class CalculationController {
    private final GetMakroForDishes getMakroForDishes;

    @Autowired
    public CalculationController(GetMakroForDishes getMakroForDishes) {
        this.getMakroForDishes = getMakroForDishes;
    }

    @PostMapping
    public GetMakroForDishesResponse getMakroForDishesResponse(@RequestBody GetMakroForDishesRequest request) {
        System.out.println(request);
//        return new GetMakroForDishesResponse(new HashMap<>());
        return getMakroForDishes.getMakroForDishes(request);
    }
}
