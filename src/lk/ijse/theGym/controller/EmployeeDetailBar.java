package lk.ijse.theGym.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.theGym.model.EmployeeController;
import lk.ijse.theGym.to.Employee;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDetailBar {
    public Text txtEmployeeId;
    public Text txtName;
    public Text txtRole;
    public Text txtCity;
    public JFXButton btnDelete;
    public JFXButton btnUpdate;

    public void viewDataOnMouseClick(MouseEvent mouseEvent) throws IOException {
        EmployeeController.empID = txtEmployeeId.getText();
        Navigation.popupNavigation("ViewEmployeeDetailsFrom.fxml");

    }

    public void UpdateOnAction(ActionEvent actionEvent) throws IOException {
        EmployeeController.empID = txtEmployeeId.getText();
        Navigation.popupNavigation("UpdateEmployeeFrom.fxml");

    }

    public void deleteOnAction(ActionEvent actionEvent) {
        Alert alert=new Alert(Alert.AlertType.WARNING,"Are your sure", ButtonType.YES,ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult().equals(ButtonType.YES)){
            try {
                boolean isDelete = EmployeeController.RemoveEmployee(txtEmployeeId.getText());
                if (isDelete){
                    EmployeeFromController.getInstance().vBox.getChildren().clear();
                    EmployeeFromController.getInstance().loadAllId();
                }else {
                    System.out.println("Not deleted !");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }


    }

    public void setData(String ids) {
        System.out.println("setData");
        try {
            ResultSet set = EmployeeController.ScrollPaneLoadData(ids);
            if (set.next()) {
                txtEmployeeId.setText(set.getString(1));
                txtName.setText(set.getString(2) + " " + set.getString(3));
                txtCity.setText(set.getString(8));
                txtRole.setText(set.getString(6));

            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setData(Employee employee) {
        txtEmployeeId.setText(employee.getEmployee_id());
        txtName.setText(employee.getFist_name() + " " + employee.getLast_name());
        txtRole.setText(employee.getRoll());
        txtCity.setText(employee.getAddress_city());
    }

    public void mouseEntered(MouseEvent mouseEvent) {
        btnDelete.setVisible(true);
        btnUpdate.setVisible(true);
    }

    public void mouseExited(MouseEvent mouseEvent) {
        btnDelete.setVisible(false);
        btnUpdate.setVisible(false);
    }
}
