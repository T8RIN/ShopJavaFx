package com.example.shop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.example.shop.Utils.openSceneWindowed;
import static com.example.shop.Utils.showMessage;

public class MainController implements Initializable {

    @FXML
    public Tab catalogTab;

    @FXML
    public Tab ordersTab;

    @FXML
    public TabPane tabPane;


    @FXML
    private void showCatalog() {
        tabPane.getSelectionModel().select(catalogTab);
    }

    @FXML
    private void showCatalogWindow() throws IOException {
        openSceneWindowed("catalog.fxml", "Catalog", 500, 500);
    }

    @FXML
    private void showOrders() {
        tabPane.getSelectionModel().select(ordersTab);
    }

    @FXML
    private void showOrdersWindow() throws IOException {
        openSceneWindowed("orders.fxml", "Orders", 500, 500);
    }


    @FXML
    private void showAbout() {
        showMessage("О программе", "Программа Учебная.  версия 1.0.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            var loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("catalog.fxml")));
            catalogTab.setContent(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            var loader = new FXMLLoader();
            loader.setLocation(Objects.requireNonNull(getClass().getResource("orders.fxml")));
            ordersTab.setContent(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
