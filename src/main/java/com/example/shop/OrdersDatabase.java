package com.example.shop;

public final class OrdersDatabase extends Database<Order> {
    public static OrdersDatabase INSTANCE = new OrdersDatabase();

    private OrdersDatabase() {
        super("orders.txt", ",", row -> new Order(Integer.parseInt(row.get(0)), row.get(1), row.get(2), Order.Status.values()[Integer.parseInt(row.get(3))]));
    }

    @Override
    ToRowMapper<Order> getToRowMapper() {

        return object -> new String[]{object.id().toString(), object.date(), object.login(), String.valueOf(object.status().ordinal())};
    }
}