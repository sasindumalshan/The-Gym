package lk.ijse.theGym.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.theGym.model.ItemsController;
import lk.ijse.theGym.to.Items;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;
import java.sql.ResultSet;
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
        ItemsController.itemCode=txtItemCode.getText();
        Navigation.popupNavigation("UpdateItemFrom.fxml");

    }

    public void deleteOnAction(ActionEvent actionEvent) {
        try {
//            Alert alert=new Alert(Alert.AlertType.WARNING,"Are your sure Delete !", ButtonType.YES,ButtonType.NO);
//            alert.show();
//            alert.wait();
            Alert alert=new Alert(Alert.AlertType.WARNING,"Are your sure", ButtonType.YES,ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult().equals(ButtonType.YES)){

                boolean isDeleted=ItemsController.deleteItem(txtItemCode.getText());
                if (isDeleted){
                    StoreFromController.getInstance().vBox.getChildren().clear();
                    StoreFromController.getInstance().loadAllId();
                    StoreFromController.getInstance().setCountOfAllItems();
                    StoreFromController.getInstance().showLimitedStock();
                }else {

                }
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void viewDataOnMouseClick(MouseEvent mouseEvent) throws IOException {
        ItemsController.itemCode=txtItemCode.getText();
        Navigation.popupNavigation("ViewItemFrom.fxml");


    }

    public void setData(String ids) {

        try {
            ResultSet set = ItemsController.ScrollPaneLoadData(ids);
                 if (set.next()) {
                     txtItemCode.setText(set.getString(1));
                     txtItemName.setText(set.getString(2));
                     txtCategory.setText(set.getString(3));
                     txtQyt.setText(set.getString(4));
                     txtPrice.setText(set.getString(5));

                 }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setData(Items items) {
        txtItemCode.setText(items.getItem_id());
        txtItemName.setText(items.getItem_name());
        txtPrice.setText(String.valueOf(items.getPrice()));
        txtQyt.setText(String.valueOf(items.getQut()));
        txtCategory.setText(items.getCategory());
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
