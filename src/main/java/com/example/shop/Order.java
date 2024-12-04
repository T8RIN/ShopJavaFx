package com.example.shop;

import java.util.List;

public record Order(Integer id, String date, String login, Status status) {

    public Order copyWithNewStatus(Status status) {
        return new Order(id, date, login, status);
    }

    List<Product> products() {
        for (Order order : OrdersDatabase.INSTANCE.data) {
            if (order.id.equals(this.id)) {
                var productIds = ProductOrdersDatabase.INSTANCE.data.stream().filter(productOrder -> productOrder.orderId().equals(order.id)).map(ProductOrder::productId);

                return productIds.map(productId -> ProductsDatabase.INSTANCE.data.stream().filter(p -> p.id().equals(productId)).toList().get(0)).toList();
            }
        }

        return List.of();
    }

    public enum Status {
        New(Strings.NewOrder), Payed(Strings.PayedOrder), Shipped(Strings.ShippedOrder);

        public final String nameRu;

        Status(String nameRu) {
            this.nameRu = nameRu;
        }

        @Override
        public String toString() {
            return nameRu;
        }
    }

}
