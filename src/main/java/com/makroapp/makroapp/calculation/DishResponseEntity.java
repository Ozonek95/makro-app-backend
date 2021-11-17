package com.makroapp.makroapp.calculation;

import com.makroapp.makroapp.calculation.logic.DishInfo;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DishResponseEntity {
    private String name;
    private DishInfo dishInfo;
}
