package com.makroapp.makroapp.dish;

import com.makroapp.makroapp.product.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "produkty_do_dania", joinColumns = @JoinColumn(name = "produkt_id"),
            inverseJoinColumns = @JoinColumn(name = "danie_id"))
    private List<Product> products;

    public Dish(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }
}
