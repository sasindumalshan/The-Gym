package lk.ijse.theGym.controller;

import javafx.scene.text.Text;

public class OrderBarController {
    public Text txtName;
    public Text txtPrice;
    public Text txtQTY;
    public Text txtId;

    public void setData(String id, String name, String price, String qty) {
        txtId.setText(id);
        txtName.setText(name);
        txtPrice.setText(price);
        txtQTY.setText(qty);
    }
}
