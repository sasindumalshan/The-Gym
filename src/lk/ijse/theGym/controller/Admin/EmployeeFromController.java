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
import lk.ijse.theGym.model.EmployeeAttendanceController;
import lk.ijse.theGym.model.EmployeeController;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                    ResultSet set=EmployeeController.getSearchData(search.getText());
                    while (set.next()){
                        LoadEmployeeData(set.getString(1));
                    }
                }
                if (rBtnAttendance.isSelected()) {

                    ResultSet set1 = EmployeeAttendanceController.searchId(search.getText());
                while (set1.next()) {
                    setSearchData(set1.getString(1), set1.getString(2), set1.getString(3), set1.getString(4), set1.getString(5), set1.getString(6));
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

    public void setSearchData(String string, String set1String, String s, String string1, String set1String1, String s1) {
        FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/bar/EmployeeAttendaceBar.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EmployeeAttendanceBar controller = loader.getController();
        controller.setData(string, set1String, s, string1, set1String1, s1);
        vBox.getChildren().add(root);
    }

    public void LoadEmployeeAttendanceData() {
        vBox.getChildren().clear();
        try {
            ResultSet set = EmployeeAttendanceController.getAllDetails();
            while (set.next()) {

                FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/bar/EmployeeAttendaceBar.fxml"));
                Parent root = loader.load();
                EmployeeAttendanceBar controller = loader.getController();
                controller.setData(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getString(6));
                vBox.getChildren().add(root);

            }

        } catch (SQLException | ClassNotFoundException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setTodayAttendance() {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            ResultSet set = EmployeeAttendanceController.getTodayAttendance(dateFormat.format(new Date()));
            if (set.next()) {
                txtTodayAttendance.setText(set.getString(1));
            }
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
                        ResultSet set = EmployeeAttendanceController.searchDate(year.format(new Date()) + "-" + month);
                        if (set.next()) {
                            vBox.getChildren().clear();
                            selectDate.setDisable(true);
                        } else {
                            selectDate.setDisable(false);
                            vBox.getChildren().clear();
                        }
                        while (set.next()) {

                            FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/bar/EmployeeAttendaceBar.fxml"));
                            Parent root = loader.load();
                            EmployeeAttendanceBar controller = loader.getController();
                            controller.setData(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getString(6));
                            vBox.getChildren().add(root);
                        }
                        break;

                    }
                }
            } catch (SQLException | IOException | ClassNotFoundException throwables) {
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
            LocalDate date = selectDate.getValue();
            String dateNow = "0000-00-00";
            try {
                dateNow = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            } catch (NullPointerException e) {

            }
            ResultSet set = EmployeeController.searchDate(dateNow);
//            if (set.next()) {
//                selectMonth.setDisable(true);
//            }

            while (set.next()) {
                selectMonth.setDisable(true);
                System.out.println(set.getString(2));

                FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/bar/EmployeeAttendaceBar.fxml"));
                Parent root = loader.load();
                EmployeeAttendanceBar controller = loader.getController();
                controller.setData(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5), set.getString(6));
                vBox.getChildren().add(root);
            }
        } catch (SQLException | ClassNotFoundException | IOException throwables) {
            throwables.printStackTrace();
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
