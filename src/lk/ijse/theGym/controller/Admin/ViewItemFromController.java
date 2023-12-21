package lk.ijse.theGym.controller.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lk.ijse.theGym.dto.ItemDTO;
import lk.ijse.theGym.model.ItemModel;
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

    public static String itemCode;

    public void setData() {
        try {
            ItemDTO itemDTO = ItemModel.findById(itemCode);
                txtItemCode.setText(itemDTO.getItem_id());
                txtPrice.setText(String.valueOf(itemDTO.getPrice()));
                txtBrand.setText(itemDTO.getBrand());
                txtName.setText(itemDTO.getItem_name());
                txtCategory.setText(itemDTO.getCategory());
                txtDescription.setText(itemDTO.getDescription());
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
