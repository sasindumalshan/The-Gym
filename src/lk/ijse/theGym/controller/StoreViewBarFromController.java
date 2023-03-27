package lk.ijse.theGym.controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class StoreViewBarFromController {
    public Text txtItemCode;
    public Text txtItemName;
    public Text txtBrand;
    public Text txtQTY;
    public Text txtPrice;
    public void setData(String itemCode,String itemName,String brand,String qty,String price){
        txtItemCode.setText(itemCode);
        txtItemName.setText(itemName);
        txtBrand.setText(brand);
        txtQTY.setText(qty);
        txtPrice.setText(price);
    }


}
