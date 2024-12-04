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

import static com.example.shop.Utils.*;

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
        openSceneWindowed(Routes.Catalog, Strings.Catalog, 500, 500);
    }

    @FXML
    private void showOrders() {
        tabPane.getSelectionModel().select(ordersTab);
    }

    @FXML
    private void showStatistics() throws IOException {
        openModalScene(Routes.Stats, Strings.Stats, null);
    }


    @FXML
    private void showAbout() {
        showMessage(Strings.AboutApp, Strings.AboutAppSub);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        runCatching(
                () -> {
                    var loader = new FXMLLoader();
                    loader.setLocation(Objects.requireNonNull(getClass().getResource(Routes.Catalog)));
                    catalogTab.setContent(loader.load());
                }
        );
        runCatching(
                () -> {
                    var loader = new FXMLLoader();
                    loader.setLocation(Objects.requireNonNull(getClass().getResource(Routes.Catalog)));
                    catalogTab.setContent(loader.load());
                }
        );
        runCatching(
                () -> {
                    var loader = new FXMLLoader();
                    loader.setLocation(Objects.requireNonNull(getClass().getResource(Routes.Orders)));
                    ordersTab.setContent(loader.load());
                }
        );
    }
}
