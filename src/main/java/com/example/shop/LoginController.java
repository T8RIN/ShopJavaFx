package com.example.shop;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.shop.Utils.openScene;
import static com.example.shop.Utils.runCatching;

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
        openScene((Stage) registerLink.getScene().getWindow(), Routes.Registration, 320, 240);
    }

    @FXML
    private void handleButtonAction() {
        String login = textFieldLogin.getText();
        String password = textFieldPassword.getText();
        User user = new User(login.strip(), password.strip());

        if (UserDatabase.INSTANCE.isEntryExists(user)) {
            label.setText(Strings.LoginSuccess);
            if (login.equals(Strings.AdminLogin)) {
                new Thread(() -> runCatching(
                        () -> {
                            Thread.sleep(1000);
                            Platform.runLater(
                                    () -> runCatching(
                                            () -> openScene((Stage) registerLink.getScene().getWindow(), Routes.Main)
                                    )
                            );
                        }
                )).start();
            }
        } else {
            label.setText(Strings.LoginFailure);
        }
    }
}