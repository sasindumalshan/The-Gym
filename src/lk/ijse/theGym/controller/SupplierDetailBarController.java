package lk.ijse.theGym.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.theGym.model.SupplierController;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;
import java.sql.SQLException;

public class SupplierDetailBarController {
    public Text txtSupplierId;
    public Text txtCompanyName;
    public Text txtLocation;
    public Text txtContactNo;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;

    public void setData(String supplier_id, String company_name, String location, String mobile_no) {
        txtCompanyName.setText(company_name);
        txtSupplierId.setText(supplier_id);
        txtLocation.setText(location);
        txtContactNo.setText(mobile_no);
    }

    public void deleteOnAction(ActionEvent actionEvent) {

        Alert alert=new Alert(Alert.AlertType.WARNING,"Are your sure", ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.YES)){
            try {
                boolean isDeleted = SupplierController.removeSupplier(txtSupplierId.getText());
                if (isDeleted) {
                    SupplierFromController.getInstance().setAllSuppliersCount();
                    SupplierFromController.getInstance().setAllIdForLoadAllSupplier();
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public void UpdateOnAction(ActionEvent actionEvent) throws IOException {
        UpdateSupplierFromController.setId(txtSupplierId.getText());
        Navigation.popupNavigation("UpdateSupplierFrom.fxml");

    }

    public void viewDataOnMouseClick(MouseEvent mouseEvent) throws IOException {
        ViewSupplierFromController.setId(txtSupplierId.getText());
        Navigation.popupNavigation("ViewSupplierFrom.fxml");
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        btnDelete.setVisible(true);
        btnUpdate.setVisible(true);
    }

    public void mouseExitered(MouseEvent mouseEvent) {
        btnDelete.setVisible(false);
        btnUpdate.setVisible(false);
    }
}
