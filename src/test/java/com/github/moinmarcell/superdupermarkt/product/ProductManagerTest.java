package com.github.moinmarcell.superdupermarkt.product;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ProductManagerTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductManager productManager = new ProductManager(productRepository);

    Product cheese = new Product("Gouda", ProductType.CHEESE, new BigDecimal("1.99"), 50, LocalDate.of(2024, 3, 28),
            LocalDate.of(2024, 2, 2));

    Product wine = new Product("Merlot", ProductType.WINE, new BigDecimal("15.00"), 20, null, LocalDate.of(2024, 1, 20));

    Product bread = new Product("Bread", ProductType.BREAD, new BigDecimal("2.00"), 15, LocalDate.of(2024, 2, 6),
            LocalDate.of(2024, 1, 28));

    @Test
    void checkQualityShouldReturnTrueForCheeseWithQuality30() {
        cheese = new Product("Gouda", ProductType.CHEESE, new BigDecimal("1.99"), 30, LocalDate.of(2024, 3, 28),
                LocalDate.of(2024, 2, 2));
        assertTrue(productManager.checkQuality(cheese));
    }

    @Test
    void checkQualityShouldReturnFalseForCheeseWithQuality29() {
        cheese = new Product("Gouda", ProductType.CHEESE, new BigDecimal("1.99"), 29, LocalDate.of(2024, 3, 28),
                LocalDate.of(2024, 2, 2));
        assertFalse(productManager.checkQuality(cheese));
    }

    @Test
    void checkQualityShouldReturnTrueForWineWithQuality1() {
        wine = new Product("Merlot", ProductType.WINE, new BigDecimal("15.00"), 1, null, LocalDate.of(2024, 1, 20));
        assertTrue(productManager.checkQuality(wine));
    }

    @Test
    void checkQualityShouldReturnFalseForWineWithQuality0() {
        wine = new Product("Merlot", ProductType.WINE, new BigDecimal("15.00"), 0, null, LocalDate.of(2024, 1, 20));
        assertFalse(productManager.checkQuality(wine));
    }

    @Test
    void isExpiredShouldReturnTrueForExpiredProduct() {
        assertTrue(productManager.isExpired(bread, LocalDate.of(2024, 2, 7)));
    }

    @Test
    void isExpiredShouldReturnFalseForNotExpiredProduct() {
        assertFalse(productManager.isExpired(bread, LocalDate.of(2024, 2, 5)));
    }

    @Test
    void calculatePriceShouldReturnPriceForWine() {
        BigDecimal expected = new BigDecimal("15.00").add(new BigDecimal("2.00"));
        assertEquals(expected, productManager.calculatePrice(wine, LocalDate.of(2024, 1, 20)));
    }

    @Test
    void calculatePriceShouldReturnPriceForBread() {
        BigDecimal expected = new BigDecimal("2.00").add(new BigDecimal("1.50"));
        assertEquals(expected, productManager.calculatePrice(bread, LocalDate.of(2024, 2, 6)));
    }

}