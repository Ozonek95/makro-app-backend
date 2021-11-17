package com.makroapp.makroapp.dish;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DodajDanieRequest {
    private String name;
    private List<String> products;
}
