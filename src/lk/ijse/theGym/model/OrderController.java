package lk.ijse.theGym.model;

import javafx.scene.control.Alert;
import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.to.Order;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderController {
    public static ResultSet getFinalTotalOnDay(String day) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT SUM(final_total)FROM orders WHERE date=?", day);
    }

    public static ResultSet getFinalTotalOnYear(String year) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT final_total  FROM orders WHERE date LIKE ?");
        statement.setString(1, year + "%");
        return statement.executeQuery();

    }

    public static ResultSet getMonthlyReport(String date) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT SUM(final_total)FROM orders WHERE date LIKE ?", date);
    }

    public static ResultSet getLastOrderId() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");
    }

    public static boolean PlaceOrder(Order order, ArrayList<lk.ijse.theGym.view.data.Order> orderDetails) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            if (OrderController.setOrder(order)) {
                if (OrderDetailsController.setOrderDetails(orderDetails,order)) {
                    if (ItemsController.updateQty(orderDetails)) {
                            connection.commit();
                            //new Alert(Alert.AlertType.CONFIRMATION,"Order Success !").show();
                            return true;
                    } else {
                        connection.rollback();
                        new Alert(Alert.AlertType.ERROR,"Order fail !").show();
                    }
                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR,"Order fail !").show();
                }
            } else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR,"Order fail !").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    private static boolean setOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO orders VALUES (?,?,?,?,?)",
                order.getOrder_id(),
                order.getTime(),
                order.getDate(),
                order.getFinal_total(),
                order.getCustomer_id()
                );
    }
}
