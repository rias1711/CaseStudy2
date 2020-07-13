package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerSignIn implements Initializable {

    public TextField username;
    public PasswordField password;

    public void logIn(ActionEvent actionEvent) throws IOException {
        String user = username.getText();
        String pass = password.getText();
        if (!(user.equals("admin")) || !(pass.equals("admin"))) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Wrong");
            alert.setHeaderText("Wrong username or password");
            alert.setContentText("Please re-type");
            alert.show();
        } else {
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/students.fxml"));
            Scene sceneMenu = new Scene(root);
            primaryStage.setTitle("Menu");
            primaryStage.setScene(sceneMenu);
            primaryStage.show();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void cancelLogIn(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.close();
    }


    public void forgotPassword(ActionEvent actionEvent) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Find Password");
        alert.setHeaderText("Here your password");
        alert.setContentText("Your password is admin");
        alert.show();
    }

}
