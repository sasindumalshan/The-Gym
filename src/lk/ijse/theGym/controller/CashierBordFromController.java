package lk.ijse.theGym.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.theGym.model.CoachController;
import lk.ijse.theGym.model.CustomerAttendanceController;
import lk.ijse.theGym.util.DateTimeUtil;
import lk.ijse.theGym.util.Navigation;
import lk.ijse.theGym.util.Notification;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class CashierBordFromController implements Initializable {

    public Text txtHour;
    public Text txtMin;
    public Text txtDate;
    public Text txtAdminLastName;
    public Text txtAdminFistName;
    public Text txtCoaches;
    public Text txtEmployeeAttendance;
    public AnchorPane anchorpane;

    public void closeMouseClick(MouseEvent mouseEvent) {
        Navigation.exit();
    }

    public void minimizMouseClick(MouseEvent mouseEvent) {
        Stage stage= (Stage) anchorpane.getScene().getWindow();
        stage.setIconified(true);
    }

    public void CustomerOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.swatchNavigation("RegstionFrom.fxml",actionEvent);
    }

    public void PaymentOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.swatchNavigation("PaymentFrom.fxml",actionEvent);
    }

    public void StoreOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.swatchNavigation("StoreViewFrom.fxml",actionEvent);

    }

    public void AttendancOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.swatchNavigation("Attendance.fxml",actionEvent);

    }

    public void logoutMouseClick(MouseEvent mouseEvent) throws IOException {
        Navigation.swatchNavigation("LoginFrom.fxml",mouseEvent);
    }
    private void setTime(){
        Thread clock = new Thread() {
            public void run() {
                while (true) {
                    DateFormat hour = new SimpleDateFormat("hh");
                    txtHour.setText(hour.format(new Date()));
                    DateFormat min = new SimpleDateFormat("mm");
                    txtMin.setText(min.format(new Date()));
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        clock.start();
    }
    private void setDate(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, d MMMMM ");
        txtDate.setText(simpleDateFormat.format(new Date()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCoachCount();
        setCustomerAttendanceCount();
        setDate();
        setTime();


    }

    private void setCoachCount() {
        try {
            ResultSet set= CoachController.getCoachCunt();
            if (set.next()){
                txtCoaches.setText(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setCustomerAttendanceCount() {
        try {
            ResultSet set= CustomerAttendanceController.getTodayAttendanceCount(DateTimeUtil.dateNow());
            if (set.next()){
                txtEmployeeAttendance.setText(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
