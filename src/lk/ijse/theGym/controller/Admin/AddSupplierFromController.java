package lk.ijse.theGym.controller.Admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.theGym.model.SupplierController;
import lk.ijse.theGym.to.Supplier;
import lk.ijse.theGym.util.Navigation;
import lk.ijse.theGym.util.Notification;
import lk.ijse.theGym.util.RegexUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddSupplierFromController {
    public JFXTextField txtMobileNo;
    public JFXTextField txtCompanyName;
    public JFXTextField txtLocation;
    public JFXTextField txtEmail;
    public JFXButton btnAdd;

    public void addOnAction(ActionEvent actionEvent) {
        if (txtEmail.getText().equals("") | txtLocation.getText().equals("")| txtCompanyName.getText().equals("")| txtMobileNo.getText().equals("")){
            Notification.notificationWARNING("Please Entre data","Empty Data ");
        }else {
            try {
                boolean isAdded = SupplierController.addNewSupplier(new Supplier(txtCompanyName.getText(), getNextId(), txtEmail.getText(), txtMobileNo.getText(), txtLocation.getText()));
                if (isAdded) {
                    Notification.notification("Successful Added","Suppler is Added");
                    SupplierFromController.getInstance().setAllSuppliersCount();
                    if (!SupplierFromController.getInstance().rBAllSuppliers.isSelected()) {
                        SupplierFromController.getInstance().rBAllSuppliers.fire();
                    } else {
                        SupplierFromController.getInstance().setAllIdForLoadAllSupplier();
                    }
                    Navigation.close(actionEvent);
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private String getNextId() {
        String id=null;
        try {
            ResultSet set = SupplierController.getLarstId();
            while (set.next()) {
                id=set.getString(1);
            }
            try {
                String[] S = id.split("S00");
                int Next= Integer.parseInt(S[1]);
                Next++;
                return "S00"+Next;
            }catch (NullPointerException e){
                return "S001";
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return "S001";

        }
//===============================================================
    public void mobile(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtMobileNo, txtMobileNo.getText(), "0((11)|(7(7|0|8|4|9|1|[3-7]))|(3[1-8])|(4(1|5|7))|(5(1|2|4|5|7))|(6(3|[5-7]))|([8-9]1))[0-9]{7}", "-fx-text-fill: white");

    }

    public void companyName(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtCompanyName, txtCompanyName.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white");
    }

    public void location(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtLocation, txtLocation.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white");

    }

    public void email(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, txtEmail, txtEmail.getText(), "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{3,4}$", "-fx-text-fill: white");
    }

    public void close(MouseEvent mouseEvent) {
        Navigation.close(mouseEvent);
    }
}
