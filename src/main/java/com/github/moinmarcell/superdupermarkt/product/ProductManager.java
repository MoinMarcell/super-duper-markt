package com.github.moinmarcell.superdupermarkt.product;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
class ProductManager implements CommandLineRunner {

    private static final BigDecimal QUALITY_MULTIPLIER = new BigDecimal("0.10");
    private static final int MAX_WINE_QUALITY = 50;
    private static final int SHELF_LIFE_DIVISOR = 10;
    private final ProductRepository productRepository;

    boolean isExpired(Product product, LocalDate date) {
        return product.expires() != null && product.expires().isBefore(date);
    }

    boolean checkQuality(Product product) {
        if (product.type() == ProductType.CHEESE) {
            return product.quality() >= 30;
        }

        return product.quality() > 0;
    }

    BigDecimal calculatePrice(Product product, LocalDate date) {
        BigDecimal multiplier =
                QUALITY_MULTIPLIER.multiply(BigDecimal.valueOf(calculateQuality(product, date)));

        return product.price().add(multiplier);
    }

    private int calculateQuality(Product product, LocalDate date) {
        if (product.type().equals(ProductType.CHEESE)) {
            return calculateCheeseQuality(product, date);
        }

        if (product.type().equals(ProductType.WINE)) {
            return calculateWineQuality(product, date);
        }

        return product.quality();
    }

    private int calculateCheeseQuality(Product product, LocalDate date) {
        int daysSinceShelved = (int) ChronoUnit.DAYS.between(product.shelved(), date);
        return product.quality() - daysSinceShelved;
    }

    private int calculateWineQuality(Product product, LocalDate date) {
        long shelfLife = ChronoUnit.DAYS.between(product.shelved(), date);
        int qualityIncrease = (int) (shelfLife / SHELF_LIFE_DIVISOR);

        return Math.min(product.quality() + qualityIncrease, MAX_WINE_QUALITY);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.printf("%nAnzahl der Produkte: %d%n", productRepository.findAll().size());
        productRepository.findAll().forEach(product -> {
            String output = String.format("Produkt: %s, Preis: %s, Qualität: %d",
                    product.name(), product.price(), product.quality());
            System.out.println(output);
        });

        LocalDate currentDate = LocalDate.of(2024, 2, 5);
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nBitte geben Sie die Anzahl der Tage ein: ");
        final LocalDate endDate = currentDate.plusDays(scanner.nextInt());
        scanner.close();

        while (!currentDate.isAfter(endDate)) {
            System.out.println("Datum: " + currentDate);

            LocalDate finalCurrentDate = currentDate;
            productRepository.findAll().forEach(product -> {
                BigDecimal price = calculatePrice(product, finalCurrentDate);
                int quality = calculateQuality(product, finalCurrentDate);
                boolean isExpired = product.expires() != null && isExpired(product, finalCurrentDate);

                boolean mustBeDisposed = isExpired || (product.type() == ProductType.CHEESE && quality < 30);

                if (!mustBeDisposed) {
                    mustBeDisposed = !checkQuality(product);
                }

                String output = String.format("Produkt: %s, Preis: %s, Qualität: %d, %s",
                        product.name(), price, quality, mustBeDisposed ? "muss entsorgt werden" : "ist noch haltbar");
                System.out.println(output);
            });

            System.out.println("-------------------------------");
            currentDate = currentDate.plusDays(1);
        }
    }
}

