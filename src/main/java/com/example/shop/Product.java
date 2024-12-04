package com.example.shop;

public record Product(
        String name,
        Integer id,
        Integer count,
        Double sum
) {
}
