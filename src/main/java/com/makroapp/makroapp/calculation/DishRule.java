package com.makroapp.makroapp.calculation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DishRule {
    String dishName;
    List<ProductRuleInfo> productRuleInfos;
}
