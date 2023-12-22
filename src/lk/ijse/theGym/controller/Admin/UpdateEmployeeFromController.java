package lk.ijse.theGym.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.theGym.modelController.EmployeeController;
import lk.ijse.theGym.modelController.SalaryController;
import lk.ijse.theGym.to.Employee;
import lk.ijse.theGym.util.Navigation;
import lk.ijse.theGym.util.Notification;
import lk.ijse.theGym.util.RegexUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdateEmployeeFromController implements Initializable {
    public JFXTextField txtFName;
    public JFXTextField txtLName;
    public JFXTextField txtEmail;
    public JFXTextField txtNIC;
    public JFXTextField txtCity;
    public JFXTextField txtStreet;
    public JFXTextField txtLane;
    public JFXTextField txtMoNo;
    public JFXDatePicker DpBirthday;
    public JFXComboBox combRole;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXButton btn;
    ArrayList<String> roles=new ArrayList<>();
    private static String dateNow;
    public void updateOnAction(ActionEvent actionEvent) {
        if (txtFName.getText().equals("")|
                txtLName.getText().equals("")|
                txtEmail.getText().equals("")|
                txtMoNo.getText().equals("")|
                txtNIC.getText().equals("")|
                combRole.getValue()==null){
            Notification.notificationWARNING("Please Check again","something wrong");

        }else {
            try {
                boolean isUpdate=EmployeeController.Update(new Employee(
                        "",
                        txtFName.getText(),
                        txtLName.getText(),
                        txtUserName.getText(),
                        txtPassword.getText(),
                        String.valueOf(combRole.getValue()),
                        txtStreet.getText(),
                        txtCity.getText(),
                        txtLane.getText(),
                        txtEmail.getText(),
                        dateNow,
                        txtNIC.getText(),
                        txtMoNo.getText(),
                        "",
                        getSalaryId()

                ));
                if (isUpdate){
                    EmployeeFromController.getInstance().vBox.getChildren().clear();
                    EmployeeFromController.getInstance().loadAllId();
                    Navigation.close(actionEvent);
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    public void roleOnAction(ActionEvent actionEvent) {
        if(combRole.getValue().equals("Admin")|combRole.getValue().equals("Cashier")){
            txtUserName.setVisible(true);
            txtPassword.setVisible(true);
        }else {
            txtUserName.setVisible(false);
            txtPassword.setVisible(false);
        }
    }
    private void setAllRole() {
        try {
            ResultSet set= SalaryController.getAllRoles();
            while (set.next()){
                roles.add(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        combRole.getItems().addAll(roles);
    }

    public void BirthdayOnAction(ActionEvent actionEvent) {
        LocalDate date = DpBirthday.getValue();
        dateNow = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(dateNow);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAllRole();
        setData();
    }

    private void setData() {
        try {
            ResultSet set = EmployeeController.getAllData();
            if (set.next()){
                txtFName.setText(set.getString(2));
                txtLName.setText(set.getString(3));
                txtUserName.setText(set.getString(4));
                txtPassword.setText(set.getString(5));
                combRole.setValue(set.getString(6));
                txtStreet.setText(set.getString(7));
                txtCity.setText(set.getString(8));
                txtLane.setText(set.getString(9));
                txtEmail.setText(set.getString(10));
                DpBirthday.setId(set.getString(11));
                txtNIC.setText(set.getString(12));
                txtMoNo.setText(set.getString(13));

            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    private String getSalaryId() {

        try {
            ResultSet set = SalaryController.findSalaryId((String) combRole.getValue());
            if (set.next()) {
                return set.getString(1);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void close(MouseEvent mouseEvent) {
        Navigation.close(mouseEvent);
    }

    public void userName(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtUserName, txtUserName.getText(), "^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{0,29}$", "-fx-text-fill: white");

    }

    public void password(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtPassword, txtPassword.getText(), "^(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$", "-fx-text-fill: white");

    }

    public void fistName(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtFName, txtFName.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white");

    }

    public void LastName(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtLName, txtLName.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white");

    }

    public void email(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtEmail, txtEmail.getText(), "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{3,4}$", "-fx-text-fill: white");

    }

    public void nic(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtNIC, txtNIC.getText(), "^(?:19|20)?\\d{2}[0-9]{10}|[0-9]{9}[x|X|v|V]$", "-fx-text-fill: white");

    }

    public void ctiy(KeyEvent keyEvent){
        RegexUtil.regex(btn, txtCity, txtCity.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: black");

    }


    public void street(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtStreet, txtStreet.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: black");

    }

    public void lean(KeyEvent keyEvent) {
        RegexUtil.regex(btn, txtLane, txtLane.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: black");

    }

    public void mobil(KeyEvent keyEvent) {
        RegexUtil.regex(btn,txtMoNo, txtMoNo.getText(), "0((11)|(7(7|0|8|4|9|1|[3-7]))|(3[1-8])|(4(1|5|7))|(5(1|2|4|5|7))|(6(3|[5-7]))|([8-9]1))[0-9]{7}", "-fx-text-fill: white");

    }
}
