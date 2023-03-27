package lk.ijse.theGym.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lk.ijse.theGym.model.OrderDetailsController;
import lk.ijse.theGym.util.DateTimeUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderDetailsFromController implements Initializable {
    public AnchorPane anchorpane;
    public ScrollPane scrollPane;
    public VBox vBox;
    public JFXTextField search;
    public JFXComboBox comboSelectMonth;
    public JFXComboBox comboSelectYear;
    String[] allMonth = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    ArrayList<String> searchIds = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorpane.setStyle("-fx-background-color: transparent");
        setComboMonth();
        setComboYear();
        String[] split = DateTimeUtil.dateNow().split("-");
        setData(split[0] + "-" + split[1] + "-");
        comboSelectYear.setValue(DateTimeUtil.yearNow());
        comboSelectMonth.setValue(DateTimeUtil.monthNow());
    }

    private void setData(String date) {
        vBox.getChildren().clear();
        try {
            ResultSet set = OrderDetailsController.getAllData(date);
            while (set.next()) {
                navigation(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5));
            }
        } catch (SQLException | IOException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    ArrayList<String> year = new ArrayList<>();

    private void setComboYear() {
        try {
            year.clear();
            year.add(DateTimeUtil.yearNow());

            ResultSet set = OrderDetailsController.getAllYears();
            while (set.next()) {
                for (int i = 0; i < year.size(); i++) {
                    String[] split = set.getString(1).split("-");

                    if (!year.get(i).equals(split[0])) {
                        year.add(split[0]);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        comboSelectYear.getItems().addAll(year);
    }

    private void setComboMonth() {
        comboSelectMonth.getItems().addAll(allMonth);
    }

    private void navigation(String customerId, String date, String time, String orderId, String total) throws IOException {
        FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/bar/OrderDetailsBarFrom.fxml"));
        Parent root = loader.load();
        OrderDetailsBarFromController controller = loader.getController();
        controller.setData(customerId, date, time, orderId, total);
        vBox.getChildren().add(root);
    }

    public void searchOnKeyReleased(KeyEvent keyEvent) {
        vBox.getChildren().clear();
        if (search.getText().equals("")){
            System.out.println("empty");
            selectComboBox();

        }else {
            searchIds.clear();
            vBox.getChildren().clear();
            try {
                ResultSet set = OrderDetailsController.searchIDOrCustomerId(search.getText());
                while (set.next()) {
                    for (int i = 0; i < searchIds.size(); i++) {
                        if (!searchIds.get(i).equals(set.getString(1))) {
                            searchIds.add(set.getString(1));
                        }
                    }
                    if (searchIds.isEmpty()) {
                        searchIds.add(set.getString(1));
                    }

                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
            getAllDataForOrderId();

        }


    }

    private void getAllDataForOrderId() {
        vBox.getChildren().clear();
        try {
            for (int i = 0; i < searchIds.size(); i++) {
                ResultSet set=OrderDetailsController.getDataForOrderId(searchIds.get(i));
                while (set.next()){
                    navigation(set.getString(1), set.getString(2), set.getString(3), set.getString(4), set.getString(5));
                }
            }
        } catch (SQLException | ClassNotFoundException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    public void comboSelectMonthOnAction(ActionEvent actionEvent) {
        selectComboBox();
    }

    private void selectComboBox() {
        String monthIndex = null;
        for (int i = 0; i < allMonth.length; i++) {
            if (allMonth[i].equals(String.valueOf(comboSelectMonth.getValue()))) {
                i++;
                monthIndex = String.valueOf(i);
                monthIndex = monthIndex.length() == 1 ? "0" + monthIndex : monthIndex;
            }
        }
        System.out.println(monthIndex);
        setData(comboSelectYear.getValue() + "-" + monthIndex + "-");
    }

    public void comboSelectYearOnAction(ActionEvent actionEvent) {
        selectComboBox();
    }
}
