package lk.ijse.theGym.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.theGym.controller.Admin.bar.ItemBarController;
import lk.ijse.theGym.controller.User.StoreFromController;
import lk.ijse.theGym.dto.ItemDTO;
import lk.ijse.theGym.model.ItemModel;
import lk.ijse.theGym.util.Navigation;
import lk.ijse.theGym.util.Notification;
import lk.ijse.theGym.util.RegexUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddItemFromController implements Initializable {
    public JFXTextField txtItemCode;
    public JFXTextField txtItemName;
    public JFXTextField txtPrice;
    public JFXTextField txtBrand;
    public JFXTextField txtDescription;
    public JFXComboBox combCategory;
    public JFXButton btnAdd;

    public void addOnAction(ActionEvent actionEvent) {
        ArrayList<String> reg = new ArrayList<>();
        if (txtItemName.getText().equals("") |
                txtPrice.getText().equals("") |
                txtBrand.getText().equals("") |
                combCategory.getValue() == null |
                txtItemCode.getText().equals("")) {

            Notification.notificationWARNING("pleas enter data", "empty column ");

        } else {
            try {
                ItemDTO itemDTO=new ItemDTO();
                itemDTO.setItem_id(txtItemCode.getText());
                itemDTO.setBrand(txtBrand.getText());
                itemDTO.setItem_name(txtItemName.getText());
                itemDTO.setCategory(String.valueOf(combCategory.getValue()));
                itemDTO.setPrice(Double.valueOf(txtPrice.getText()));
                itemDTO.setQut(0);
                itemDTO.setDescription( txtDescription.getText());

                boolean isItemAdded = ItemModel.save(itemDTO);
                if (isItemAdded) {
                    Navigation.close(actionEvent);
                    setData(itemDTO);
                    StoreFromController.getInstance().setCountOfAllItems();
                    StoreFromController.getInstance().showLimitedStock();
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void setData(ItemDTO itemDTO) {
        try {
            FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/bar/ItemBar.fxml"));
            Parent root = loader.load();
            ItemBarController controller = loader.getController();
            controller.setData(itemDTO);
            StoreFromController.getInstance().vBox.getChildren().add(root);
        } catch (IOException e) {
        }

    }

    private void setCombCategory() {
        String[] category = {"Supplement", "other", "Workout Items", "Vitamins"};
        combCategory.getItems().addAll(category);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCombCategory();
    }

    public void item(KeyEvent keyEvent) {

    }

    public void itemName(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtItemName, txtItemName.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white");
    }

    public void price(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtPrice, txtPrice.getText(), "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$", "-fx-text-fill: white");
    }

    public void brand(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtPrice, txtPrice.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white");
    }

    public void discription(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtDescription, txtDescription.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white");
    }

    public void colse(MouseEvent mouseEvent) {
        Navigation.close(mouseEvent);
    }
}
