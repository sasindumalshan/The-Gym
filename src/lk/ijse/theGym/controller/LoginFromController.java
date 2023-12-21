package lk.ijse.theGym.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.theGym.util.CrudUtil;
import lk.ijse.theGym.util.Navigation;
import lk.ijse.theGym.util.Notification;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginFromController implements Initializable {


    public JFXTextField txtUserName;
    public JFXPasswordField txtPassword;
    public Text txtWarning;
    public JFXButton btn;


    public void loginOnAction(ActionEvent actionEvent) throws IOException {

        try {
            ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM employee WHERE usr_name=? AND password=?", txtUserName.getText(), txtPassword.getText());
            if (resultSet.next()) {
                String role = resultSet.getString(6);
                if (role.equals("Admin")) {
                    Navigation.swatchNavigation("AdminDashBordFrom.fxml", actionEvent);
                    Notification.notification("Login Successful  ", " your Login Successful ");
                }
                if (role.equals("Cashier")) {
                    Navigation.swatchNavigation("CashiarDashBordFrom.fxml", actionEvent);
                    Notification.notification("Login Successful  ", " your Login Successful ");
                }
            } else {
                txtWarning.setText("Check your Username Password");
                txtPassword.setText("");
                txtUserName.setText("");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void user(ActionEvent actionEvent) {
        txtPassword.requestFocus();
    }

    public void password(ActionEvent actionEvent) {
        btn.fire();
    }
}
