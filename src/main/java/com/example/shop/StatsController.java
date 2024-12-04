package com.example.shop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatsController {

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;

    private final ObservableList<String> productNames = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        var products = ProductDatabase.INSTANCE.data.stream().map(Product::name).toList();
        productNames.addAll(products);
        xAxis.setCategories(productNames);
        setProductData(OrdersDatabase.INSTANCE.data);
    }

    public void setProductData(List<Order> orders) {
        Map<String, Long> productCounts = orders.stream()
                .flatMap(order -> order.products().stream())
                .collect(Collectors.groupingBy(Product::name, Collectors.counting()));

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        productNames.forEach(product -> {
            long count = productCounts.getOrDefault(product, 0L);
            series.getData().add(new XYChart.Data<>(product, count));
        });

        barChart.getData().clear();
        barChart.getData().add(series);
    }
}