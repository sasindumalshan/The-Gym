package lk.ijse.theGym.controller.User;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.theGym.model.CustomerController;
import lk.ijse.theGym.model.PackController;
import lk.ijse.theGym.to.Package;
import lk.ijse.theGym.util.Notification;
import lk.ijse.theGym.util.RegexUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PackageFromController implements Initializable {
    private static PackageFromController instance;
    private static String id;
    public JFXTextField lblPackage;
    public JFXTextField lblPrice;
    public JFXTextField lblDuration;
    public JFXButton btnAdd;
    public VBox Vbox;
    public AnchorPane anchorPane;
    public Text txtName1;
    private String price;

    public PackageFromController() {
        instance = this;
    }

    public static PackageFromController getInstance() {
        return instance;
    }

    public void setData(String pack, String duration, String price, String id) {
        btnAdd.setText("OK");
        lblPackage.setEditable(false);
        lblDuration.setEditable(false);
        lblPackage.setText(pack);
        lblDuration.setText(duration);
        lblPrice.setText(price);
        this.price = price;
        PackageFromController.id = id;
    }

    public void addOnAction(ActionEvent actionEvent) {
        if (lblPackage.getText().equals("") | lblPrice.getText().equals("") | lblDuration.getText().equals("")) {
            Notification.notificationWARNING("Place Add Data ", "No Data");
        } else {
            try {
                if (btnAdd.getText().equals("ADD NEW PACKAGE")) {
                    if (lblDuration.getText().equals("") | lblPackage.getText().equals("") | lblPrice.getText().equals("")) {
//                        new Alert(Alert.AlertType.WARNING, "please enter data ").show();
                        Notification.notificationWARNING("Place Add Data ", "No Data");
                    } else {
                        if (PackController.addPackage(new Package(
                                lblPrice.getText(),
                                lblPackage.getText(),
                                lblDuration.getText(),
                                getPackageId()
                        ))) {
                            lblPackage.clear();
                            lblDuration.clear();
                            lblPrice.clear();
                            setPackData();
//                            new Alert(Alert.AlertType.CONFIRMATION, "Ok").show();
                            Notification.notification("Successful Data Added", "OK lest go");

                        }
                    }

                }
                if (btnAdd.getText().equals("UPDATE PACKAGE")) {
                    if (lblDuration.getText().equals("") | lblPackage.getText().equals("") | lblPrice.getText().equals("")) {
                        new Alert(Alert.AlertType.WARNING, "please enter data ").show();
                    } else {
                        if (PackController.update(
                                new Package(

                                        lblPrice.getText(),
                                        lblPackage.getText(),
                                        lblDuration.getText(),
                                        id
                                )
                        )) {
                            setPackData();
                            new Alert(Alert.AlertType.CONFIRMATION, "Ok").show();
                        }
                        lblPackage.clear();
                        lblDuration.clear();
                        lblPrice.clear();
                        btnAdd.setText("ADD NEW PACKAGE");
                    }

                }
                if (btnAdd.getText().equals("OK")) {
                    lblPackage.clear();
                    lblDuration.clear();
                    lblPrice.clear();
                    lblPackage.setEditable(true);
                    lblDuration.setEditable(true);
                    btnAdd.setText("ADD NEW PACKAGE");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private String getPackageId() {
        String id=null;
        try {
            ResultSet set = PackController.getLastId();
            while (set.next()) {
                id=set.getString(1);
            }
            try {
                String[] P = id.split("P");
                int n= Integer.parseInt(P[1]);
                n++;
                return "P"+n;
            }catch (NullPointerException e){
                return "P1";
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return "P1";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAdd.setText("ADD NEW PACKAGE");
        setPackData();
        anchorPane.setStyle("-fx-background-color: transparent");
    }

    private void setPackData() {
        Vbox.getChildren().clear();
        try {
            ResultSet set = PackController.getAll();
            while (set.next()) {
                ResultSet resultSet = CustomerController.getPackgeUsageCount(set.getString(4));
                if (resultSet.next()) {
                    navigation(set.getString(4), set.getString(2), set.getString(3), set.getString(1), resultSet.getString(1));

                } else {
                    navigation(set.getString(4), set.getString(2), set.getString(3), set.getString(1), "0");
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void priceKeyReleased(KeyEvent keyEvent) {
        if (RegexUtil.regex(btnAdd, lblPrice, lblPrice.getText(), "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$", "-fx-text-fill: white")) {
            if (!price.equals(lblPrice.getText())) {
                btnAdd.setText("UPDATE PACKAGE");
            } else {
                btnAdd.setText("OK");
            }
        }
    }

    private void navigation(String id, String pack, String duration, String price, String usage) {
        try {
            FXMLLoader loader = new FXMLLoader(StoreFromController.class.getResource("/lk/ijse/theGym/view/bar/packageBar.fxml"));
            Parent root = loader.load();
            PackageBarController controller = loader.getController();
            controller.setData(id, pack, duration, price, usage);
            Vbox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void packageOnKeyrelesed(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, lblPackage, lblPackage.getText(), "\\b([a-z]|[A-Z])+", "-fx-text-fill: white");
    }

    public void durationOnKeyReleased(KeyEvent keyEvent) {
        RegexUtil.regex(btnAdd, lblPackage, lblPackage.getText(), "^[\\d]{0,15}$", "-fx-text-fill: white");

    }
}
