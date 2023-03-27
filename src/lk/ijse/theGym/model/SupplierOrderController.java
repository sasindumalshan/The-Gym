package lk.ijse.theGym.model;

import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.to.SupplierOrder;
import lk.ijse.theGym.to.SupplierOrderDetails;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderController {
    public static ResultSet getIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT order_id FROM Suppler_Order ORDER BY LENGTH(order_id),order_id");
    }

    public static boolean setOrder(ArrayList<SupplierOrderDetails> orderDetails, SupplierOrder supplierOrder) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            if (SupplierOrderController.setOrder(supplierOrder)) {
                System.out.println("Oder OK");
                if (SupplierOrderDetailsController.setOrderDetails(orderDetails, supplierOrder)) {
                    System.out.println("Oder details OK");
                    if (ItemsController.supplierUpdateQty(orderDetails)) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                    }
                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }

        return false;
    }

    private static boolean setOrder(SupplierOrder supplierOrder) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO suppler_order VALUES (?,?,?,?,?)",
                supplierOrder.getOrder_id(),
                supplierOrder.getTotal(),
                supplierOrder.getSuppler_id(),
                supplierOrder.getDate(),
                supplierOrder.getTime()

        );
    }

    public static ResultSet getAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT order_id FROM Suppler_Order");
    }
}
