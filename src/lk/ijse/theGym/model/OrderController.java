package lk.ijse.theGym.model;

import javafx.scene.control.Alert;
import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.dto.ItemDTO;
import lk.ijse.theGym.dto.OrderDTO;
import lk.ijse.theGym.dto.OrderDetailsDTO;
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

    public static boolean PlaceOrder(OrderDTO orderDTO, ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            if (save(orderDTO)) {
                boolean isDetailsSave = OrderDetailsModel.saveAll(orderDetailsDTOS);
                if (isDetailsSave) {
                    ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
                    for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
                        ItemDTO itemDTO = ItemModel.findById(orderDetailsDTO.getItem_id());
                        itemDTO.setQut(itemDTO.getQut() - orderDetailsDTO.getQut());
                        itemDTOS.add(itemDTO);
                        boolean isUpdate = ItemModel.update(itemDTO);
                        if (!isUpdate) {
                            connection.commit();
                            return true;
                        } else {
                            connection.rollback();
                            new Alert(Alert.AlertType.ERROR, "Order fail !").show();
                        }
                    }


                } else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR, "Order fail !").show();
                }
            } else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Order fail !").show();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            connection.rollback();
            throwables.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    private static boolean save(OrderDTO orderDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO orders VALUES (?,?,?,?,?)",
                orderDTO.getOrder_id(),
                orderDTO.getTime(),
                orderDTO.getDate(),
                orderDTO.getFinal_total(),
                orderDTO.getCustomer_id()
        );
    }
}
