package com.example.shop;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.shop.Utils.showErrorMessage;
import static com.example.shop.Utils.showMessage;

public class ProductEditController {
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;

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
            showErrorMessage(Strings.AllFieldShouldBeFilled);
            return;
        }

        try {
            Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            showErrorMessage(Strings.QuantityMustBeNumber);
            return;
        }

        try {
            Double.parseDouble(price);
        } catch (NumberFormatException e) {
            showErrorMessage(Strings.CostMustBeNumber);
            return;
        }

        var product = new Product(name, Integer.parseInt(id), Integer.parseInt(quantity), Double.parseDouble(price));
        if (saveListener.save(product)) {
            showMessage(Strings.Saved, product.toString());

            handleCancel();
        } else {
            showErrorMessage(Strings.SomethingWentWrong);
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
