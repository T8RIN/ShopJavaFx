package com.example.shop;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.shop.Utils.showErrorMessage;

public class ProductEditController {
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    private SaveListener saveListener;

    void setSaveListener(SaveListener listener) {
        saveListener = listener;
    }

    @FXML
    private void handleOk() throws IOException {
        String id = idField.getText();
        String name = nameField.getText();
        String quantity = quantityField.getText();
        String price = priceField.getText();

        if (id.isEmpty() || name.isEmpty() || quantity.isEmpty() || price.isEmpty()) {
            showErrorMessage("Все поля должны быть заполнены.");
            return;
        }

        try {
            Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            showErrorMessage("Количество должно быть числом.");
            return;
        }

        try {
            Double.parseDouble(price);
        } catch (NumberFormatException e) {
            showErrorMessage("Стоимость должна быть числом.");
            return;
        }

        if (saveListener.save(
                new Product(name, Integer.parseInt(id), Integer.parseInt(quantity), Double.parseDouble(price))
        )) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Данные сохранены");
            alert.setHeaderText(null);
            alert.setContentText("Артикул: " + id + "\nНаименование: " + name + "\nКоличество: " + quantity + "\nСтоимость: " + price);
            alert.showAndWait();

            handleCancel();
        } else {
            showErrorMessage("Что-то пошло не так");
        }
    }

    @FXML
    private void handleCancel() {
        idField.clear();
        nameField.clear();
        quantityField.clear();
        priceField.clear();

        ((Stage) idField.getScene().getWindow()).close();
    }

    public void setProduct(Product product) {
        idField.setText(product.id().toString());
        nameField.setText(product.name());
        priceField.setText(product.sum().toString());
        quantityField.setText(product.count().toString());
    }

    interface SaveListener {
        boolean save(Product product) throws IOException;
    }
}
