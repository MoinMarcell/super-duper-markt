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
            new Product("Gouda", ProductType.CHEESE, new BigDecimal("1.99"), 50, LocalDate.of(2024, 3, 28),
                    LocalDate.of(2024, 2, 2)),
            new Product("Cheddar", ProductType.CHEESE, new BigDecimal("2.99"), 40, LocalDate.of(2024, 5, 6),
                    LocalDate.of(2024, 2, 1)),
            new Product("Merlot", ProductType.WINE, new BigDecimal("15.00"), 20, null, LocalDate.of(2024, 1, 20)),
            new Product("Chardonnay", ProductType.WINE, new BigDecimal("20.00"), 40, null, LocalDate.of(2023, 12, 1)),
            new Product("Milk", ProductType.MILK, new BigDecimal("1.50"), 10, LocalDate.of(2024, 2, 9),
                    LocalDate.of(2024, 1, 24)),
            new Product("Bread", ProductType.BREAD, new BigDecimal("2.00"), 15, LocalDate.of(2024, 2, 6),
                    LocalDate.of(2024, 1, 28))
    );

    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

}
