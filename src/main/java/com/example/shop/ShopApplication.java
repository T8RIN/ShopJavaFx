package com.example.shop;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.shop.Utils.openScene;

public class ShopApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        ApplicationHolder.INSTANCE.inject(this);
        stage.setTitle("Shop");
        openScene(stage, "login.fxml", 320, 240);
    }
}