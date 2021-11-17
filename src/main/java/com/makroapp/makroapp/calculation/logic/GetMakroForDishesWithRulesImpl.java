package com.makroapp.makroapp.calculation.logic;

import com.makroapp.makroapp.calculation.*;
import com.makroapp.makroapp.dish.Dish;
import com.makroapp.makroapp.product.MakroZrodlo;
import com.makroapp.makroapp.product.Product;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GetMakroForDishesWithRulesImpl implements GetMakroForDishes {
    @Override
    public GetMakroForDishesResponse getMakroForDishes(GetMakroForDishesRequest request) {
        List<Dish> dishes = request.getDishes();
        List<DishResponseEntity> dishResponseEntities =
                dishes.stream().map(dish -> getDishResponseEntity(dish, request)).collect(Collectors.toList());
        return new GetMakroForDishesResponse(dishResponseEntities);
    }

    private DishResponseEntity getDishResponseEntity(Dish dish, GetMakroForDishesRequest request) {
        DishResponseEntity dishResponseEntity = new DishResponseEntity();
        dishResponseEntity.setName(dish.getName());
        dishResponseEntity.setDishInfo(getInfoForDish(dish, request));
        return dishResponseEntity;
    }

    DishInfo getInfoForDish(Dish dish, GetMakroForDishesRequest request) {
        DishInfo dishInfo = new DishInfo();
        List<Product> products = dish.getProducts();

        List<Product> produktyZBiałkiem = getProductWithMakro(products, MakroZrodlo.WHEY);
        List<Product> produktyZTłuszczem = getProductWithMakro(products, MakroZrodlo.FAT);
        List<Product> produktyZWęglami = getProductWithMakro(products, MakroZrodlo.CARBO);

        validateMakro(request, products, dishInfo);

        Map<Product, Double> produktToQuantityMap = new HashMap<>();

        addProducts(produktyZBiałkiem, MakroZrodlo.WHEY, produktToQuantityMap, request);
        addProducts(produktyZTłuszczem, MakroZrodlo.FAT, produktToQuantityMap, request);
        addProducts(produktyZWęglami, MakroZrodlo.CARBO, produktToQuantityMap, request);


        processRules(dish, request, produktToQuantityMap);


        List<ProductQuantity> productQuantities = new ArrayList<>();

        for (Map.Entry<Product, Double> produktLongEntry : produktToQuantityMap.entrySet()) {
            productQuantities.add(new ProductQuantity(produktLongEntry.getKey().getName(),
                    produktLongEntry.getValue()));

        }

        dishInfo.setProductQuantities(productQuantities);
        return dishInfo;
    }

    private void processRules(Dish dish, GetMakroForDishesRequest request, Map<Product, Double> produktToQuantityMap) {
        if (thereIsARuleForDish(dish, request)) {
            Optional<DishRule> ruleForTheDish = request.getRules()
                    .stream()
                    .filter(dishRule -> dishRule.getDishName().equals(dish.getName()))
                    .findFirst();


            DishRule dishRule = ruleForTheDish.get();
            for (ProductRuleInfo productRuleInfo : dishRule.getProductRuleInfos()) {
                produktToQuantityMap.put(productRuleInfo.getProduct(),
                        getQuantityOfProductForRequestedMakro(productRuleInfo.getProduct(),
                                Double.parseDouble(productRuleInfo.getQuantity())));
            }
        }
    }

    private boolean thereIsARuleForDish(Dish dish, GetMakroForDishesRequest request) {
        if (request.getRules() == null || request.getRules().isEmpty()) {
            return false;
        }
        return request.getRules().stream().anyMatch(dishRule -> dishRule.getDishName().equals(dish.getName()));
    }

    private void addProducts(List<Product> products, MakroZrodlo makroZrodlo,
                             Map<Product, Double> productToQuantityMap,
                             GetMakroForDishesRequest request) {

        for (Product product : products) {
            productToQuantityMap.put(product, getQuantity(products, product, makroZrodlo, request));
        }
    }

    private Double getQuantity(List<Product> produkty, Product product, MakroZrodlo makroZrodlo,
                               GetMakroForDishesRequest request) {
        if (produkty.size() == 1) {
            return getQuantityOfProductForRequestedMakro(product,
                    getRequiredMakroFromRequest(request,
                            makroZrodlo));
        }

        return roundResultToBeDividableByFiveDown(getQuantityOfProductForRequestedMakro(product,
                getRequiredMakroFromRequest(request,
                        makroZrodlo), produkty.size()));
    }

    private double roundResultToBeDividableByFiveDown(double result) {
        double currentResult = Math.round(result);
        while (currentResult % 5 != 0) {
            currentResult = currentResult - 1;
        }
        return currentResult;
    }

    private void validateMakro(GetMakroForDishesRequest request, List<Product> products, DishInfo dishInfo) {
        if (Double.parseDouble(request.getMakro().getWhey()) > 0 && getProductWithMakro(products,
                MakroZrodlo.WHEY).isEmpty()) {
            dishInfo.addAdditionalInfo("Brak źródeł białka w produktach.");
        }

        if (Double.parseDouble(request.getMakro().getCarbo()) > 0 && getProductWithMakro(products,
                MakroZrodlo.CARBO).isEmpty()) {
            dishInfo.addAdditionalInfo("Brak źródeł węgli w produktach.");
        }

        if (Double.parseDouble(request.getMakro().getFat()) > 0 && getProductWithMakro(products,
                MakroZrodlo.FAT).isEmpty()) {
            dishInfo.addAdditionalInfo("Brak źródeł tłuszczu w produktach.");
        }
    }

    private List<Product> getProductWithMakro(List<Product> products, MakroZrodlo makroZrodlo) {
        return products.stream().filter(produkt -> produkt.getMakroZrodlo().equals(makroZrodlo)).collect(Collectors.toList());
    }

    private double getRequiredMakroFromRequest(GetMakroForDishesRequest request,
                                               MakroZrodlo makroZrodlo) {
        switch (makroZrodlo) {
            case WHEY -> {
                return Double.parseDouble(request.getMakro().getWhey());
            }
            case FAT -> {
                return Double.parseDouble(request.getMakro().getFat());
            }
            case CARBO -> {
                return Double.parseDouble(request.getMakro().getCarbo());
            }
        }
        throw new IllegalStateException("Zle makro zrodlo" + makroZrodlo);
    }

    private double getQuantityOfProductForRequestedMakro(Product product, double requestedMakro) {
        return roundResultToBeDividableByFiveDown((requestedMakro / product.getQuantityInHundredGrams()) * 100);
    }

    private double getQuantityOfProductForRequestedMakro(Product product,
                                                         double requestedMakro,
                                                         int numberOfProductsWithTheSameMakroSource) {
        return roundResultToBeDividableByFiveDown(((requestedMakro /
                product.getQuantityInHundredGrams()) * 100 * 1.0 /
                (double) numberOfProductsWithTheSameMakroSource));
    }
}
