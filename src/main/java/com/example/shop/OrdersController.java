package com.example.shop;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class OrdersController {

    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> orderIdColumn;
    @FXML
    private TableColumn<Order, String> orderDateColumn;
    @FXML
    private TableColumn<Order, String> orderLoginColumn;

    @FXML
    private Label orderNumberLabel;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, String> productCountColumn;
    @FXML
    private TableColumn<Product, String> productSumColumn;
    @FXML
    private Label totalAmountLabel;

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private final ObservableList<Order> orders = FXCollections.observableArrayList();
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final ObservableList<String> statusList = FXCollections.observableArrayList("Новый", "Доставлено", "Оплачено");

    @FXML
    private void initialize() {
        statusComboBox.setDisable(true);

        orderIdColumn.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().id().toString())
        );
        orderDateColumn.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().date())
        );
        orderLoginColumn.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().login())
        );
        ordersTable.setItems(orders);

        productNameColumn.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().name())
        );
        productCountColumn.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().count().toString())
        );
        productSumColumn.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().sum().toString())
        );
        productsTable.setItems(products);

        statusComboBox.setItems(statusList);
        statusComboBox.setValue("Новый");

        ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldOrder, newOrder) -> {
            if (newOrder != null) {
                showOrderDetails(newOrder);
            }
        });

        saveButton.setOnAction(event -> handleSave());
        cancelButton.setOnAction(event -> handleCancel());

        loadOrders();
    }

    private void loadOrders() {
        orders.setAll(OrdersDatabase.INSTANCE.data);
    }

    private void showOrderDetails(Order order) {
        orderNumberLabel.setText(order.id().toString());
        products.setAll(order.products());
        statusComboBox.setDisable(false);

        double totalSum = products.stream().mapToDouble(Product::sum).sum();
        totalAmountLabel.setText(String.format("%.2f", totalSum));
    }

    private void handleSave() {
        System.out.println("Сохранение изменений...");
    }

    private void handleCancel() {
        System.out.println("Отмена изменений...");
    }

}
