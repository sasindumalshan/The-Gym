package lk.ijse.theGym.controller.User.bar;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.theGym.dto.OrderDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderDetailsBarFromController implements Initializable {
    public Text txtCustomerId;
    public Text txtDate;
    public Text txtTime;
    public Text txtOrderId;
    public Text txtTotal;
    public Button btn;

    public void setData(OrderDTO orderDTO){
       txtCustomerId.setText(orderDTO.getCustomer_id());
       txtDate.setText(orderDTO.getDate());
       txtTime.setText(orderDTO.getTime());
       txtOrderId.setText(orderDTO.getOrder_id());
       txtTotal.setText(String.valueOf(orderDTO.getFinal_total()));
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
