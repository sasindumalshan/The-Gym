package lk.ijse.theGym.controller.User;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.dto.CoachAttendanceDTO;
import lk.ijse.theGym.dto.CustomerAttendanceDTO;
import lk.ijse.theGym.dto.projection.AttendanceProjection;
import lk.ijse.theGym.dto.EmployeeAttendanceDTO;
import lk.ijse.theGym.model.CoachAttendanceModel;
import lk.ijse.theGym.model.CustomerAttendanceModel;
import lk.ijse.theGym.model.EmployeeAttendanceModel;
import lk.ijse.theGym.modelController.*;
import lk.ijse.theGym.to.CustomerAttendance;
import lk.ijse.theGym.util.DateTimeUtil;
import lk.ijse.theGym.util.Navigation;
import lk.ijse.theGym.util.Notification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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


    private void navigation(AttendanceProjection attendanceProjection) {
        try {
            FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/bar/AttendanceBarFrom.fxml"));
            Parent root = loader.load();
            AttendanceBarFromController controller = loader.getController();
            controller.setData(attendanceProjection);
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
        txtCoach.setText(CoachAttendanceModel.countAttendanceByDate());
    }

    private void setTodayAllCustomerAttendanceCount() throws SQLException, ClassNotFoundException {
        txtCustomer.setText(CustomerAttendanceModel.countAttendanceByDate(DateTimeUtil.dateNow()));
    }

    private void setTodayAllEmployeeAttendanceCount() throws SQLException, ClassNotFoundException {
        txtEmployee.setText(EmployeeAttendanceModel.countAttendanceByDate(DateTimeUtil.dateNow()));
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
        ArrayList<AttendanceProjection> projectionList = EmployeeAttendanceModel.findAttendanceByDate(date);
        setDataToNavigation(projectionList);
    }

    private void todayCoachAttendance(String date) throws SQLException, ClassNotFoundException {
        ArrayList<AttendanceProjection> projectionList = CoachAttendanceModel.findAttendanceByDate(date);
        setDataToNavigation(projectionList);
    }

    private void setDataToNavigation(ArrayList<AttendanceProjection> projectionList) {
        for (AttendanceProjection attendanceProjection : projectionList) {
            navigation(attendanceProjection);
        }
    }

    private void todayCustomerAttendance(String date) throws SQLException, ClassNotFoundException {
        ArrayList<AttendanceProjection> projectionList = CustomerAttendanceModel.findAttendanceCustomerByDate(date);
        setDataToNavigation(projectionList);
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
                        searchCustomerName(DateTimeUtil.dateNow(), search.getText());
                        searchEmployeeNameOrId(DateTimeUtil.dateNow(), search.getText());
                        searchCoachNameOrId(DateTimeUtil.dateNow(), search.getText());
                    }
                    if (String.valueOf(combMembers.getValue()).equals("Employee")) {
                        searchEmployeeNameOrId(DateTimeUtil.dateNow(), search.getText());
                    }
                    if (String.valueOf(combMembers.getValue()).equals("Members")) {
                        searchCustomerName(DateTimeUtil.dateNow(), search.getText());
                    }
                    if (String.valueOf(combMembers.getValue()).equals("Coach")) {
                        searchCoachNameOrId(DateTimeUtil.dateNow(), search.getText());
                    }

                } else {
                    if (String.valueOf(combMembers.getValue()).equals("All")) {
                        searchCustomerName(DateTimeUtil.dateNow(), getSelectedDate());
                        searchEmployeeNameOrId(DateTimeUtil.dateNow(), getSelectedDate());
                        searchCoachNameOrId(DateTimeUtil.dateNow(), getSelectedDate());
                    }
                    if (String.valueOf(combMembers.getValue()).equals("Employee")) {
                        searchEmployeeNameOrId(DateTimeUtil.dateNow(), getSelectedDate());
                    }
                    if (String.valueOf(combMembers.getValue()).equals("Members")) {
                        searchCustomerName(DateTimeUtil.dateNow(), getSelectedDate());
                    }
                    if (String.valueOf(combMembers.getValue()).equals("Coach")) {
                        searchCoachNameOrId(getSelectedDate(), search.getText());
                    }


                }
                clear();

                for (int i = 0; i < ids.size(); i++) {
                    setDataToNavigation(CustomerAttendanceModel.findAttendanceByCustomerId(ids.get(i)));
                    setDataToNavigation(CoachAttendanceModel.findAttendanceByCoachId(ids.get(i)));
                    setDataToNavigation(EmployeeAttendanceModel.findAttendanceByEmployeeId(ids.get(i)));
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
                    if (i == 5) {
                        Platform.runLater(() -> txtId.requestFocus());
                    }
                }
            }
        };
        Count5S.start();
    }

    private void checkDuplicatedId(ArrayList<AttendanceProjection> projectionArrayList) throws SQLException {
        for (AttendanceProjection attendanceProjection : projectionArrayList) {
            if (ids.isEmpty()) {
                ids.add(attendanceProjection.getId());
            } else {
                for (String id : ids) {
                    if (!id.equals(attendanceProjection.getId())) {
                        ids.add(attendanceProjection.getId());
                    }
                }
            }
        }
    }

    private void searchCoachNameOrId(String date, String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<AttendanceProjection> attendanceProjectionArrayList = CoachAttendanceModel.findAttendanceByDateAndFistNameLikeOrLastNameOrDateLikeOrCoachId(date, searchText + "%");
        checkDuplicatedId(attendanceProjectionArrayList);
    }

    private void searchEmployeeNameOrId(String date, String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<AttendanceProjection> attendanceProjectionArrayList = EmployeeAttendanceModel.findAttendanceByDateAndFistNameLikeOrLastNameOrDateLikeOrEmployeeId(date, searchText + "%");
        checkDuplicatedId(attendanceProjectionArrayList);
    }

   /* private void searchEmployeeId(String date, String id) throws SQLException, ClassNotFoundException {
        ResultSet set = EmployeeAttendanceController.getSearchId(date, id + "%");
        checkDuplicatedId(set);
    }*/

    private void searchCustomerName(String date, String searchText) throws SQLException, ClassNotFoundException {
        ArrayList<AttendanceProjection> attendanceProjectionArrayList = CustomerAttendanceModel.findAttendanceByDateAndFistNameLikeOrLastNameOrDateLikeOrCustomerId(date, searchText + "%");
        checkDuplicatedId(attendanceProjectionArrayList);
    }

    /*private void searchCustomerId(String date, String id) throws SQLException, ClassNotFoundException {
        ResultSet set = CustomerAttendanceController.getSearchId(date, id + "%");
        checkDuplicatedId(set);
    }*/


    private boolean searchDuplicateId() {
        for (int i = 0; i < todayAllAttendanceId.size(); i++) {
            if (todayAllAttendanceId.get(i).equals(txtId.getText())) {
                Notification.notificationWARNING("Already Added", "you Already mark Attendance");
                txtId.setText("");
                return false;
            }
        }
        return true;
    }


    private void setDataTodayAllAttendanceIds(ArrayList<AttendanceProjection> projectionArrayList) throws SQLException {
        for (AttendanceProjection attendanceProjection : projectionArrayList) {
            todayAllAttendanceId.add(attendanceProjection.getId());
        }
    }


    public void idOnkeyTyped(KeyEvent keyEvent) {
        try {
            setDataTodayAllAttendanceIds(CustomerAttendanceModel.findAttendanceCustomerByDate(DateTimeUtil.dateNow()));
            setDataTodayAllAttendanceIds(EmployeeAttendanceModel.findAttendanceByDate(DateTimeUtil.dateNow()));
            setDataTodayAllAttendanceIds(CoachAttendanceModel.findAttendanceByDate(DateTimeUtil.dateNow()));

            if (searchDuplicateId()) {
                boolean cNot = false;
                boolean eNot = false;
                boolean mNot = false;
                if (CustomerController.idExists(txtId.getText())) {

                    CustomerAttendanceDTO customerAttendanceDTO=new CustomerAttendanceDTO();
                    customerAttendanceDTO.setCustomer_id(txtId.getText());
                    customerAttendanceDTO.setTime(DateTimeUtil.timeNow());
                    customerAttendanceDTO.setDate(DateTimeUtil.dateNow());

                    boolean added = CustomerAttendanceModel.save(customerAttendanceDTO);
                    if (added) {
                        Notification.notification("Mark Attendance", "successful Mark on today Attendance ");
                    }
                    txtId.setText("");
                } else {
                    mNot = true;
                }
                if (EmployeeController.idExists(txtId.getText())) {

                    EmployeeAttendanceDTO employeeAttendanceDTO=new EmployeeAttendanceDTO();
                    employeeAttendanceDTO.setEmployee_id(txtId.getText());
                    employeeAttendanceDTO.setDate(DateTimeUtil.dateNow());
                    employeeAttendanceDTO.setTime(DateTimeUtil.timeNow());

                    boolean isSave = EmployeeAttendanceModel.save(employeeAttendanceDTO);
                    if (isSave) {
                        Notification.notification("Mark Attendance", "successful Mark on today Attendance ");
                    }
                    txtId.setText("");
                } else {
                    eNot = true;
                }
                if (CoachController.idExists(txtId.getText())) {

                    CoachAttendanceDTO coachAttendanceDTO = new CoachAttendanceDTO();
                    coachAttendanceDTO.setCoach_id(txtId.getText());
                    coachAttendanceDTO.setDate(DateTimeUtil.dateNow());
                    coachAttendanceDTO.setTime(DateTimeUtil.timeNow());
                    boolean isSave = CoachAttendanceModel.save(coachAttendanceDTO);

                    if (isSave) {
                        Notification.notification("Mark Attendance", "successful Mark on today  Attendance ");
                    }
                    txtId.setText("");
                } else {
                    cNot = true;
                }
                if (cNot & eNot & mNot) {
                    txtId.clear();
//                    new Alert(Alert.AlertType.WARNING, "Place Add Member ").show();
                    Notification.notificationWARNING("This Member Not Added", "please add this Member and before you can workout ");

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
