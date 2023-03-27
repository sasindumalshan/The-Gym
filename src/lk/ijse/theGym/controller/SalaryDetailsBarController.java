package lk.ijse.theGym.controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SalaryDetailsBarController {
    public Text txtRole;
    public Text txtSalary;
    public Text txtId;
    public Text txtUsage;
    public void setData(String id,String salary,String role,String usage){
        txtId.setText(id);
        txtSalary.setText(salary);
        txtRole.setText(role);
        txtUsage.setText(usage);
    }

    public void clickOnAction(MouseEvent mouseEvent) {
        System.out.println("click");
        SalaryFromController.getController().setData(txtId.getText(),txtSalary.getText());

    }
}
