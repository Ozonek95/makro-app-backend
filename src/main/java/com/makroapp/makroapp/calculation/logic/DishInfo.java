package com.makroapp.makroapp.calculation.logic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DishInfo implements Serializable {
    private List<ProductQuantity> productQuantities;
    private List<String> additionalInfo = new ArrayList<>();

    public void addAdditionalInfo(String info) {
        additionalInfo.add(info);
    }
}
