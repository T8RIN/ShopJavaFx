package com.example.shop;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.shop.Utils.openScene;

public class LoginController {
    @FXML
    private TextField textFieldLogin;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private Label label;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private void handleRegisterLink() throws IOException {
        openScene((Stage) registerLink.getScene().getWindow(), "registration.fxml", 320, 240);
    }

    @FXML
    private void handleButtonAction() {
        String login = textFieldLogin.getText();
        String password = textFieldPassword.getText();
        User user = new User(login.strip(), password.strip());

        if (UserDatabase.INSTANCE.isEntryExists(user)) {
            label.setText("Вход успешен");
            if (login.equals("admin")) {
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        Platform.runLater(
                                () -> {
                                    try {
                                        openScene((Stage) registerLink.getScene().getWindow(), "main.fxml");
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        } else {
            label.setText("Неверный логин или пароль");
        }
    }
}