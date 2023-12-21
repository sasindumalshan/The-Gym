package lk.ijse.theGym.controller.Admin.bar;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.theGym.controller.Admin.UpdateItemFromController;
import lk.ijse.theGym.controller.Admin.ViewItemFromController;
import lk.ijse.theGym.controller.User.StoreFromController;
import lk.ijse.theGym.dto.ItemDTO;
import lk.ijse.theGym.model.ItemModel;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;
import java.sql.SQLException;

public class ItemBarController {
    public Text txtItemCode;
    public Text txtItemName;
    public Text txtPrice;
    public Text txtQyt;
    public Text txtCategory;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;


    public void UpdateOnAction(ActionEvent actionEvent) throws IOException {
        UpdateItemFromController.itemCode = txtItemCode.getText();
        Navigation.popupNavigation("UpdateItemFrom.fxml");

    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
//            Alert alert=new Alert(Alert.AlertType.WARNING,"Are your sure Delete !", ButtonType.YES,ButtonType.NO);
//            alert.show();
//            alert.wait();
            Alert alert = new Alert(Alert.AlertType.WARNING, "Are your sure", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.YES)) {

                boolean isDeleted = ItemModel.delete(txtItemCode.getText());
                if (isDeleted) {
                    StoreFromController.getInstance().vBox.getChildren().clear();
                    StoreFromController.getInstance().loadAllId();
                    StoreFromController.getInstance().setCountOfAllItems();
                    StoreFromController.getInstance().showLimitedStock();
                } else {

                }
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void viewDataOnMouseClick(MouseEvent mouseEvent) throws IOException {
        ViewItemFromController.itemCode = txtItemCode.getText();
        Navigation.popupNavigation("ViewItemFrom.fxml");


    }

    public void setData(String ids) {
        try {
            ItemDTO itemDTO = ItemModel.findById(ids);
            txtItemCode.setText(itemDTO.getItem_id());
            txtItemName.setText(itemDTO.getItem_name());
            txtCategory.setText(itemDTO.getCategory());
            txtQyt.setText(String.valueOf(itemDTO.getQut()));
            txtPrice.setText(String.valueOf(itemDTO.getPrice()));

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setData(ItemDTO itemDTO) {
        txtItemCode.setText(itemDTO.getItem_id());
        txtItemName.setText(itemDTO.getItem_name());
        txtPrice.setText(String.valueOf(itemDTO.getPrice()));
        txtQyt.setText(String.valueOf(itemDTO.getQut()));
        txtCategory.setText(itemDTO.getCategory());
    }

    public void onMuseEntered(MouseEvent mouseEvent) {
        btnUpdate.setVisible(true);
        btnDelete.setVisible(true);
    }

    public void onMuseExitered(MouseEvent mouseEvent) {
        btnUpdate.setVisible(false);
        btnDelete.setVisible(false);
    }
}
