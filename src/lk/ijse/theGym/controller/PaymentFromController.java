package lk.ijse.theGym.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import lk.ijse.theGym.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentFromController implements Initializable {
    public Pane pane;
    public JFXButton btnMonthlyPayment;

    public void monthlyPaymentOnAction(ActionEvent actionEvent) throws IOException {
        navigation("MonthlyPaymentFrom.fxml");
    }

    public void paymentDetailsOnAction(ActionEvent actionEvent) throws IOException {
        navigation("MonthlyPaymentDetailsFrom.fxml");
    }

    public void OrdesOnAction(ActionEvent actionEvent) throws IOException {
        navigation("CustomerOrderFrom.fxml");
    }

    public void OrdersDetailsOnAction(ActionEvent actionEvent) throws IOException {
        navigation("OrderDetailsFrom.fxml");
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.swatchNavigation("CashiarDashBordFrom.fxml",actionEvent);
    }

    private void navigation(String url) throws IOException {
        pane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/" + url));
        Parent root = loader.load();
        pane.getChildren().add(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnMonthlyPayment.fire();
    }

}
