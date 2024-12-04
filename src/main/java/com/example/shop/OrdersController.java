package com.example.shop;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class OrdersController {

    private final ObservableList<Order> orders = FXCollections.observableArrayList();
    private final ObservableList<Product> products = FXCollections.observableArrayList();
    private final ObservableList<Order.Status> statusList = FXCollections.observableArrayList(Order.Status.values());
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
    private ComboBox<Order.Status> statusComboBox;
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
    private Order selectedOrder;

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
        statusComboBox.setValue(Order.Status.New);

        ordersTable.getSelectionModel().selectedItemProperty().addListener((obs, oldOrder, newOrder) -> {
            if (newOrder != null) {
                showOrderDetails(newOrder);
            }
        });

        saveButton.setOnAction(event -> handleSave());
        cancelButton.setOnAction(event -> handleCancel());

        orders.setAll(OrdersDatabase.INSTANCE.data);
    }

    private void showOrderDetails(Order order) {
        selectedOrder = order;
        orderNumberLabel.setText(order.id().toString());
        products.setAll(order.products());
        statusComboBox.setValue(order.status());

        double totalSum = products.stream().mapToDouble(Product::sum).sum();
        totalAmountLabel.setText(String.format("%.2f", totalSum));
    }


    private void handleSave() {
        var selectedStatus = statusComboBox.getSelectionModel().getSelectedItem();
        var selectedOrder = this.selectedOrder;
        var newOrder = selectedOrder.copyWithNewStatus(selectedStatus);

        try {
            OrdersDatabase.INSTANCE.deleteEntry(selectedOrder);
            orders.remove(selectedOrder);
            OrdersDatabase.INSTANCE.appendEntry(newOrder);
            orders.add(newOrder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        statusComboBox.setDisable(true);
    }

    private void handleCancel() {
        statusComboBox.setDisable(true);
    }

    @FXML
    public void activateStatusComboBox() {
        statusComboBox.setDisable(false);
    }
}
