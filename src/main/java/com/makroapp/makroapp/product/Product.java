package com.makroapp.makroapp.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Product implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private MakroZrodlo makroZrodlo;
    private double quantityInHundredGrams;

    public Product(String name, MakroZrodlo makroZrodlo, double quantityInHundredGrams) {
        this.name = name;
        this.makroZrodlo = makroZrodlo;
        this.quantityInHundredGrams = quantityInHundredGrams;
    }
}
