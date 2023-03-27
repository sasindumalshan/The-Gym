package lk.ijse.theGym.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.theGym.model.ItemsController;
import lk.ijse.theGym.to.Items;
import lk.ijse.theGym.util.Navigation;
import lk.ijse.theGym.util.Notification;
import lk.ijse.theGym.util.RegexUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateItemFromController implements Initializable {
    public JFXTextField txtItemName;
    public JFXTextField txtPrice;
    public JFXTextField txtBrand;
    public JFXTextField txtDescription;
    public JFXComboBox combCategory;
    public Text txtItemCode;
    public JFXButton btnUpdate;

    public void updateOnAction(ActionEvent actionEvent) {
        if (txtItemName.getText().equals("") |
                txtPrice.getText().equals("") |
                txtBrand.getText().equals("") |
                combCategory.getValue() == null |
                txtItemCode.getText().equals("")
        ){
            Notification.notificationWARNING("pleas enter data","empty column ");
        }else {
            try {
                Items items = new Items(txtItemCode.getText(), txtItemName.getText(), String.valueOf(combCategory.getValue()), 0, Double.valueOf(txtPrice.getText()), txtBrand.getText(), txtDescription.getText());
                boolean isUpdated = ItemsController.updateItem(items);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Updated !");
                    Navigation.close(actionEvent);

                    StoreFromController.getInstance().vBox.getChildren().clear();
                    StoreFromController.getInstance().loadAllId();

                } else {
                    System.out.println("not add");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void setData() {
        try {
            ResultSet set = ItemsController.viewAllItemDetails();
            if (set.next()) {
                txtItemCode.setText(set.getString(1));
                txtPrice.setText(set.getString(5));
                txtBrand.setText(set.getString(6));
                txtItemName.setText(set.getString(2));
                txtDescription.setText(set.getString(7));
                combCategory.getItems().addAll(set.getString(3));
            } else {
                System.out.println("null");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setCombCategory() {
        String[] category = {"Supplement", "other", "Workout Items", "Vitamins"};
        combCategory.getItems().addAll(category);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCombCategory();
        setData();
    }

    public void itemName(KeyEvent keyEvent) {
        RegexUtil.regex(btnUpdate, txtItemName, txtItemName.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white");

    }

    public void price(KeyEvent keyEvent) {
        RegexUtil.regex(btnUpdate, txtPrice, txtPrice.getText(), "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$", "-fx-text-fill: white");

    }

    public void brand(KeyEvent keyEvent) {
        RegexUtil.regex(btnUpdate, txtBrand, txtBrand.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white");
    }

    public void disription(KeyEvent keyEvent) {
        RegexUtil.regex(btnUpdate, txtBrand, txtBrand.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white");

    }

    public void colse(MouseEvent mouseEvent) {
        Navigation.close(mouseEvent);
    }
}
