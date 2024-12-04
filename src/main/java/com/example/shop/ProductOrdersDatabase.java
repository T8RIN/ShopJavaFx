package com.example.shop;


public final class ProductOrdersDatabase extends Database<ProductOrder> {
    public static ProductOrdersDatabase INSTANCE = new ProductOrdersDatabase();

    private ProductOrdersDatabase() {
        super("product-orders.txt", ",", row -> new ProductOrder(Integer.parseInt(row.get(0)), Integer.parseInt(row.get(1))));
    }

    @Override
    ToRowMapper<ProductOrder> getToRowMapper() {

        return object -> new String[]{object.orderId().toString(), object.productId().toString()};
    }
}
