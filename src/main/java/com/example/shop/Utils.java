package com.example.shop;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Utils {
    static void setIcon(Stage stage, String iconLocation) {
        stage.getIcons().add(new Image(Objects.requireNonNull(ApplicationHolder.INSTANCE.getResource(iconLocation)).toString()));
    }

    static void setDefaultIcon(Stage stage) {
        setIcon(stage, "logo.png");
    }

    static void openScene(Stage stage, String sceneLocation) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationHolder.INSTANCE.getResource(sceneLocation));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        setDefaultIcon(stage);
        stage.show();
    }

    interface OnControllerReadyListener<T> {
        void onControllerReady(T controller);
    }

    static <T> void openModalScene(String sceneLocation, OnControllerReadyListener<T> onControllerReadyListener) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Edit Product");
        FXMLLoader loader = new FXMLLoader(ApplicationHolder.INSTANCE.getResource(sceneLocation));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        setDefaultIcon(stage);

        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(null);

        onControllerReadyListener.onControllerReady(loader.getController());

        stage.showAndWait();
    }

    static void openScene(Stage stage, String sceneLocation, int width, int height) throws IOException {
        FXMLLoader loader = new FXMLLoader(ApplicationHolder.INSTANCE.getResource(sceneLocation));
        Parent root = loader.load();
        stage.setScene(new Scene(root, width, height));
        setDefaultIcon(stage);
        stage.show();
    }

    static void showErrorMessage(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}