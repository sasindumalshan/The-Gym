package lk.ijse.theGym.controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class PackageBarController {
    public Text txtPrice;
    public Text txtDuration;
    public Text txtPackage;
    public Text txtPackId;
    public Text txtUsage;

    public void setData(String id,String pack,String duration,String price,String usage) {
        txtPackage.setText(pack);
        txtDuration.setText(duration);
        txtPackId.setText(id);
        txtUsage.setText(usage);
        txtPrice.setText(price);
    }

    public void onMuseClick(MouseEvent mouseEvent) {
        PackageFromController.getInstance().setData(txtPackage.getText(),txtDuration.getText(),txtPrice.getText(),txtPackId.getText());
    }
}
