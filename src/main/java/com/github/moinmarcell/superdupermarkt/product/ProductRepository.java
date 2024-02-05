package com.github.moinmarcell.superdupermarkt.product;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class ProductRepository {

    private final List<Product> products = Arrays.asList(
            new Product("Gouda", ProductType.CHEESE, new BigDecimal("1.99"), 50, LocalDate.now().plusDays(30)),
            new Product("Cheddar", ProductType.CHEESE, new BigDecimal("2.99"), 40, LocalDate.now().plusDays(40)),
            new Product("Merlot", ProductType.WINE, new BigDecimal("15.00"), 20, LocalDate.now().plusDays(100)),
            new Product("Chardonnay", ProductType.WINE, new BigDecimal("20.00"), 40, LocalDate.now().plusDays(200)),
            new Product("Milk", ProductType.MILK, new BigDecimal("1.50"), 10, LocalDate.now().plusDays(10)),
            new Product("Bread", ProductType.BREAD, new BigDecimal("2.00"), 15, LocalDate.now().plusDays(5))
    );

    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

}
