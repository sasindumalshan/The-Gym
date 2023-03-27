package lk.ijse.theGym.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lk.ijse.theGym.model.SupplierController;
import lk.ijse.theGym.util.Navigation;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewSupplierFromController implements Initializable {
    public Text txtSupplierId;
    public Text txtCompanyName;
    public Text txtLocation;
    public Text txtMobilNo;
    public Text txtMail;
    private static String id;

    public static void setId(String id) {
        ViewSupplierFromController.id = id;
    }

    public void closeOnAction(ActionEvent actionEvent) {
        Navigation.close(actionEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }

    private void setData() {
        try {
            ResultSet set=SupplierController.getSupplierDetails(id);
            if (set.next()){
                txtSupplierId.setText(set.getString(2));
                txtCompanyName.setText(set.getString(1));
                txtLocation.setText(set.getString(5));
                txtMail.setText(set.getString(3));
                txtMobilNo.setText(set.getString(4));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
