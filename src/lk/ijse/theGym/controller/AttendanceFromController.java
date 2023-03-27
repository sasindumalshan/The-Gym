package lk.ijse.theGym.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.model.*;
import lk.ijse.theGym.to.CoachAttendance;
import lk.ijse.theGym.to.CustomerAttendance;
import lk.ijse.theGym.to.EmployeeAttendance;
import lk.ijse.theGym.util.DateTimeUtil;
import lk.ijse.theGym.util.Navigation;
import lk.ijse.theGym.util.Notification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AttendanceFromController implements Initializable {
    public Text txtCustomer;
    public Text txtCoach;
    public JFXTextField search;
    public ScrollPane scrollPane;
    public VBox vBox;
    public Text txtCityDate;
    public JFXComboBox combMembers;
    public JFXDatePicker dpDate;
    public JFXTextField txtId;
    public Text txtEmployee;
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> todayAllAttendanceId = new ArrayList<>();


    private void navigation(String id, String name, String time, String date) {
        try {
            FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/bar/AttendanceBarFrom.fxml"));
            Parent root = loader.load();
            AttendanceBarFromController controller = loader.getController();
            controller.setData(id, name, time, date);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.swatchNavigation("CashiarDashBordFrom.fxml", actionEvent);
    }


    public void selectMemberTypeOnAction(ActionEvent actionEvent) {
        autoSelected();
        vBox.getChildren().clear();
        getSelectedDetails();
    }

    private void getSelectedDetails() {
        try {
            if (getSelectedDate() == null) {
                if (String.valueOf(combMembers.getValue()).equals("All")) {
                    loadTodayAttendance();
                }
                if (String.valueOf(combMembers.getValue()).equals("Employee")) {
                    todayEmployeeAttendance(DateTimeUtil.dateNow());
                }
                if (String.valueOf(combMembers.getValue()).equals("Members")) {
                    todayCustomerAttendance(DateTimeUtil.dateNow());
                }
                if (String.valueOf(combMembers.getValue()).equals("Coach")) {
                    todayCoachAttendance(DateTimeUtil.dateNow());
                }
            } else {
                if (String.valueOf(combMembers.getValue()).equals("All")) {
                    loadSelectedAttendance();
                }

                if (String.valueOf(combMembers.getValue()).equals("Employee")) {
                    System.out.println(getSelectedDate());
                    todayEmployeeAttendance(getSelectedDate());
                }
                if (String.valueOf(combMembers.getValue()).equals("Members")) {
                    todayCustomerAttendance(getSelectedDate());
                }
                if (String.valueOf(combMembers.getValue()).equals("Coach")) {
                    todayCoachAttendance(getSelectedDate());
                }

            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private void loadSelectedAttendance() {
        try {
            todayCustomerAttendance(getSelectedDate());
            todayEmployeeAttendance(getSelectedDate());
            todayCoachAttendance(getSelectedDate());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private String getSelectedDate() {
        if (dpDate.getValue() == null) {
            return null;
        }
        LocalDate date = dpDate.getValue();
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    }

    public void dateOnAction(ActionEvent actionEvent) {
        autoSelected();
        vBox.getChildren().clear();
        getSelectedDetails();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTodayAttendance();
        setMemberType();
        combMembers.setValue("All");
        setCount();
        Platform.runLater(() -> txtId.requestFocus());


    }

    private void setCount() {
        try {
            setTodayAllEmployeeAttendanceCount();
            setTodayAllCustomerAttendanceCount();
            setTodayAllCoachAttendanceCount();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    private void setTodayAllCoachAttendanceCount() throws SQLException, ClassNotFoundException {
        ResultSet set = CoachAttendanceController.getTodayAttendanceCount();
        if (set.next()) {
            txtCoach.setText(set.getString(1));
        }
    }

    private void setTodayAllCustomerAttendanceCount() throws SQLException, ClassNotFoundException {
        ResultSet set = CustomerAttendanceController.getTodayAttendanceCount();
        if (set.next()) {
            txtCustomer.setText(set.getString(1));
        }
    }

    private void setTodayAllEmployeeAttendanceCount() throws SQLException, ClassNotFoundException {
        ResultSet set = EmployeeAttendanceController.getTodayAttendanceCount();
        if (set.next()) {
            txtEmployee.setText(set.getString(1));
        }
    }

    private void setMemberType() {
        ArrayList<String> memberType = new ArrayList<>();
        memberType.add("All");
        memberType.add("Employee");
        memberType.add("Members");
        memberType.add("Coach");
        combMembers.getItems().addAll(memberType);
    }

    private void loadTodayAttendance() {
        try {
            todayCustomerAttendance(DateTimeUtil.dateNow());
            todayEmployeeAttendance(DateTimeUtil.dateNow());
            todayCoachAttendance(DateTimeUtil.dateNow());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void todayEmployeeAttendance(String date) throws SQLException, ClassNotFoundException {
        ResultSet set2 = EmployeeAttendanceController.getTodayEmpAttendance(date);
        while (set2.next()) {
            navigation(set2.getString(3), set2.getString(4) + " " + set2.getString(5), set2.getString(2), set2.getString(1));
        }
    }

    private void todayCoachAttendance(String date) throws SQLException, ClassNotFoundException {
        ResultSet set3 = CoachAttendanceController.getTodayAttendance(date);
        while (set3.next()) {
            navigation(set3.getString(3), set3.getString(4) + " " + set3.getString(5), set3.getString(2), set3.getString(1));
        }
    }

    private void todayCustomerAttendance(String date) throws SQLException, ClassNotFoundException {
        ResultSet set1 = CustomerAttendanceController.getTodayAttendance(date);
        while (set1.next()) {
            navigation(set1.getString(3), set1.getString(4) + " " + set1.getString(5), set1.getString(2), set1.getString(1));
        }
    }

    private void clear() {
        vBox.getChildren().clear();
    }

    public void searchOnKeyReleased(KeyEvent keyEvent) {
        ids.clear();
        try {
            if (search.getText().equals("")) {
                clear();
                loadTodayAttendance();
                autoSelected();

            } else {
                if (getSelectedDate() == null) {

                    if (String.valueOf(combMembers.getValue()).equals("All")) {
                        searchCustomerId(DateTimeUtil.dateNow(), search.getText());
                        searchCustomerName(DateTimeUtil.dateNow(), search.getText());
                        searchEmployeeId(DateTimeUtil.dateNow(), search.getText());
                        searchEmployeeName(DateTimeUtil.dateNow(), search.getText());
                        searchCoachId(DateTimeUtil.dateNow(), search.getText());
                        searchCoachName(DateTimeUtil.dateNow(), search.getText());
                    }
                    if (String.valueOf(combMembers.getValue()).equals("Employee")) {
                        searchEmployeeId(DateTimeUtil.dateNow(), search.getText());
                        searchEmployeeName(DateTimeUtil.dateNow(), search.getText());
                    }
                    if (String.valueOf(combMembers.getValue()).equals("Members")) {
                        searchCustomerId(DateTimeUtil.dateNow(), search.getText());
                        searchCustomerName(DateTimeUtil.dateNow(), search.getText());
                    }
                    if (String.valueOf(combMembers.getValue()).equals("Coach")) {
                        searchCoachId(DateTimeUtil.dateNow(), search.getText());
                        searchCoachName(DateTimeUtil.dateNow(), search.getText());
                    }

                } else {
                    if (String.valueOf(combMembers.getValue()).equals("All")) {
                        searchCustomerId(DateTimeUtil.dateNow(), getSelectedDate());
                        searchCustomerName(DateTimeUtil.dateNow(), getSelectedDate());
                        searchEmployeeId(DateTimeUtil.dateNow(), getSelectedDate());
                        searchEmployeeName(DateTimeUtil.dateNow(), getSelectedDate());
                        searchCoachId(DateTimeUtil.dateNow(), getSelectedDate());
                        searchCoachName(DateTimeUtil.dateNow(), getSelectedDate());
                    }
                    if (String.valueOf(combMembers.getValue()).equals("Employee")) {
                        searchEmployeeId(DateTimeUtil.dateNow(), getSelectedDate());
                        searchEmployeeName(DateTimeUtil.dateNow(), getSelectedDate());
                    }
                    if (String.valueOf(combMembers.getValue()).equals("Members")) {
                        searchCustomerId(DateTimeUtil.dateNow(), getSelectedDate());
                        searchCustomerName(DateTimeUtil.dateNow(), getSelectedDate());
                    }
                    if (String.valueOf(combMembers.getValue()).equals("Coach")) {
                        searchCoachId(DateTimeUtil.dateNow(), getSelectedDate());
                        searchCoachName(DateTimeUtil.dateNow(), getSelectedDate());
                    }


                }
                clear();

                for (int i = 0; i < ids.size(); i++) {
                    System.out.println(ids.get(i));
                    setData(CustomerAttendanceController.getIdDate(ids.get(i)));
                    setData(CoachAttendanceController.getIdData(ids.get(i)));
                    setData(EmployeeAttendanceController.getIdData(ids.get(i)));
                }
                if (ids.size() == 0) {
                    clear();
                }
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }

    private void autoSelected() {
        Thread Count5S = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= 10; i++) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(i);
                    if (i==5){
                        Platform.runLater(() -> txtId.requestFocus());
                    }
                }
            }
        };
        Count5S.start();
    }

    private void setData(ResultSet set) throws SQLException {
        if (set.next()) {
            navigation(set.getString(3), set.getString(4) + " " + set.getString(5), set.getString(2), set.getString(1));
        }
    }

    private void checkDublicateID(ResultSet set) throws SQLException {
        while (set.next()) {
            if (ids.size() == 0) {

                ids.add(set.getString(1));
            } else {
                for (int i = 0; i < ids.size(); i++) {
                    if (!ids.get(i).equals(set.getString(1))) {
                        ids.add(set.getString(1));
                    }
                }
            }
        }
    }

    private void searchCoachName(String date, String name) throws SQLException, ClassNotFoundException {
        ResultSet set = CoachAttendanceController.getsearchName(date, name + "%");
        checkDublicateID(set);
    }

    private void searchCoachId(String date, String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CoachAttendanceController.getsearchId(date, id + "%");
        checkDublicateID(set);
    }

    private void searchEmployeeName(String date, String name) throws SQLException, ClassNotFoundException {
        ResultSet set = EmployeeAttendanceController.getsSearchName(date, name + "%");
        checkDublicateID(set);
    }

    private void searchEmployeeId(String date, String id) throws SQLException, ClassNotFoundException {
        ResultSet set = EmployeeAttendanceController.getSearchId(date, id + "%");
        checkDublicateID(set);
    }

    private void searchCustomerName(String date, String name) throws SQLException, ClassNotFoundException {
        ResultSet set = CustomerAttendanceController.getSearchName(date, name + "%");
        checkDublicateID(set);
    }

    private void searchCustomerId(String date, String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CustomerAttendanceController.getSearchId(date, id + "%");
        checkDublicateID(set);
    }



    private boolean searchDuplicateId() {
        for (int i = 0; i < todayAllAttendanceId.size(); i++) {
            if (todayAllAttendanceId.get(i).equals(txtId.getText())) {
//                new Alert(Alert.AlertType.WARNING,"Already Added").show();
                Notification.notificationWARNING("Already Added","you Already mark Attendance");
                txtId.setText("");
                return false;
            }
        }
        return true;
    }


    private void setDataTodayAllAttendanceIds(ResultSet set) throws SQLException {
        while (set.next()) {
            todayAllAttendanceId.add(set.getString(1));
        }

    }


    public void idOnkeyTyped(KeyEvent keyEvent) {
        try {
            setDataTodayAllAttendanceIds(CustomerAttendanceController.getAllIds());
            setDataTodayAllAttendanceIds(EmployeeAttendanceController.getAllIds());
            setDataTodayAllAttendanceIds(CoachAttendanceController.getAllIds());

            if (searchDuplicateId()) {
                boolean cNot = false;
                boolean eNot = false;
                boolean mNot = false;
                if (CustomerController.idExists(txtId.getText())) {
                    boolean added = CustomerAttendanceController.setAttendance(new CustomerAttendance(DateTimeUtil.dateNow(), DateTimeUtil.timeNow(), txtId.getText()));
                    if (added){
                        Notification.notification("Mark Attendance","successful Mark on today Attendance ");
                    }
                    txtId.setText("");
                } else {
                    mNot = true;
                }
                if (EmployeeController.idExists(txtId.getText())) {
                    boolean added = EmployeeAttendanceController.setAttendance(new EmployeeAttendance(txtId.getText(), DateTimeUtil.dateNow(), DateTimeUtil.timeNow()));
                    if (added){
                        Notification.notification("Mark Attendance","successful Mark on today Attendance ");
                    }
                    txtId.setText("");
                } else {
                    eNot = true;
                }
                if (CoachController.idExists(txtId.getText())) {
                    boolean added = CoachAttendanceController.setAttendance(new CoachAttendance(DateTimeUtil.dateNow(), DateTimeUtil.timeNow(), txtId.getText()));
                    if (added){
                        Notification.notification("Mark Attendance","successful Mark on today  Attendance ");
                    }
                    txtId.setText("");
                } else {
                    cNot = true;
                }
                if (cNot & eNot & mNot) {
                    txtId.clear();
//                    new Alert(Alert.AlertType.WARNING, "Place Add Member ").show();
                    Notification.notificationWARNING("This Member Not Added","please add this Member and before you can workout ");

                }
                setCount();
                clear();
                loadTodayAttendance();

            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void CoachOnAction(ActionEvent actionEvent) {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/theGym/view/report/COACH_ATTENDANCE.jrxml");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("DATE", "2022-12-02");

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(print, false);

        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void EmployeeOnAction(ActionEvent actionEvent) {

        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/theGym/view/report/EMPLOYEE_ATTENDANCE.jrxml");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("DATE", "2022-11-19");

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(print, false);

        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void CustomerOnAction(ActionEvent actionEvent) {
        InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/theGym/view/report/CUSTOMER_ATTENDANCE.jrxml");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("DATE", "2022-12-02");

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(print, false);

        } catch (JRException e) {
            throw new RuntimeException(e);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
