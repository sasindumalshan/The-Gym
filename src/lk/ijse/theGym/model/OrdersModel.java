package lk.ijse.theGym.model;

import javafx.scene.control.Alert;
import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.dto.ItemDTO;
import lk.ijse.theGym.dto.OrderDTO;
import lk.ijse.theGym.dto.OrderDetailsDTO;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdersModel {

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

    public static List<String> findOrdersIdOrderByLength() throws SQLException, ClassNotFoundException {
        return setStringList(CrudUtil.crudUtil("SELECT order_id FROM orders ORDER BY LENGTH(order_id),order_id"));
    }

    public static List<String> findFinalTotalByDate(String year) throws SQLException, ClassNotFoundException {
        String date = year + "%";
        String sql = "SELECT final_total  FROM orders WHERE date LIKE ?";
        return setStringList(CrudUtil.crudUtil(sql, date));

    }

    public static List<OrderDTO> findOrdersByOrdersId(String id) throws SQLException, ClassNotFoundException {
        return setDTO(CrudUtil.crudUtil("SELECT customer_id,date,time,order_id,final_total FROM orders WHERE order_id=?", id));
    }

    public static List<OrderDTO> findOrdersByDate(String date) throws SQLException, ClassNotFoundException {
        return setDTO(CrudUtil.crudUtil("SELECT customer_id,date,time,order_id,final_total FROM orders WHERE date LIKE ?", date + "%"));
    }

    public static String sumOrdersByDate(String day) throws SQLException, ClassNotFoundException {
        return setString(CrudUtil.crudUtil("SELECT SUM(final_total)FROM orders WHERE date=?", day));
    }

    public static String sumOrdersByDateLike(String date) throws SQLException, ClassNotFoundException {
        return setString(CrudUtil.crudUtil("SELECT SUM(final_total)FROM orders WHERE date LIKE ?", date));
    }

    private static List<OrderDTO> setDTO(ResultSet resultSet) throws SQLException {
        List<OrderDTO> list = new ArrayList<>();
        while (resultSet.next()) {
            OrderDTO orderDTO = new OrderDTO();

            orderDTO.setCustomer_id(resultSet.getString(1));
            orderDTO.setDate(resultSet.getString(2));
            orderDTO.setTime(resultSet.getString(3));
            orderDTO.setOrder_id(resultSet.getString(4));
            orderDTO.setFinal_total(Double.parseDouble(resultSet.getString(5)));

            list.add(orderDTO);
        }
        return list;
    }

    private static List<String> setStringList(ResultSet resultSet) throws SQLException {
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }

    private static String setString(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getString(1)==null?"0":resultSet.getString(1);
        }
        return "0";
    }

    public static String getYearSum(String year) throws SQLException, ClassNotFoundException {
       return setString(CrudUtil.crudUtil("SELECT SUM( final_total) FROM orders WHERE YEAR(date)=?",year));
    }
}
