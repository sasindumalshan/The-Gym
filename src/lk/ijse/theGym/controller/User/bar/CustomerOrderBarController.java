package lk.ijse.theGym.controller.User.bar;

import javafx.scene.text.Text;
import lk.ijse.theGym.dto.OrderDetailsDTO;
import lk.ijse.theGym.model.ItemModel;

import java.sql.SQLException;

public class CustomerOrderBarController {


    public Text code;
    public Text name;
    public Text price;
    public Text qty;

    public void setData(OrderDetailsDTO orderDetailsDTO) throws SQLException, ClassNotFoundException {
        code.setText(orderDetailsDTO.getItem_id());
        name.setText(ItemModel.findById(orderDetailsDTO.getItem_id()).getItem_name());
        this.price.setText(String.valueOf(orderDetailsDTO.getPrice()));
        this.qty.setText(String.valueOf(orderDetailsDTO.getQut()));
    }
}
