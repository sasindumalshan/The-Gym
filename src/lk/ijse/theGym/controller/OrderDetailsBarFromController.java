package lk.ijse.theGym.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderDetailsBarFromController implements Initializable {
    public Text txtCustomerId;
    public Text txtDate;
    public Text txtTime;
    public Text txtOrderId;
    public Text txtTotal;
    public Button btn;

    public void setData(String customerId,String date,String time,String orderId,String total){
       txtCustomerId.setText(customerId);
       txtDate.setText(date);
       txtTime.setText(time);
       txtOrderId.setText(orderId);
       txtTotal.setText(total);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn.setVisible(false);
    }

    public void mm(MouseEvent mouseEvent) {
        btn.setVisible(true);
    }

    public void eee(MouseEvent mouseEvent) {
        btn.setVisible(false);
    }
}
