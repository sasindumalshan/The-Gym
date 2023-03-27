package lk.ijse.theGym.controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;

public class SupplierOrderDetailBarController {
    public Text txtOrderId;
    public Text txtCompanyName;
    public Text txtItemName;
    public Text txtPrice;
    public Text txtQut;

    public void setData(String orderId,String companyName,String itemName,String price,String qut) {
        txtOrderId.setText(orderId);
        txtCompanyName.setText(companyName);
        txtItemName.setText(itemName);
        txtPrice.setText(price);
        txtQut.setText(qut);
    }

    public void viewDataOnMouseClick(MouseEvent mouseEvent) throws IOException {
        ViewOrderDetailsFromController.setDetails(txtOrderId.getText(),txtCompanyName.getText(),txtQut.getText(),txtPrice.getText(),txtItemName.getText());
        Navigation.popupNavigation("ViewOrderDetailsFrom.fxml");
    }
}
