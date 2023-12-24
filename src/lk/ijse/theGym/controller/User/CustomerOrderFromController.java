package lk.ijse.theGym.controller.User;

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
import lk.ijse.theGym.controller.User.bar.CustomerOrderBarController;
import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.dto.ItemDTO;
import lk.ijse.theGym.dto.OrderDTO;
import lk.ijse.theGym.dto.OrderDetailsDTO;
import lk.ijse.theGym.modelController.CustomerController;
import lk.ijse.theGym.model.ItemModel;
import lk.ijse.theGym.model.OrdersModel;
import lk.ijse.theGym.util.DateTimeUtil;
import lk.ijse.theGym.util.Notification;
import lk.ijse.theGym.util.RegexUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerOrderFromController implements Initializable {
    static String oid = null;
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
    //ArrayList<Order> ids = new ArrayList<>();
    ArrayList<OrderDetailsDTO> orderDetailsDTOS = new ArrayList<>();
    String currentQty;

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
        if (RegexUtil.regex(btnAdd, lblBalance, lblBalance.getText(), "^([+-]?[0-9]+(?:\\.[0-9]{0,4})?)$", "-fx-text-fill: #FBA23E")) {

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

        if (orderDetailsDTOS.isEmpty()) {
            OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
            orderDetailsDTO.setItem_id(lblItemCode.getText());
            orderDetailsDTO.setPrice(Double.parseDouble(txtPrice.getText()));
            orderDetailsDTO.setQut(Integer.parseInt(lblQty.getText()));
            orderDetailsDTOS.add(orderDetailsDTO);
        } else {
            boolean isDuplicate = true;
            L1:
            for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
                if (orderDetailsDTO.getItem_id().equals(lblItemCode.getText())) {
                    orderDetailsDTO.setPrice(Double.parseDouble(txtPrice.getText()));
                    orderDetailsDTO.setQut((Integer.parseInt(lblQty.getText()) + Integer.parseInt(String.valueOf(orderDetailsDTO.getQut()))));
                    isDuplicate = false;
                    break L1;
                }
            }
            if (isDuplicate) {
                OrderDetailsDTO orderDetailsDTO = new OrderDetailsDTO();
                orderDetailsDTO.setItem_id(lblItemCode.getText());
                orderDetailsDTO.setPrice(Double.parseDouble(txtPrice.getText()));
                orderDetailsDTO.setQut(Integer.parseInt(lblQty.getText()));
                orderDetailsDTOS.add(orderDetailsDTO);
            }
        }


    }

    private void setData() {
        vBox.getChildren().clear();
        try {
            for (OrderDetailsDTO detailsDTO : orderDetailsDTOS) {
                navigation(detailsDTO);
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
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
                if (ItemModel.isExistId(lblItemCode.getText())) {
                    lblItemCode.setStyle("-fx-border-color: white");
                    lblQty.setEditable(true);
                    lblBalance.setEditable(true);
                    btnAdd.setDisable(false);
                    ItemDTO itemDTO = ItemModel.findById(lblItemCode.getText());
                    txtItemsName.setText(itemDTO.getItem_name());
                    L1:
                    for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
                        if (orderDetailsDTO.getItem_id().equals(lblItemCode.getText())) {
                            txtQty.setText(String.valueOf(itemDTO.getQut() - orderDetailsDTO.getQut()));
                            break L1;
                        } else {
                            txtQty.setText(String.valueOf(itemDTO.getQut()));
                        }
                    }
                    if (orderDetailsDTOS.isEmpty()) {
                        txtQty.setText(String.valueOf(itemDTO.getQut()));

                    }
                    txtPrice.setText(String.valueOf(itemDTO.getPrice()));
                    lblQty.requestFocus();
                    currentQty = txtQty.getText();

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

    public void qtyOnKeyReleased(KeyEvent keyEvent) {

        if (RegexUtil.regex(btnAdd, lblQty, lblQty.getText(), "^[\\d]{0,15}$", "-fx-text-fill: #FBA23E")) {
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

    public void payOnAction(ActionEvent actionEvent) {
        try {
            if (lblCustomerId.getText().equals("") | lblBalance.getText().equals("")) {
                Notification.notificationWARNING("Place Enter Order", " Order Error ");
                btnPay.setDisable(true);
            } else {

                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setOrder_id(nextOrderId());
                orderDTO.setTime(DateTimeUtil.timeNow());
                orderDTO.setDate(DateTimeUtil.dateNow());
                orderDTO.setFinal_total(Double.parseDouble(txtTotal.getText()));
                orderDTO.setCustomer_id(lblCustomerId.getText());

                for (OrderDetailsDTO detailsDTO : orderDetailsDTOS) {
                    detailsDTO.setOrder_id(orderDTO.getOrder_id());
                }

                if (OrdersModel.PlaceOrder(orderDTO, orderDetailsDTOS)) {
                    lblCustomerId.requestFocus();
                    txtName.setText("");
                    txtNic.setText("");
                    lblCustomerId.clear();
                    lblBalance.clear();
                    txtBalance.setText("");
                    txtTotal.setText("");
                    orderDetailsDTOS.clear();
                    vBox.getChildren().clear();
                    Notification.notification(" Successfully Pay  ", " Successfully pay monthly payment");

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
        String currentId = null;
        try {
            List<String> idList = OrdersModel.findOrdersIdOrderByLength();
            for (String id :idList) {
                currentId =id;
            }
            try {
                String[] O = currentId.split("O00");
                int n = Integer.parseInt(O[1]);
                n++;
                return "O00" + n;
            } catch (NullPointerException e) {
                return "O001";
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        return "O001";
    }

    private void navigation(OrderDetailsDTO orderDetailsDTO) throws IOException, SQLException, ClassNotFoundException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lk/ijse/theGym/view/bar/CustomerOrderBar.fxml"));
        Parent root = loader.load();
        CustomerOrderBarController controller = loader.getController();
        controller.setData(orderDetailsDTO);
        vBox.getChildren().add(root);
    }

    private void setTotal() {
        double total = 0;
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            total += Double.parseDouble(String.valueOf(orderDetailsDTO.getPrice() * orderDetailsDTO.getQut()));
        }
        txtTotal.setText(String.valueOf(total));
    }

}
