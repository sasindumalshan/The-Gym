package lk.ijse.theGym.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportFromController implements Initializable {
    private static int lost;
    private static int profit;
    public Text txtTodayOrders;
    public JFXTextField search;
    public Text txtMonthlyOrders;
    public Text txtAllCustomer;
    public Text txtReportStatus;
    public Pane pane;
    public JFXButton btnReport;

    public static void setStatus(ArrayList<String> lost, ArrayList<String> profit) {
        for (String getLost : lost) {
            ReportFromController.lost += Integer.parseInt(getLost);
        }
        for (String getProfit : profit) {
            ReportFromController.profit += Double.parseDouble(getProfit);
        }

    }

    private void setStatus() {

        if (lost > profit) {
            txtReportStatus.setText("Lost");
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.swatchNavigation("AdminDashBordFrom.fxml", actionEvent);
    }

    public void searchKeyReleased(KeyEvent keyEvent) {

    }

    public void salaryOnAction(ActionEvent actionEvent) {
        navigation("SalaryFom.fxml");
    }

    public void reportOnAction(ActionEvent actionEvent) {
        navigation("ReportChartsfrom.fxml");
        setStatus();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnReport.fire();
    }

    public void packOnAction(ActionEvent actionEvent) {
        navigation("PackageFrom.fxml");
    }

    private void navigation(String url) {
        pane.getChildren().clear();
        try {
            FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/" + url));
            Parent root = loader.load();
            pane.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
