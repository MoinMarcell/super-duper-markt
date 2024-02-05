package com.github.moinmarcell.superdupermarkt.product;

import java.math.BigDecimal;
import java.time.LocalDate;

record Product(
        String name,
        ProductType type,
        BigDecimal price,
        int quality,
        LocalDate expires,
        LocalDate shelved
) {
}
