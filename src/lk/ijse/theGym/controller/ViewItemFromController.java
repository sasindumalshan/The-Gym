package lk.ijse.theGym.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lk.ijse.theGym.model.ItemsController;
import lk.ijse.theGym.util.Navigation;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewItemFromController implements Initializable {

    public Text txtItemCode;
    public Text txtCategory;
    public Text txtName;
    public Text txtPrice;
    public Text txtBrand;
    public Text txtDescription;

    public void setData() {
        try {
            ResultSet set = ItemsController.viewAllItemDetails();
            if (set.next()) {
                System.out.println(set.getString(1));
                txtItemCode.setText(set.getString(1));
                txtPrice.setText(set.getString(5));
                txtBrand.setText(set.getString(6));
                txtName.setText(set.getString(2));
                txtCategory.setText(set.getString(3));
                txtDescription.setText(set.getString(7));

            }else {
                System.out.println("null");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeOnAction(ActionEvent actionEvent) {
        Navigation.close(actionEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }
}
