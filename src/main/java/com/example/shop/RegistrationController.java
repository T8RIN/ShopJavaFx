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
    private void handleRegister() throws IOException {
        String login = textFieldLogin.getText();
        String password = textFieldPassword.getText();

        if (login.isBlank() || password.isBlank()) {
            showErrorMessage(Strings.AllFieldShouldBeFilled);
        } else if (!UserDatabase.INSTANCE.appendEntry(new User(login, password))) {
            showErrorMessage(Strings.LoginAlreadyExits);
        } else {
            goBackToLoginForm();
        }
    }

    private void goBackToLoginForm() throws IOException {
        openScene((Stage) textFieldLogin.getScene().getWindow(), Routes.Login, 320, 240);
    }
}