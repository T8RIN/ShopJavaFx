package com.example.shop;

import java.util.List;

public record Order(Integer id, String date, String login) {

    List<Product> products() {
        for(Order order : OrdersDatabase.INSTANCE.data) {
            if(order.id.equals(this.id)) {
                var productIds = ProductOrdersDatabase.INSTANCE.data.stream().filter(productOrder -> productOrder.orderId().equals(order.id)).map(ProductOrder::productId);

                return productIds.map(productId ->  ProductDatabase.INSTANCE.data.stream().filter(p -> p.id().equals(productId)).toList().get(0)).toList();
            }
        }

        return List.of();
    }

}
