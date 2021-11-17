package com.makroapp.makroapp.calculation.logic;

import com.makroapp.makroapp.calculation.GetMakroForDishesRequest;
import com.makroapp.makroapp.calculation.GetMakroForDishesResponse;

public interface GetMakroForDishes {
    public GetMakroForDishesResponse getMakroForDishes(GetMakroForDishesRequest getMakroForDishesRequest);
}
