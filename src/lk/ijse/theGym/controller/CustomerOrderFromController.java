package lk.ijse.theGym.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.model.CustomerController;
import lk.ijse.theGym.model.ItemsController;
import lk.ijse.theGym.model.OrderController;
import lk.ijse.theGym.util.DateTimeUtil;
import lk.ijse.theGym.util.Notification;
import lk.ijse.theGym.util.RegexUtil;
import lk.ijse.theGym.view.data.Order;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CustomerOrderFromController implements Initializable {
    public AnchorPane anchorpane;
    public ScrollPane scrollPane;
    public VBox vBox;
    public JFXTextField search;
    public JFXTextField lblItemCode;
    public JFXTextField lblBalance;
    public Text txtTotal;
    public Text txtItemsName;
    public Text txtNic;
    public Text txtBalance;
    public Text txtName;
    public JFXTextField lblCustomerId;
    public Text txtQty;
    public Text txtPrice;
    public JFXTextField lblQty;
    public JFXButton btnAdd;
    public JFXButton btnPay;
    ArrayList<Order> ids = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorpane.setStyle("-fx-background-color: transparent");
        lblBalance.setEditable(false);
        lblQty.setEditable(false);
        lblItemCode.setEditable(false);
        Platform.runLater(() -> lblCustomerId.requestFocus());
        setData();


    }

    public void customerIdOnAction(ActionEvent actionEvent) {
        if (lblCustomerId.getText().equals("")) {
            lblCustomerId.requestFocus();
        } else {
            lblItemCode.requestFocus();
        }
    }

    public void itemCodeOnAction(ActionEvent actionEvent) {
        lblItemCode.requestFocus();
    }


    public void balanceOnKeyReleased(KeyEvent keyEvent) {
        if (RegexUtil.regex(btnAdd,lblBalance,lblBalance.getText(),"^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$","-fx-text-fill: #FBA23E")){

            if (!lblBalance.getText().equals("")) {
                double balance = Double.parseDouble(lblBalance.getText()) - Double.parseDouble(txtTotal.getText());
                if (balance >= 0) {
                    lblBalance.setStyle("-fx-border-color: white");
                    btnPay.setDisable(false);
                } else {
                    lblBalance.setStyle("-fx-border-color: red");
                    btnPay.setDisable(true);
                }
                txtBalance.setText(String.valueOf(balance));

            } else {
                txtBalance.setText("00.00");
            }
        }

    }


    public void qtyOnAction(ActionEvent actionEvent) {
        btnAdd.fire();
    }


    public void addOnAction(ActionEvent actionEvent) {

        if (lblItemCode.getText().equals("") | lblQty.getText().equals("")) {
            btnAdd.setDisable(true);
            btnPay.setDisable(true);
        } else {

            setDataForIds();
            setTotal();
            lblItemCode.setText("");
            lblQty.setText("");
            txtItemsName.setText("");
            txtPrice.setText("");
            txtQty.setText("");
            Platform.runLater(() -> lblItemCode.requestFocus());
        }
        setData();

    }

    private void setDataForIds() {

        if (ids.isEmpty()) {
            System.out.println("is emplty");
            ids.add(new Order(lblItemCode.getText(), txtItemsName.getText(), txtPrice.getText(), lblQty.getText()));
        } else {
            boolean isDuplicate = true;
            System.out.println("on else");

            L1:
            for (int i = 0; i < ids.size(); i++) {
                System.out.println(i);
                if (ids.get(i).getId().equals(lblItemCode.getText())) {
                    System.out.println(ids.get(i).getId());
                    System.out.println(lblItemCode.getText());
                    System.out.println("on if");
                    ids.get(i).setItemName(txtItemsName.getText());
                    ids.get(i).setPrice(txtPrice.getText());
                    int qty = 0;
                    qty = Integer.parseInt(lblQty.getText()) + Integer.parseInt(ids.get(i).getQty());
                    System.out.println(qty);
                    ids.get(i).setQty(String.valueOf(qty));
                    isDuplicate = false;
                    break L1;
                }
            }
            if (isDuplicate) {
                System.out.println("!if");
                ids.add(new Order(lblItemCode.getText(), txtItemsName.getText(), txtPrice.getText(), lblQty.getText()));
            }

            System.out.println("ids.size()" + ids.size());
        }


    }

    private void setData() {
        vBox.getChildren().clear();
        try {
            for (int i = 0; i < ids.size(); i++) {
                navigation(ids.get(i).getId(), ids.get(i).getItemName(), ids.get(i).getPrice(), ids.get(i).getQty());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void itemCodeOnKeyReleased(KeyEvent keyEvent) {
        setItemCodeDetails();
    }

    public void itemCodeOnKeyTyped(KeyEvent keyEvent) {
        setItemCodeDetails();
    }

    public void customerIdReleased(KeyEvent keyEvent) {
        setCustomerDetails();
    }


    private void setCustomerDetails() {
        try {
            if (isIdExistOnCustomerTable(lblCustomerId.getText())) {
                lblCustomerId.setStyle("-fx-border-color: white");
                lblItemCode.requestFocus();
                ResultSet set = CustomerController.getIdData(lblCustomerId.getText());
                if (set.next()) {
                    txtName.setText(set.getString(2) + " " + set.getString(3));
                    txtNic.setText(set.getString(9));
                    lblCustomerId.setEditable(false);
                    lblItemCode.setEditable(true);
                    lblQty.setEditable(false);
                    lblBalance.setEditable(false);
                }

            } else {
                lblCustomerId.setStyle("-fx-border-color: red");
                btnAdd.setDisable(true);
                btnPay.setDisable(true);
                lblItemCode.setEditable(false);
                lblQty.setEditable(false);
                lblBalance.setEditable(false);

            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setItemCodeDetails() {
        try {
            if (lblItemCode.getText().equals("")) {
                txtItemsName.setText("");
                txtQty.setText("");
                txtPrice.setText("");
            } else {
                if (ItemsController.isExistId(lblItemCode.getText())) {
                    lblItemCode.setStyle("-fx-border-color: white");
                    lblQty.setEditable(true);
                    lblBalance.setEditable(true);
                    btnAdd.setDisable(false);
                    ResultSet set = ItemsController.getItemsForId(lblItemCode.getText());
                    if (set.next()) {
                        txtItemsName.setText(set.getString(2));
                        L1:
                        for (int i = 0; i < ids.size(); i++) {
                            if (ids.get(i).getId().equals(lblItemCode.getText())) {
                                txtQty.setText(String.valueOf(Integer.parseInt(set.getString(4)) - Integer.parseInt(ids.get(i).getQty())));
                                break L1;
                            } else {
                                txtQty.setText(set.getString(4));
                            }
                        }
                        if (ids.isEmpty()) {
                            txtQty.setText(set.getString(4));

                        }
                        txtPrice.setText(set.getString(5));
                        lblQty.requestFocus();
                        currentQty = txtQty.getText();
                    }
                } else {
                    txtItemsName.setText("");
                    txtTotal.setText("");
                    txtQty.setText("");
                    txtPrice.setText("");
                    lblQty.setText("");
                    lblItemCode.setStyle("-fx-border-color: red");
                    lblQty.setEditable(false);
                    lblBalance.setEditable(false);
                    btnAdd.setDisable(true);
                    btnPay.setDisable(true);
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    String currentQty;

    public void qtyOnKeyReleased(KeyEvent keyEvent) {

        if (RegexUtil.regex(btnAdd,lblQty,lblQty.getText(),"^[\\d]{0,15}$","-fx-text-fill: #FBA23E")){
            if (!lblQty.getText().equals("")) {
                btnAdd.setDisable(false);
                int qty = Integer.parseInt(currentQty) - Integer.parseInt(lblQty.getText());
                if (qty >= 0) {
                    lblQty.setStyle("-fx-border-color: white");
                    txtQty.setText(String.valueOf(qty));
                } else {
                    lblQty.setStyle("-fx-border-color: red");
                    lblQty.setText("");
                    txtQty.setText(currentQty);
                    btnAdd.setDisable(true);
                    btnPay.setDisable(true);
                }


            } else {

                txtQty.setText(currentQty);
                btnAdd.setDisable(true);
            }
        }



    }

    public void customerIdOnKeyTyped(KeyEvent keyEvent) {
        setCustomerDetails();

    }


    private boolean isIdExistOnCustomerTable(String customer_id) throws SQLException, ClassNotFoundException {
        return CustomerController.idExists(customer_id);
    }
static String oid=null;
    public void payOnAction(ActionEvent actionEvent) {
        try {
            if ( lblCustomerId.getText().equals("")  | lblBalance.getText().equals("")) {
                Notification.notificationWARNING("Place Enter Order"," Order Error ");
                btnPay.setDisable(true);
            } else {
                oid=nextOrderId();
                if (OrderController.PlaceOrder(new lk.ijse.theGym.to.Order(
                                oid,
                                DateTimeUtil.timeNow(),
                                DateTimeUtil.dateNow(),
                                Double.parseDouble(txtTotal.getText()),
                                lblCustomerId.getText()),
                        ids
                )){
                    lblCustomerId.requestFocus();
                    txtName.setText("");
                    txtNic.setText("");
                    lblCustomerId.clear();
                    lblBalance.clear();
                    txtBalance.setText("");
                    txtTotal.setText("");
                    ids.clear();
                    vBox.getChildren().clear();
                    Notification.notification(" Successfully Pay  "," Successfully pay monthly payment");

//                    ==========================================================

                    InputStream resource = this.getClass().getResourceAsStream("/lk/ijse/theGym/view/report/TheGymBill.jrxml");
                    HashMap<String, Object> hm = new HashMap<>();
                    hm.put("oId", oid);
                    try {
                        JasperReport jasperReport = JasperCompileManager.compileReport(resource);
                        JasperPrint print = JasperFillManager.fillReport(jasperReport, hm, DBConnection.getInstance().getConnection());
                        JasperViewer.viewReport(print, false);
                    } catch (JRException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private String nextOrderId() {
        String id=null;
     try {
         ResultSet set=OrderController.getLastOrderId();
        while (set.next()){
             id=set.getString(1);
         }
        try {
            String[] O = id.split("O00");
            int n= Integer.parseInt(O[1]);
            n++;
            return "O00"+n;
        }catch (NullPointerException e){
            return "O001" ;
        }

     } catch (SQLException | ClassNotFoundException throwables) {
         throwables.printStackTrace();
     }

        return "O001" ;
    }

    private void navigation(String itemCode, String itemName, String price, String qty) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/theGym/view/bar/CustomerOrderBar.fxml"));
        Parent root = loader.load();
        CustomerOrderBarController controller = loader.getController();
        controller.setData(itemCode, itemName, price, qty);
        vBox.getChildren().add(root);
    }

    private void setTotal() {
        double total = 0;
        for (int i = 0; i < ids.size(); i++) {
            total += Double.parseDouble(ids.get(i).getPrice()) * Double.parseDouble(ids.get(i).getQty());
        }
        txtTotal.setText(String.valueOf(total));
    }

}
