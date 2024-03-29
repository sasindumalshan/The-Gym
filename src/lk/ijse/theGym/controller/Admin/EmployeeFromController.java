package lk.ijse.theGym.controller.Admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.theGym.controller.Admin.bar.EmployeeDetailBar;
import lk.ijse.theGym.controller.User.StoreFromController;
import lk.ijse.theGym.controller.User.bar.EmployeeAttendanceBar;
import lk.ijse.theGym.dto.projection.EmployeeAttendanceProjection;
import lk.ijse.theGym.model.EmployeeAttendanceModel;
import lk.ijse.theGym.modelController.EmployeeController;
import lk.ijse.theGym.util.DateTimeUtil;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class EmployeeFromController implements Initializable {
    private static EmployeeFromController instance;
    public VBox vBox;
    public Text txtTodayAttendance;
    public JFXTextField search;
    public Text txtallEmployee;
    public JFXComboBox selectMonth;
    public JFXDatePicker selectDate;
    public JFXRadioButton rBtnAllEmployee;
    public JFXRadioButton rBtnAttendance;
    public Text or;
    public Text txtCityDate;
    public Text txtOptionTime;
    public ScrollPane scrollPane;

    ArrayList<String> month = new ArrayList<>();

    public EmployeeFromController() {
        instance = this;
    }

    public static EmployeeFromController getInstance() {
        return instance;
    }


    public void loadAllId() {

        vBox.getChildren().clear();
        try {

            ResultSet ids = EmployeeController.loadAllIds();

            while (ids.next()) {
                System.out.println("id");
                LoadEmployeeData(ids.getString(1));
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void LoadEmployeeData(String id) {

        try {
            System.out.println(id);
            FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/bar/EmployeeDetailBar.fxml"));
            Parent root = loader.load();
            EmployeeDetailBar controller = loader.getController();
            controller.setData(id);
            vBox.getChildren().add(root);
        } catch (IOException e) {
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        //scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        scrollPane.lookup(".scroll-pane").setStyle("-fx-background-color: transparent");
        setSelectMonth();
        setTodayAttendance();
        setAllEmployee();
        loadAllId();
        rBtnAllEmployee.setSelected(true);
        if (rBtnAllEmployee.isSelected()) {
            txtCityDate.setText("City");
            txtOptionTime.setText("Option");
            or.setVisible(false);
            selectDate.setVisible(false);
            selectDate.setDisable(true);
            selectMonth.setDisable(true);
            selectMonth.setValue(false);
            rBtnAttendance.setSelected(false);
        } else {
            or.setVisible(true);
            selectDate.setVisible(true);
            selectMonth.setValue(true);
        }
        System.out.println("check");

    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.swatchNavigation("AdminDashBordFrom.fxml", actionEvent);
    }

    public void newEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.popupNavigation("AddEmployeeFrom.fxml");
    }

    public void searchOnKeyReleased(KeyEvent keyEvent) {
        vBox.getChildren().clear();
        try {
            if (search.getText().equals("")) {
                if (rBtnAllEmployee.isSelected()) {
                    loadAllId();
                }
                if (rBtnAttendance.isSelected()) {
                    LoadEmployeeAttendanceData();
                }
            } else {
                if (rBtnAllEmployee.isSelected()) {
                    ResultSet set = EmployeeController.getSearchData(search.getText());
                    while (set.next()) {
                        LoadEmployeeData(set.getString(1));
                    }
                }
                if (rBtnAttendance.isSelected()) {

                    ArrayList<EmployeeAttendanceProjection> employeeAttendance = EmployeeAttendanceModel.findEmployeeAttendance(search.getText());
                    for (EmployeeAttendanceProjection employeeAttendanceProjection : employeeAttendance) {
                        loadAttendanceBar(employeeAttendanceProjection);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void allEmployeeOnAction(ActionEvent actionEvent) {
        if (rBtnAllEmployee.isSelected()) {
            System.out.println("Load All ");
            txtCityDate.setText("City");
            txtOptionTime.setText("Option");
            loadAllId();
            or.setVisible(false);
            selectDate.setVisible(false);
            selectDate.setDisable(true);
            selectMonth.setDisable(true);
            selectMonth.setValue(false);
            rBtnAttendance.setSelected(false);
        } else {
            or.setVisible(true);
            selectDate.setVisible(true);
            selectMonth.setValue(true);
        }
    }

    public void attendOnAction(ActionEvent actionEvent) {
        if (rBtnAttendance.isSelected()) {
            txtCityDate.setText("Date");
            txtOptionTime.setText("Time");
            LoadEmployeeAttendanceData();
            selectDate.setDisable(false);
            selectMonth.setDisable(false);
            or.setVisible(true);
            selectDate.setVisible(true);
            selectMonth.setValue(true);
            rBtnAllEmployee.setSelected(false);
            System.out.println("clikAttendancOnAction");
        } else {
            or.setVisible(false);
            selectDate.setVisible(false);
            selectMonth.setValue(false);
        }
    }

    public void loadAttendanceBar(EmployeeAttendanceProjection employeeAttendanceProjection) {
        try {
            FXMLLoader loader = new FXMLLoader(EmployeeFromController.class.getResource("/lk/ijse/theGym/view/bar/EmployeeAttendaceBar.fxml"));
            Parent root = loader.load();
            EmployeeAttendanceBar controller = loader.getController();
            controller.setData(employeeAttendanceProjection);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LoadEmployeeAttendanceData() {
        vBox.getChildren().clear();
        try {
            ArrayList<EmployeeAttendanceProjection> list = EmployeeAttendanceModel.findAttendanceAndEmployee();
            for (EmployeeAttendanceProjection employeeAttendanceProjection : list) {
                loadAttendanceBar(employeeAttendanceProjection);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }


    private void setTodayAttendance() {
        try {
            txtTodayAttendance.setText(EmployeeAttendanceModel.countAttendanceByDate(DateTimeUtil.dateNow()));
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setAllEmployee() {
        try {
            ResultSet set = EmployeeController.getAllEmployeeCount();
            if (set.next()) {
                txtallEmployee.setText(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setSelectMonth() {
        if (month.isEmpty()) {
            month.add("");
            month.add("January");
            month.add("February");
            month.add("March");
            month.add("April");
            month.add("May");
            month.add("June");
            month.add("July");
            month.add("August");
            month.add("September");
            month.add("October");
            month.add("November");
            month.add("December");
        }
        selectMonth.getItems().addAll(month);
    }

    public void selectMonthOnAction(ActionEvent actionEvent) {

        if (!String.valueOf(selectMonth.getValue()).equals("")) {

            try {
                SimpleDateFormat year = new SimpleDateFormat("yyyy");

                for (int i = 0; i < month.size(); i++) {
                    if (month.get(i).equals(String.valueOf(selectMonth.getValue()))) {
                        String Index = String.valueOf(i);
                        String month = Index.length() == 1 ? "0" + Index : Index;
                        ArrayList<EmployeeAttendanceProjection> employeeAttendanceProjections = EmployeeAttendanceModel.findAttendanceEmployeeByDate(year.format(new Date()) + "-" + month);
                        if (!employeeAttendanceProjections.isEmpty()) {
                            vBox.getChildren().clear();
                            selectDate.setDisable(true);
                        } else {
                            selectDate.setDisable(false);
                            vBox.getChildren().clear();
                        }
                        for (EmployeeAttendanceProjection projection : employeeAttendanceProjections) {
                            loadAttendanceBar(projection);
                        }
                        break;
                    }
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else {
            LoadEmployeeAttendanceData();
            selectDate.setDisable(false);
        }


    }

    private void searchDate() {
        vBox.getChildren().clear();
        try {
            ArrayList<EmployeeAttendanceProjection> attendanceEmployeeByDate = EmployeeAttendanceModel.findAttendanceEmployeeByDate(DateTimeUtil.formatDatePatten(selectDate.getValue()));
            for (EmployeeAttendanceProjection projection:attendanceEmployeeByDate) {
                selectMonth.setDisable(true);
               loadAttendanceBar(projection);
            }
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }

    public void selectOnDateOnAction(ActionEvent actionEvent) {
        searchDate();

    }

    public void dateKeyReleased(KeyEvent keyEvent) {
        vBox.getChildren().clear();
        LoadEmployeeAttendanceData();
        selectMonth.setDisable(false);


    }
}
