package lk.ijse.theGym.controller.Admin;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.theGym.controller.User.OrderBarController;
import lk.ijse.theGym.dto.ItemDTO;
import lk.ijse.theGym.model.ItemModel;
import lk.ijse.theGym.modelController.SupplierController;
import lk.ijse.theGym.model.SupplierOrderModel;
import lk.ijse.theGym.to.SupplierOrder;
import lk.ijse.theGym.to.SupplierOrderDetails;
import lk.ijse.theGym.util.DateTimeUtil;
import lk.ijse.theGym.util.Navigation;
import lk.ijse.theGym.util.RegexUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierOrderFromController implements Initializable {

    public static ArrayList<SupplierOrderDetails> orderDetails = new ArrayList<>();
    public ScrollPane scrollPane;
    public VBox vBox;
    public JFXTextField lblItemCode;
    public JFXButton btnOder;
    public Text txtTotal;
    public Text txtName;
    public JFXTextField lblCustomerId;
    public JFXTextField lblQty;
    public JFXButton btnAdd;
    public JFXTextField lblPrice;
    public Text txtBalance;
    public Text txtItemsName;
    ArrayList<String> name = new ArrayList<>();

    private void navigation(String id, String name, String price, String qty) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/theGym/view/bar/Orderbar.fxml"));
            Parent root = loader.load();
            OrderBarController controller = loader.getController();
            controller.setData(id, name, price, qty);
            vBox.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void OkOrderOnAction(ActionEvent actionEvent) {
        if (Double.parseDouble(txtTotal.getText()) > 0) {
            try {
                if (SupplierOrderModel.placeSupplerOrder(orderDetails,
                        new SupplierOrder(getNextId(),
                                txtTotal.getText(),
                                lblCustomerId.getText(),
                                DateTimeUtil.dateNow(),
                                DateTimeUtil.timeNow()
                        ))) {
                    orderDetails.clear();
                    txtName.setText("");
                    lblCustomerId.clear();
                    Navigation.close(actionEvent);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            btnOder.setDisable(true);
        }

    }

    private String getNextId() {
        String existsId = null;
        try {
            List<String> idList = SupplierOrderModel.findIdOrderByLength();
            for (String id : idList) {
                existsId = id;
            }
            try {
                String[] os = existsId.split("O");
                int nextId = Integer.parseInt(os[1]);
                nextId++;
                return "O" + nextId;
            } catch (NullPointerException e) {
                return "O1";
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void itemCodeOnAction(ActionEvent actionEvent) {
        lblQty.requestFocus();
    }

    public void itemCodeOnKeyReleased(KeyEvent keyEvent) {
        btnAdd.setDisable(lblItemCode.getText().equals(""));
        searchItemCode();
    }

    private void searchItemCode() {
        try {
            ItemDTO itemDTO = ItemModel.findById(lblItemCode.getText());
            txtItemsName.setText(itemDTO.getItem_name());
            lblQty.requestFocus();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void itemCodeOnKeyTyped(KeyEvent keyEvent) {
        searchItemCode();
    }

    public void supIdOnKeyTyped(KeyEvent keyEvent) {
        searchSupperId();
    }

    public void supIdReleased(KeyEvent keyEvent) {
        searchSupperId();
    }

    private void searchSupperId() {
        try {
            ResultSet set = SupplierController.getComName(lblCustomerId.getText());
            if (set.next()) {
                txtName.setText(set.getString(1));
                lblItemCode.requestFocus();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void qtyOnKeyReleased(KeyEvent keyEvent) {
        if (RegexUtil.regex(btnAdd, lblQty, lblQty.getText(), "^[\\d]{0,15}$", "-fx-text-fill: #FBA23E")) {

            btnAdd.setDisable(lblQty.getText().equals(""));
        }

    }

    public void priceOnKeyReleased(KeyEvent keyEvent) {
        if (RegexUtil.regex(btnAdd, lblPrice, lblPrice.getText(), "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$", "-fx-text-fill: #FBA23E")) {
            btnAdd.setDisable(lblPrice.getText().equals(""));
        }
    }

    public void supIdOnAction(ActionEvent actionEvent) {
        lblItemCode.requestFocus();
    }

    public void qtyOnAction(ActionEvent actionEvent) {
        lblPrice.requestFocus();
    }

    public void priceOnAction(ActionEvent actionEvent) {
        btnAdd.fire();
    }

    public void addOnAction(ActionEvent actionEvent) {
        if (lblQty.getText().equals("") | lblPrice.getText().equals("") | lblItemCode.getText().equals("")) {
            btnAdd.setDisable(true);
        } else {
            if (orderDetails.isEmpty()) {
                orderDetails.add(new SupplierOrderDetails(
                        null,
                        lblItemCode.getText(),
                        lblQty.getText(),
                        lblPrice.getText()
                ));
                name.add(txtItemsName.getText());
            }
            boolean isNoDuplicate = true;
            for (int i = 0; i < orderDetails.size(); i++) {
                if (orderDetails.get(i).getItem_code().equals(lblItemCode.getText())) {
                    orderDetails.get(i).setQut(String.valueOf(Integer.parseInt(orderDetails.get(i).getQut()) + Integer.parseInt(lblQty.getText())));
                    orderDetails.get(i).setPrice(lblPrice.getText());
                    isNoDuplicate = false;
                }
            }
            if (isNoDuplicate) {
                orderDetails.add(new SupplierOrderDetails(
                        null,
                        lblItemCode.getText(),
                        lblQty.getText(),
                        lblPrice.getText()
                ));
                name.add(txtItemsName.getText());
            }
            total();
            clear();
            lblItemCode.requestFocus();
        }
        setDataNavigation();
    }

    private void setDataNavigation() {
        vBox.getChildren().clear();
        for (int i = 0; i < orderDetails.size(); i++) {
            navigation(orderDetails.get(i).getItem_code(), name.get(i), orderDetails.get(i).getPrice(), orderDetails.get(i).getQut());
        }
    }

    private void total() {
        double total = 0;
        for (int i = 0; i < orderDetails.size(); i++) {
            total += Double.parseDouble(orderDetails.get(i).getPrice());
        }
        txtTotal.setText(String.valueOf(total));
        if (Double.parseDouble(txtTotal.getText()) > 0) {
            btnOder.setDisable(false);
        }
    }

    private void clear() {
        lblItemCode.clear();
        txtItemsName.setText("");
        lblQty.clear();
        lblPrice.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> lblCustomerId.requestFocus());
    }

    public void close(MouseEvent mouseEvent) {
        Navigation.close(mouseEvent);
    }
}
