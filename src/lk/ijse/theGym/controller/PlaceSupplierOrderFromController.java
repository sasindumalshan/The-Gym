package lk.ijse.theGym.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.model.ItemsController;
import lk.ijse.theGym.model.SupplierController;
import lk.ijse.theGym.model.SupplierOrderDetailsController;
import lk.ijse.theGym.to.SupplierOrderDetails;
import lk.ijse.theGym.util.DateTimeUtil;
import lk.ijse.theGym.util.Navigation;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PlaceSupplierOrderFromController implements Initializable {

    public JFXTextField txtPrice;
    public JFXTextField txtQut;
    public JFXComboBox combSupplierId;
    public JFXComboBox combItemCode;
    public Text txtCompanyName;
    public Text txtItemName;
    private ArrayList<String> supplierId = new ArrayList<>();
    private ArrayList<String> itemsId = new ArrayList<>();

    public void supplierIdOnAction(ActionEvent actionEvent) {
        try {
            ResultSet set = SupplierController.getSupplierDetails(String.valueOf(combSupplierId.getValue()));
            if (set.next()) {
                txtCompanyName.setText(set.getString(1));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void itemCodeOnAction(ActionEvent actionEvent) {
        try {
            ResultSet set = ItemsController.ScrollPaneLoadData(String.valueOf(combItemCode.getValue()));
            if (set.next()) {
                txtItemName.setText(set.getString(2));
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
//        try {
////            tranceAction();
//            Navigation.close(actionEvent);
//        } catch (SQLException | ClassNotFoundException throwables) {
//            throwables.printStackTrace();
//        }
//        if (!SupplierFromController.getInstance().rBSuppliersOrder.isSelected()) {
//            SupplierFromController.getInstance().rBSuppliersOrder.fire();
//        } else {
//            SupplierFromController.getInstance().setAllOrder();
//        }
    }

//    private void tranceAction() throws SQLException, ClassNotFoundException {
//        try {
//            Connection connection = DBConnection.getInstance().getConnection();
//            connection.setAutoCommit(false);
//            SupplierOrderDetails details = new SupplierOrderDetails(
//                    getOrderId(),
//                    String.valueOf(combSupplierId.getValue()),
//                    String.valueOf(combItemCode.getValue()),
//                    Integer.parseInt(txtQut.getText()),
//                    DateTimeUtil.dateNow(),
//                    Double.parseDouble(txtPrice.getText())
//
//            );
//            boolean isAddedOrder = SupplierOrderDetailsController.setOrder(details);
//            if (isAddedOrder) {
//                boolean isUpdated = ItemsController.setOrderUpdateDetails(details);
//                if (isUpdated) {
//                   new Alert(Alert.AlertType.CONFIRMATION,"yes huththo yes  ! tranceAction ok").show();
//                    connection.commit();
//                }
//            }
//            connection.rollback();
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        } finally {
//            DBConnection.getInstance().getConnection().setAutoCommit(true);
//        }
//    }

    private String getOrderId() {
        try {
            ResultSet set = SupplierOrderDetailsController.getLastId();
            if (set.next()){
               String[] O00= set.getString(1).split("O00");
               int incrementId= Integer.parseInt(O00[1]);
               incrementId++;
               return "O00"+incrementId;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return "O001";
    }

    private void setSupplierId() {
        try {
            ResultSet set = SupplierController.getAllId();
            while (set.next()) {
                supplierId.add(set.getString(1));
            }
            combSupplierId.getItems().addAll(supplierId);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setItemsId() {
        try {
            ResultSet set = ItemsController.loadAllIds();
            while (set.next()) {
                itemsId.add(set.getString(1));
            }
            combItemCode.getItems().addAll(itemsId);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setItemsId();
        setSupplierId();
    }


}
