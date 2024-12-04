package com.example.shop;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.shop.Utils.openScene;
import static com.example.shop.Utils.showErrorMessage;

public class RegistrationController {

    @FXML
    private TextField textFieldLogin;
    @FXML
    private TextField textFieldPassword;

    @FXML
    private void handleRegister() {
        String login = textFieldLogin.getText();
        String password = textFieldPassword.getText();

        if (login.isBlank() || password.isBlank()) {
            showErrorMessage("Зполните оба поля");
        } else if (!UserDatabase.INSTANCE.appendEntry(new User(login, password))) {
            showErrorMessage("Этот логин уже существует.");
        } else {
            goBackToLoginForm();
        }
    }

    private void goBackToLoginForm() {
        try {
            openScene((Stage) textFieldLogin.getScene().getWindow(), "login.fxml", 320, 240);
        } catch (IOException ignored) {
        }
    }
}