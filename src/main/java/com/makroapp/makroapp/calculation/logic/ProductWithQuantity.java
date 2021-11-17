package com.makroapp.makroapp.calculation.logic;

import com.makroapp.makroapp.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductWithQuantity {
    private final Product product;
    private final Long quantity;
}
