package lk.ijse.theGym.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.theGym.controller.User.StoreFromController;
import lk.ijse.theGym.dto.ItemDTO;
import lk.ijse.theGym.model.ItemModel;
import lk.ijse.theGym.to.Items;
import lk.ijse.theGym.util.Navigation;
import lk.ijse.theGym.util.Notification;
import lk.ijse.theGym.util.RegexUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateItemFromController implements Initializable {
    public static String itemCode;
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
        ) {
            Notification.notificationWARNING("pleas enter data", "empty column ");
        } else {
            try {
                ItemDTO itemDTO=ItemModel.findById(txtItemCode.getText());
                itemDTO.setItem_name( txtItemName.getText());
                itemDTO.setCategory(String.valueOf(combCategory.getValue()));
                itemDTO.setPrice( Double.valueOf(txtPrice.getText()));
                itemDTO.setBrand(txtBrand.getText());
                itemDTO.setDescription(txtDescription.getText());

                boolean isUpdated = ItemModel.update(itemDTO);
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
            ItemDTO itemDTO = ItemModel.findById(itemCode);
            txtItemCode.setText(itemDTO.getItem_id());
            txtPrice.setText(String.valueOf(itemDTO.getPrice()));
            txtBrand.setText(itemDTO.getBrand());
            txtItemName.setText(itemDTO.getItem_name());
            txtDescription.setText(itemDTO.getDescription());
            combCategory.getItems().addAll(itemDTO.getCategory());
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
