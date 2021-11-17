package com.makroapp.makroapp.calculation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GetMakroForDishesResponse implements Serializable {
    List<DishResponseEntity> dishResponseEntityList;
}
