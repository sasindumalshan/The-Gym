package lk.ijse.theGym.controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.theGym.model.EmployeeController;
import lk.ijse.theGym.model.SalaryController;
import lk.ijse.theGym.to.Employee;
import lk.ijse.theGym.util.Navigation;
import lk.ijse.theGym.util.Notification;
import lk.ijse.theGym.util.RegexUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddEmployeeFromController implements Initializable {

    private static String dateNow;
    public JFXTextField txtFName;
    public JFXTextField txtLName;
    public JFXTextField txtEmail;
    public JFXTextField txtNIC;
    public JFXTextField txtCity;
    public JFXTextField txtStreet;
    public JFXTextField txtLane;
    public JFXTextField txtMoNo;
    public JFXDatePicker dPBrithday;
    public JFXComboBox combRole;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXButton btnAdd;
    ArrayList<String> roles = new ArrayList<>();
    static String id = null;
    static String name = null;
    static String email = null;

    public void addOnAction(ActionEvent actionEvent) {
        id=getNextID();
        name= txtFName.getText()+" "+txtLName.getText();
        email=txtEmail.getText();
        if (txtFName.getText().equals("") |
                txtLName.getText().equals("") |
                txtEmail.getText().equals("") |
                txtMoNo.getText().equals("") |
                txtNIC.getText().equals("") |
                combRole.getValue() == null

        ) {
            Notification.notificationWARNING("Please Check again", "something wrong");
        } else {
            try {
                boolean isAdded = EmployeeController.addData(new Employee(
                        id,
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
                        dateNow(),
                        getSalaryId()
                ));
                if (isAdded) {
                    Navigation.close(actionEvent);
                    EmployeeFromController.getInstance().vBox.getChildren().clear();
                    EmployeeFromController.getInstance().loadAllId();
                    EmployeeFromController.getInstance().rBtnAllEmployee.setSelected(true);
                    EmployeeFromController.getInstance().rBtnAttendance.setSelected(false);
                    roles.clear();

//                    new Alert(Alert.AlertType.CONFIRMATION,"added !");
                    Notification.notification("Employee Add successful", "Employee added ");

//=================================================================================
                    try {
                        JasperReport jasperReport = JasperCompileManager.compileReport("C:\\My Aplication\\C4\\TheGym\\src\\lk\\ijse\\theGym\\view\\report\\busnescard.jrxml");
                        JRDataSource jrDataSource = new JRBeanCollectionDataSource(Arrays.asList(new Object()));

                        HashMap hm = new HashMap();
                        hm.put("id", id);
                        hm.put("name", name);
                        hm.put("email", email);

                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hm, jrDataSource);
                        JasperViewer.viewReport(jasperPrint, false);
//
                    } catch (JRException e) {
                        e.printStackTrace();
                    }


                } else {
                    System.out.println("not Add");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
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

    private String dateNow() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    public void getDateOnAction(ActionEvent actionEvent) {
        LocalDate date = dPBrithday.getValue();
        dateNow = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(dateNow);
    }

    private String getNextID() {
        String id = null;
        try {
            ResultSet set = EmployeeController.getLastId();
            while (set.next()) {
                id = set.getString(1);
            }
            try {
                String[] e = id.split("e00");
                int Id = Integer.parseInt(e[1]);
                Id++;
                return "e00" + Id;
            } catch (NullPointerException e) {
                return "e001";
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return "e001";

    }

    public void roleOnAction(ActionEvent actionEvent) {
        if (combRole.getValue().equals("Admin") | combRole.getValue().equals("Cashier")) {
            txtUserName.setVisible(true);
            txtPassword.setVisible(true);
        } else {
            txtUserName.setVisible(false);
            txtPassword.setVisible(false);
        }
    }

    private void setAllRole() {
        try {
            ResultSet set = SalaryController.getAllRoles();
            while (set.next()) {
                roles.add(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        combRole.getItems().addAll(roles);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtUserName.setVisible(false);
        txtPassword.setVisible(false);
        setAllRole();
    }
//==============================================================

    public void fistName(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtFName, txtFName.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white");

    }

    public void LastName(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtLName, txtLName.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white");
    }

    public void email(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtEmail, txtEmail.getText(), "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{3,4}$", "-fx-text-fill: white");
    }

    public void ctiy(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtCity, txtCity.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: black");
    }

    public void street(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtStreet, txtStreet.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: black");
    }

    public void lean(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtLane, txtLane.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: black");
    }

    public void mobile(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtMoNo, txtMoNo.getText(), "0((11)|(7(7|0|8|4|9|1|[3-7]))|(3[1-8])|(4(1|5|7))|(5(1|2|4|5|7))|(6(3|[5-7]))|([8-9]1))[0-9]{7}", "-fx-text-fill: white");

    }

    public void userName(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtUserName, txtUserName.getText(), "^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{0,29}$", "-fx-text-fill: white");
    }

    public void password(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtPassword, txtPassword.getText(), "^(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$", "-fx-text-fill: white");

    }

    public void close(MouseEvent mouseEvent) {
        Navigation.close(mouseEvent);
    }

    public void nic(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtNIC, txtNIC.getText(), "^(?:19|20)?\\d{2}[0-9]{10}|[0-9]{9}[x|X|v|V]$", "-fx-text-fill: white");

    }
}
