package lk.ijse.theGym.controller.User.bar;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.theGym.dto.projection.EmployeeAttendanceProjection;
import lk.ijse.theGym.modelController.EmployeeController;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;

public class EmployeeAttendanceBar {

    public Text txtEmployeeId;
    public Text txtName;
    public Text txtRole;
    public Text txtdate;
    public Text txtTime;

    public void viewDataOnMouseClick(MouseEvent mouseEvent) throws IOException {
        EmployeeController.empID = txtEmployeeId.getText();
        Navigation.popupNavigation("ViewEmployeeDetailsFrom.fxml");
    }

    public void setData(EmployeeAttendanceProjection projection) {

        txtEmployeeId.setText(projection.getId());
        txtName.setText(projection.getFistName()+" "+projection.getLastName());
        txtRole.setText(projection.getRoll());
        txtdate.setText(projection.getAttendanceDate());
        txtTime.setText(projection.getAttendanceTime());
    }
}
