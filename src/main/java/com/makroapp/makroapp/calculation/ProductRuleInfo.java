package com.makroapp.makroapp.calculation;

import com.makroapp.makroapp.product.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductRuleInfo {
    Product product;
    String quantity;
}
