package lk.ijse.theGym.model;

import lk.ijse.theGym.dto.OrderDetailsDTO;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsModel {

    public static boolean saveAll(ArrayList<OrderDetailsDTO> orderDetailsDTOS) throws SQLException, ClassNotFoundException {
        for (OrderDetailsDTO orderDetailsDTO : orderDetailsDTOS) {
            boolean isSave = save(orderDetailsDTO);
            if (!isSave) {
                return false;
            }
        }
        return true;
    }

    public static boolean save(OrderDetailsDTO orderDetailsDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO order_details VALUES (?,?,?,?)",
                orderDetailsDTO.getOrder_id(),
                orderDetailsDTO.getItem_id(),
                orderDetailsDTO.getPrice(),
                orderDetailsDTO.getQut()
        );


    }

    public static List<String> findCustomerIdByOrderIdLikeOrCustomerIdLike(String text) throws SQLException, ClassNotFoundException {
        return setStringList(CrudUtil.crudUtil("SELECT order_id FROM orders WHERE order_id LIKE ? OR customer_id LIKE ?", text + "%", text + "%"));
    }

    public static List<String> findDate() throws SQLException, ClassNotFoundException {
        return setStringList(CrudUtil.crudUtil("SELECT DISTINCT date FROM orders"));
    }

    private static List<String> setStringList(ResultSet resultSet) throws SQLException {
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }


}
