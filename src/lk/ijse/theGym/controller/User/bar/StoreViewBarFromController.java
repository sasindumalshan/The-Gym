package lk.ijse.theGym.controller.User.bar;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lk.ijse.theGym.dto.ItemDTO;

public class StoreViewBarFromController {
    public Text txtItemCode;
    public Text txtItemName;
    public Text txtBrand;
    public Text txtQTY;
    public Text txtPrice;
    public void setData(ItemDTO itemDTO){
        txtItemCode.setText(itemDTO.getItem_id());
        txtItemName.setText(itemDTO.getItem_name());
        txtBrand.setText(itemDTO.getBrand());
        txtQTY.setText(String.valueOf(itemDTO.getQut()));
        txtPrice.setText(String.valueOf(itemDTO.getPrice()));
    }


}
