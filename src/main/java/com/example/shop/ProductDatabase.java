package com.example.shop;

public final class ProductDatabase extends Database<Product> {
    public static ProductDatabase INSTANCE = new ProductDatabase();

    private ProductDatabase() {
        super(
                "products.txt", ",", row -> new Product(row.get(0), Integer.parseInt(row.get(1)), Integer.parseInt(row.get(2)), Double.parseDouble(row.get(3)))
        );
    }

    @Override
    ToRowMapper<Product> getToRowMapper() {
        return object -> new String[]{object.name(), object.id().toString(), object.count().toString(), object.sum().toString()};
    }
}

