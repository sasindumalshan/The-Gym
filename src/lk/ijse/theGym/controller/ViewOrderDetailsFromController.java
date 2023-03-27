package lk.ijse.theGym.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import lk.ijse.theGym.model.SupplierOrderDetailsController;
import lk.ijse.theGym.util.Navigation;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewOrderDetailsFromController implements Initializable {
    public Text txtOrderId;
    public Text txtItemName;
    public Text txtPrice;
    public Text txtQut;
    public Text txtDate;
    public Text txtItemId;
    public Text txtSupplierId;
    public Text txtCompanyName;
    private static String OrderId;
    private static String CompanyName;
    private static String qut;
    private static String price;
    private static String itemName;

    public static void setDetails(String OrderId, String CompanyName, String qut, String price,String itemName) {
        ViewOrderDetailsFromController.OrderId = OrderId;
        ViewOrderDetailsFromController.CompanyName = CompanyName;
        ViewOrderDetailsFromController.qut = qut;
        ViewOrderDetailsFromController.price = price;
        ViewOrderDetailsFromController.itemName=itemName;
    }

    public void closeOnAction(ActionEvent actionEvent) {
        Navigation.close(actionEvent);
    }

    private void setDetails() {
        try {
            ResultSet set = SupplierOrderDetailsController.getDetails(OrderId);
            if (set.next()) {
//                txtCompanyName.setText(CompanyName);
//                txtDate.setText(set.getString(5));
//                txtOrderId.setText(set.getString(1));
//                txtItemId.setText(set.getString(3));
//                txtSupplierId.setText(set.getString(2));
//                txtPrice.setText(set.getString(6));
//                txtItemName.setText(itemName);
//                txtQut.setText(set.getString(4));

            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDetails();
    }
}
