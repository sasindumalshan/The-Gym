package lk.ijse.theGym.controller;

import javafx.scene.text.Text;

public class CustomerOrderBarController {


    public Text code;
    public Text name;
    public Text price;
    public Text qty;

    public void setData(String itemCode, String itemName, String price, String qty) {

        code.setText(itemCode);
        name.setText(itemName);
        this.price.setText(price);
        this.qty.setText(qty);
    }
}
