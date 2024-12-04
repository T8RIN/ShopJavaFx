package com.example.shop;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.shop.Utils.openModalScene;
import static com.example.shop.Utils.showErrorMessage;

public class CatalogController implements Initializable {
    private final ObservableList<Product> productData = FXCollections.observableArrayList();
    @FXML
    private TableView<Product> catalogTable;
    @FXML
    private TableColumn<Product, String> productId;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, String> productSum;
    @FXML
    private TableColumn<Product, String> productCount;
    @FXML
    private Label productIdLabel;
    @FXML
    private Label productNameLabel;
    @FXML
    private Label productSumLabel;
    @FXML
    private Label productCountLabel;

    private void showProductDetails(Product product) {
        if (product != null) {
            productIdLabel.setText(product.id().toString());
            productNameLabel.setText(product.name());
            productSumLabel.setText(product.sum().toString());
            productCountLabel.setText(product.count().toString());
        } else {
            productIdLabel.setText("");
            productNameLabel.setText("");
            productSumLabel.setText("");
            productCountLabel.setText("");
        }
    }

    @FXML
    private void deleteProduct() throws IOException {
        int selectedIndex = catalogTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            deleteProductImpl(productData.get(selectedIndex));
        } else {
            showErrorMessage("Выберите товар в таблице");
        }
    }

    private boolean deleteProductImpl(Product product) throws IOException {
        productData.remove(product);
        return ProductDatabase.INSTANCE.deleteEntry(product);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productData.addAll(ProductDatabase.INSTANCE.data);
        productId.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().id().toString())
        );
        productName.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().name())
        );
        productSum.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().sum().toString())
        );
        productCount.setCellValueFactory(
                p -> new SimpleStringProperty(p.getValue().count().toString())
        );
        catalogTable.setItems(productData);

        showProductDetails(null);

        catalogTable.getSelectionModel().selectedItemProperty().addListener(
                ((observableValue, product, newValue) -> showProductDetails(newValue))
        );
    }

    @FXML
    private void addProduct() throws IOException {
        openModalScene(
                "product-edit.fxml",
                (Utils.OnControllerReadyListener<ProductEditController>) controller -> {
                    controller.setSaveListener(
                            product -> {
                                ProductDatabase.INSTANCE.appendEntry(product);
                                productData.add(product);
                                return true;
                            }
                    );
                }
        );
    }

    @FXML
    private void editProduct() throws IOException {
        int selectedIndex = catalogTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            var selectedProduct = productData.get(selectedIndex);
            openModalScene(
                    "product-edit.fxml",
                    (Utils.OnControllerReadyListener<ProductEditController>) controller -> {
                        controller.setProduct(selectedProduct);
                        controller.setSaveListener(
                                product -> {
                                    ProductDatabase.INSTANCE.appendEntry(product);
                                    productData.add(product);
                                    return deleteProductImpl(selectedProduct);
                                }
                        );
                    }
            );
        } else {
            showErrorMessage("Выберите товар в таблице");
        }
    }
}
