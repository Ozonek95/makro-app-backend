package com.makroapp.makroapp;

import com.makroapp.makroapp.dish.Dish;
import com.makroapp.makroapp.dish.DishRepository;
import com.makroapp.makroapp.product.MakroZrodlo;
import com.makroapp.makroapp.product.Product;
import com.makroapp.makroapp.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class MakroAppApplication {

    private static Map<String, Product> nameToProductMap = null;

    public static void main(String[] args) {
        SpringApplication.run(MakroAppApplication.class, args);

    }

    @Bean
    CommandLineRunner commandLineRunner(DishRepository dishRepository, ProductRepository productRepository) {
        return args -> {
            productRepository.saveAll(List.of(new Product("Pierś z kurczaka", MakroZrodlo.WHEY,
                            20),
                    new Product("Skyr", MakroZrodlo.WHEY, 12),
                    new Product("Twaróg", MakroZrodlo.WHEY, 20),
                    new Product("Pierś z indyka", MakroZrodlo.WHEY, 20),
                    new Product("Białko jaj", MakroZrodlo.WHEY, 10),
                    new Product("Ryż", MakroZrodlo.CARBO, 80),
                    new Product("Makaron", MakroZrodlo.CARBO, 60),
                    new Product("Kasza", MakroZrodlo.CARBO, 80),
                    new Product("Pieczywo", MakroZrodlo.CARBO, 50),
                    new Product("Wafle ryżowe", MakroZrodlo.CARBO, 80),
                    new Product("Banan", MakroZrodlo.CARBO, 20),
                    new Product("Orzechy", MakroZrodlo.FAT, 50),
                    new Product("Żółtka jaj", MakroZrodlo.FAT, 31),
                    new Product("Oliwa z oliwek", MakroZrodlo.FAT, 100),
                    new Product("Masło orzechowe", MakroZrodlo.FAT, 50),
                    new Product("Wiórki kokosowe", MakroZrodlo.FAT, 50)));

            List<Product> all = productRepository.findAll();

            nameToProductMap =
                    all.stream().collect(Collectors.toMap((produkt -> produkt.getName()),
                            (produkt -> produkt)));


            List<Dish> danies = List.of(
                    new Dish("Chleb tostowy z masłem orzechowym i twarogiem",
                            List.of(getProduct("Pieczywo"),
                                    getProduct("Masło orzechowe"),
                                    getProduct("Twaróg"))),
                    new Dish("Bagietka z kurczakiem i warzywami",
                            List.of(getProduct("Pieczywo"),
                                    getProduct("Pierś z kurczaka"))),
                    new Dish("Skyr z bananami i kokosem",
                            List.of(getProduct("Skyr"),
                                    getProduct("Banan"),
                                    getProduct("Wiórki kokosowe"))),
                    new Dish("Ryż z indykiem i surówką z oliwą z oliwek",
                            List.of(
                                    getProduct("Ryż"),
                                    getProduct("Pierś z indyka"),
                                    getProduct("Oliwa z oliwek"))),
                    new Dish("Makaron z serem i owocami",
                            List.of(
                                    getProduct("Makaron"),
                                    getProduct("Twaróg"),
                                    getProduct("Banan"))),
                    new Dish("Skyr z kurczakiem",
                            List.of(
                                    getProduct("Skyr"),
                                    getProduct("Pierś z kurczaka"))));

            dishRepository.saveAll(danies);

            List<Dish> all1 = dishRepository.findAll();

            for (Dish dish : all1) {
                System.out.println("Znalezione danie: "+ dish);
            }

        };


    }

    private static Product getProduct(String name) {
        return nameToProductMap.get(name);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
