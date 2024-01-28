package lk.ijse.theGym.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.theGym.controller.User.StoreFromController;
import lk.ijse.theGym.dto.CoachSalaryDetailsDTO;
import lk.ijse.theGym.dto.EmployeeSalaryDetailsDTO;
import lk.ijse.theGym.dto.SalaryDTO;
import lk.ijse.theGym.model.*;
import lk.ijse.theGym.modelController.CoachController;
import lk.ijse.theGym.model.CoachSalaryDetailsModel;
import lk.ijse.theGym.modelController.EmployeeController;
import lk.ijse.theGym.util.DateTimeUtil;
import lk.ijse.theGym.util.Notification;
import lk.ijse.theGym.util.RegexUtil;
import lk.ijse.theGym.view.data.SalaryTm;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SalaryFromController implements Initializable {
    public static String clickId;
    public static String salaryPrice;
    private static String id;
    private static String salaryId;
    private static SalaryFromController controller;
    public AnchorPane anchorPane;
    public VBox Vbox;
    public Text txtId;
    public JFXRadioButton salary;
    public JFXRadioButton salaryDetails;
    public Pane addSalary;
    public JFXTextField lblPrice;
    public JFXTextField txtOtherRole;
    public JFXButton btnAdd;
    public JFXComboBox comboRole;
    public Pane givenSalary;
    public JFXComboBox comboId;
    public Text txtName;
    public Text txtNic;
    public Text txtAttendance;
    public Text txtAvailableSalary;
    public Text txtPerMonthSalary;
    public JFXButton btnAdd1;
    public Text availableRole;
    public Text txtSalary;
    public Text usage;
    public Pane otherRole;
    public JFXComboBox comboMonth;
    public JFXComboBox comboYear;
    public Text txtIdSearch;
    public Pane paneSalaryId;
    String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};

    public SalaryFromController() {
        controller = this;
    }

    public static SalaryFromController getController() {
        return controller;
    }

    public void setData(String id, String price) {
        System.out.println("click");
        System.out.println(id);
        if (salary.isSelected()) {
            lblPrice.setText(price);
            btnAdd.setText("OK");
            salaryPrice = price;
            paneSalaryId.setVisible(true);
            txtIdSearch.setText(id);
            clickId = id;
        }
    }

    public void addOnAction(ActionEvent actionEvent) {
        if (comboId.getValue() != null) {
            try {
                if (EmployeeController.idExists(id)) {
                    EmployeeSalaryDetailsDTO employeeSalaryDetailsDTO = new EmployeeSalaryDetailsDTO();
                    employeeSalaryDetailsDTO.setEmployee_id(id);
                    employeeSalaryDetailsDTO.setPrice(Double.parseDouble(txtPerMonthSalary.getText()));
                    employeeSalaryDetailsDTO.setDate(DateTimeUtil.dateNow());
                    employeeSalaryDetailsDTO.setSalary_id(salaryId);
                    if (EmployeeSalaryDetailsModel.save(employeeSalaryDetailsDTO)) {
                        Notification.notification("Salary is Payed", "payed successful ");
//                        new Alert(Alert.AlertType.CONFIRMATION, "OK").show();
                        setSalaryDetails();
                    }
                }
                if (CoachController.idExists(id)) {
                    CoachSalaryDetailsDTO coachSalaryDetailsDTO = new CoachSalaryDetailsDTO(
                            id,
                            DateTimeUtil.dateNow(),
                            txtPerMonthSalary.getText(),
                            salaryId
                    );
                    if (CoachSalaryDetailsModel.save(coachSalaryDetailsDTO)) {
                        Notification.notification("Salary is Payed", "payed successful ");
//                        new Alert(Alert.AlertType.CONFIRMATION, "OK").show();
                    }
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Notification.notificationWARNING("please select employee", "No select employee");
        }
    }

    public void idOnAction(ActionEvent actionEvent) {
        try {
            ResultSet set1 = EmployeeController.getDataForId(String.valueOf(comboId.getValue()));
            if (set1.next()) {
                salaryId = set1.getString(5);
                id = set1.getString(4);
                txtName.setText(set1.getString(1) + " " + set1.getString(2));
                txtNic.setText(set1.getString(3));

                String employeeCount = EmployeeAttendanceModel.getAttendanceCount(String.valueOf(comboId.getValue()), getProcessDate());
                txtAttendance.setText(employeeCount);

                SalaryDTO salary = SalaryModel.findSalaryById(set1.getString(5));
                txtAvailableSalary.setText(String.valueOf(salary.getSalary()));

                SimpleDateFormat format = new SimpleDateFormat("MM");
                int days = DateTimeUtil.getDays(Integer.parseInt(DateTimeUtil.yearNow()), Integer.valueOf(format.format(new Date())));
                double oneDaySalary = Double.parseDouble(txtAvailableSalary.getText()) / days;
                double preSalary = oneDaySalary * Integer.parseInt(txtAttendance.getText());
                txtPerMonthSalary.setText(String.valueOf(preSalary));
            }
            ResultSet set = CoachController.getDataForId(String.valueOf(comboId.getValue()));
            if (set.next()) {
                salaryId = set.getString(5);
                id = set.getString(4);
                txtName.setText(set.getString(1) + " " + set.getString(2));
                txtNic.setText(set.getString(3));

                String coachCount = CoachAttendanceModel.countAttendanceByDateAndCouchId(String.valueOf(comboId.getValue()), getProcessDate());
                txtAttendance.setText(coachCount);

                SalaryDTO salary = SalaryModel.findSalaryById(set.getString(5));
                txtAvailableSalary.setText(String.valueOf(salary.getSalary()));

                SimpleDateFormat format = new SimpleDateFormat("MM");
                int days = DateTimeUtil.getDays(Integer.parseInt(DateTimeUtil.yearNow()), Integer.valueOf(format.format(new Date())));
                double oneDaySalary = Double.parseDouble(txtAvailableSalary.getText()) / days;
                double preSalary = oneDaySalary * Integer.parseInt(txtAttendance.getText());
                txtPerMonthSalary.setText(String.valueOf(preSalary));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private String getProcessDate() {
        String[] split = DateTimeUtil.dateNow().split("-");
        String month = split[0] + "-" + split[1];
        return month;
    }

    public void roleOnAction(ActionEvent actionEvent) {
        try {
            if (comboRole.getValue().equals("other")) {
                otherRole.setVisible(true);
            } else {
                otherRole.setVisible(false);
                txtOtherRole.setText("");
            }
        } catch (Exception e) {

        }

    }

    public void salaryAddOnAction(ActionEvent actionEvent) {
        System.out.println("cilck");
        if (!lblPrice.getText().equals("")) {
            if (btnAdd.getText().equals("ADD NEW SALARY")) {
                String role = null;
                if (txtOtherRole.getText().equals("")) {
                    role = String.valueOf(comboRole.getValue());
                } else {
                    role = txtOtherRole.getText();
                }
                try {
                    SalaryDTO salaryDTO = new SalaryDTO();
                    salaryDTO.setSalary_Id(getNextId());
                    salaryDTO.setSalary(Double.valueOf(lblPrice.getText()));
                    salaryDTO.setRole(role);

                    if (SalaryModel.save(salaryDTO)) {
                        Notification.notification("Salary Added", "your salary Added");
                        setSalaryData();
                    }
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (btnAdd.getText().equals("OK")) {
                System.out.println("ok");
                clear();
                btnAdd.setText("ADD NEW SALARY");
            }
            if (btnAdd.getText().equals("UPDATE SALARY")) {
                try {

                    SalaryDTO salaryDTO = SalaryModel.findSalaryById(clickId);
                    salaryDTO.setSalary(Double.parseDouble(lblPrice.getText()));

                    if (SalaryModel.update(salaryDTO)) {
                        Notification.notification("Salary Updated", "your salary is updated ");
                        btnAdd.setText("ADD NEW SALARY");
                        comboRole.getItems().clear();
                        setRole();
                        clear();
                        setSalaryData();
                    }
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                btnAdd.setText("ADD NEW SALARY");
            }
        } else {
            Notification.notificationWARNING("Place add data", "add data ");
        }
    }

    private String getNextId() {
        String currentId = null;
        try {
            List<String> salaryIds = SalaryModel.findSalaryIdOrderByLength();
            for (String id : salaryIds) {
                currentId = id;
            }
            try {
                String[] s = currentId.split("S");
                int n = Integer.parseInt(s[1]);
                n++;
                return "S" + n;
            } catch (NullPointerException e) {
                return "S1";
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return "S1";
    }

    public void salaryOnAction(ActionEvent actionEvent) {
        if (salary.isSelected()) {
            addSalary.setVisible(true);
            txtId.setText("SALARY ID");
            txtSalary.setText("SALARY");
            availableRole.setText("AVAILABLE ROLE");
            usage.setText("USAGE");
            givenSalary.setVisible(false);
            setSalaryData();
            comboMonth.setVisible(false);
            comboYear.setVisible(false);
        }
        if (salaryDetails.isSelected()) {
            addSalary.setVisible(false);
            txtId.setText("EMP ID");
            txtSalary.setText("SALARY");
            availableRole.setText("SALARY ID");
            usage.setText("DATE");
            givenSalary.setVisible(true);
            setSalaryDetails();
            comboMonth.setValue(true);
            comboYear.setValue(true);
            setEmpIds();
        }
    }

    public void salaryDetaisOnAction(ActionEvent actionEvent) {
        if (salary.isSelected()) {
            addSalary.setVisible(true);
            txtId.setText("SALARY ID");
            txtSalary.setText("SALARY");
            availableRole.setText("AVAILABLE ROLE");
            usage.setText("USAGE");
            givenSalary.setVisible(false);
            setSalaryData();
            comboMonth.setVisible(false);
            comboYear.setVisible(false);
        }
        if (salaryDetails.isSelected()) {
            setSalaryDetails();
            addSalary.setVisible(false);
            txtId.setText("EMP ID");
            txtSalary.setText("SALARY");
            availableRole.setText("SALARY ID");
            usage.setText("DATE");
            givenSalary.setVisible(true);
            comboMonth.setVisible(true);
            comboYear.setVisible(true);
            setEmpIds();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPane.setStyle("-fx-background-color: transparent");
        salary.fire();
        setRole();
        otherRole.setVisible(false);
        setMonth();
        setYear();
        comboYear.setValue(DateTimeUtil.yearNow());
        comboMonth.setValue(DateTimeUtil.monthNow());
        btnAdd.setText("ADD NEW SALARY");
        paneSalaryId.setVisible(false);

    }

    private void setYear() {
        ArrayList<String> year = new ArrayList<>();
        year.add(DateTimeUtil.yearNow());
        boolean isNotDuplicate = true;
        boolean isNotDuplicate2 = true;

        try {
            List<String> days = CoachSalaryDetailsModel.findDistinctDateCoachSalaryDetails();
            for (String day :days) {
                String[] split = day.split("-");
                String s = split[0];
                for (int i = 0; i < year.size(); i++) {
                    if (year.get(i).equals(s)) {
                        isNotDuplicate = false;
                    }
                }
                if (isNotDuplicate) {
                    year.add(s);
                }
            }
            List<String> dateList = EmployeeSalaryDetailsModel.findDistinctDate();
            for (String date : dateList) {
                String[] split = date.split("-");
                String s = split[0];
                for (int i = 0; i < year.size(); i++) {
                    if (year.get(i).equals(s)) {
                        isNotDuplicate2 = false;
                    }
                }
                if (isNotDuplicate2) {
                    year.add(s);
                }
            }
            comboYear.getItems().addAll(year);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setMonth() {
        comboMonth.getItems().clear();
        comboMonth.getItems().addAll(allMonth);
    }

    private void setEmpIds() {
        comboId.getItems().clear();
        ArrayList<String> ids = new ArrayList<>();
        try {
            ResultSet set = EmployeeController.getAllIds();
            while (set.next()) {
                String month = null;
                for (int i = 0; i < allMonth.length; i++) {
                    if (allMonth[i].equals(comboMonth.getValue())) {
                        month = String.valueOf(i).length() == 1 ? "0" + (i + 1) : String.valueOf((i + 1));
                    }

                }
                List<EmployeeSalaryDetailsDTO> detailsDTOS = EmployeeSalaryDetailsModel.findEmployeeSalaryDetailsByEmployeeIdAndDateLike(comboYear.getValue() + "-" + month, set.getString(1));
                if (detailsDTOS.isEmpty()) {
                    ids.add(set.getString(1));
                }

            }
            ResultSet set1 = CoachController.getALlId();
            while (set1.next()) {
                String month = null;
                for (int i = 0; i < allMonth.length; i++) {
                    if (allMonth[i].equals(comboMonth.getValue())) {
                        month = String.valueOf(i).length() == 1 ? "0" + (i + 1) : String.valueOf((i + 1));
                    }

                }
                boolean isExistThisMonth = CoachSalaryDetailsModel.existThisMonth(comboYear.getValue() + "-" + month, set1.getString(1));
                if (!isExistThisMonth) {
                    ids.add(set1.getString(1));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        comboId.getItems().addAll(ids);
    }

    private void setRole() {
        comboRole.getItems().clear();
        ArrayList<String> role = new ArrayList<>();
        role.clear();
        try {
            List<SalaryDTO> salaryDTOS = SalaryModel.findSalary();
            for (SalaryDTO salaryDTO : salaryDTOS) {
                role.add(salaryDTO.getRole());
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        role.add("other");
        comboRole.getItems().addAll(role);

    }

    public void setSalaryDetails() {
        String month = null;
        for (int i = 0; i < allMonth.length; i++) {
            if (allMonth[i].equals(comboMonth.getValue())) {
                month = String.valueOf(i).length() == 1 ? "0" + (i + 1) : String.valueOf((i + 1));
            }

        }

        Vbox.getChildren().clear();
        try {
            List<lk.ijse.theGym.dto.EmployeeSalaryDetailsDTO> employeeSalaryDetailsDTOS = lk.ijse.theGym.model.EmployeeSalaryDetailsModel.findEmployeeSalaryByLikeDate(comboYear.getValue() + "-" + month);
            for (lk.ijse.theGym.dto.EmployeeSalaryDetailsDTO employeeSalaryDetailsDTO : employeeSalaryDetailsDTOS) {
                navigation(
                        employeeSalaryDetailsDTO.getEmployee_id(),
                        String.valueOf(employeeSalaryDetailsDTO.getPrice()),
                        employeeSalaryDetailsDTO.getSalary_id(),
                        employeeSalaryDetailsDTO.getDate()
                );

            }
            List<CoachSalaryDetailsDTO> coachSalaryDetailsDTOsList = CoachSalaryModel.findCoachSalaryDetails(comboYear.getValue() + "-" + month);
            for (CoachSalaryDetailsDTO coachSalaryDetailsDTO : coachSalaryDetailsDTOsList) {
                navigation(
                        coachSalaryDetailsDTO.getCoach_id(),
                        coachSalaryDetailsDTO.getPrice(),
                        coachSalaryDetailsDTO.getSalary_id(),
                        coachSalaryDetailsDTO.getDate()
                );
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setSalaryData() {
        ArrayList<SalaryTm> salaryData = new ArrayList<>();
        Vbox.getChildren().clear();
        try {
            List<SalaryDTO> salary = SalaryModel.findSalary();
            for (SalaryDTO salaryDTO : salary) {
                boolean isNotDuplicate1 = true;
                int index1 = -1;
                int index2 = -1;
                boolean isNotDuplicate2 = true;
                ResultSet set2 = EmployeeController.getSalaryCount(salaryDTO.getSalary_Id());
                if (set2.next()) {

                    for (int i = 0; i < salaryData.size(); i++) {
                        if (salaryDTO.getSalary_Id().equals(salaryData.get(i).getId())) {
                            isNotDuplicate1 = false;
                            index1 = i;
                        }
                    }
                    if (isNotDuplicate1) {
                        salaryData.add(new SalaryTm(
                                salaryDTO.getSalary_Id(),
                                String.valueOf(salaryDTO.getSalary()),
                                salaryDTO.getRole(),
                                set2.getString(1) == null ? "0" : set2.getString(1)

                        ));
                    } else {
                        isNotDuplicate1 = true;
                        salaryData.get(index1).setUsage(String.valueOf(Integer.valueOf(salaryData.get(index1).getUsage()) + Integer.parseInt(set2.getString(1))));

                    }

                } else {
                    for (int i = 0; i < salaryData.size(); i++) {
                        if (salaryDTO.getSalary_Id().equals(salaryData.get(i).getId())) {
                            isNotDuplicate1 = false;
                            index1 = i;
                        }
                    }
                    if (isNotDuplicate1) {
                        salaryData.add(new SalaryTm(
                                salaryDTO.getSalary_Id(),
                                String.valueOf(salaryDTO.getSalary()),
                                salaryDTO.getRole(),
                                salaryData.get(index1).getUsage() == null ? "0" : String.valueOf(Integer.valueOf(salaryData.get(index1).getUsage()) + Integer.parseInt(set2.getString(1)))

                        ));
                    } else {
                        isNotDuplicate1 = true;
                        salaryData.get(index1).setUsage(String.valueOf(Integer.valueOf(salaryData.get(index1).getUsage()) + Integer.parseInt(set2.getString(1))));

                    }
                }

                String salaryCount = CoachController.countSalaryBySalaryId(salaryDTO.getSalary_Id());
                for (int i = 0; i < salaryData.size(); i++) {
                    if (salaryDTO.getSalary_Id().equals(salaryData.get(i).getId())) {
                        isNotDuplicate2 = false;
                        index2 = i;
                    }
                }
                if (isNotDuplicate2) {
                    salaryData.add(new SalaryTm(
                            salaryDTO.getSalary_Id(),
                            String.valueOf(salaryDTO.getSalary()),
                            salaryDTO.getRole(),
                            salaryCount == null ? "0" : salaryCount

                    ));
                } else {
                    isNotDuplicate2 = true;
                    salaryData.get(index2).setUsage(String.valueOf(Integer.valueOf(salaryData.get(index2).getUsage()) + Integer.parseInt(salaryCount)));

                }

            }
            for (int i = 0; i < salaryData.size(); i++) {

                navigation(salaryData.get(i).getId(), salaryData.get(i).getSalary(), salaryData.get(i).getRole(), salaryData.get(i).getUsage());
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private void navigation(String id, String salary, String role, String usage) {
        try {
            FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/bar/SalaryDetailsBar.fxml"));
            Parent root = loader.load();
            SalaryDetailsBarController controller = loader.getController();
            controller.setData(id, salary, role, usage);
            Vbox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void monthOnAction(ActionEvent actionEvent) {
        setSalaryDetails();
        setEmpIds();

    }

    public void yearOnAction(ActionEvent actionEvent) {
        setSalaryDetails();
        setEmpIds();
    }

    private void clear() {
        lblPrice.clear();
        paneSalaryId.setVisible(false);
    }

    public void priceOnAction(KeyEvent keyEvent) {
        if (RegexUtil.regex(btnAdd, lblPrice, lblPrice.getText(), "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$", "-fx-text-fill: white")) {
            try {
                if (salaryPrice.equals(lblPrice.getText())) {

                } else {
                    btnAdd.setText("UPDATE SALARY");
                }
            } catch (NullPointerException n) {
            }


        }

    }

    public void otherRole(KeyEvent keyEvent) {
        if (RegexUtil.regex(btnAdd, txtOtherRole, txtOtherRole.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white")) {

        }
    }

    public void employeeId(KeyEvent keyEvent) {

    }
}
