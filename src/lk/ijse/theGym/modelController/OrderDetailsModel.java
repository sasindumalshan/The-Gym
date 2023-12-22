package lk.ijse.theGym.modelController;

import lk.ijse.theGym.dto.OrderDetailsDTO;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public static ResultSet getAllYears() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT DISTINCT date FROM orders");
    }

    public static ResultSet getAllData(String date) throws SQLException, ClassNotFoundException {
        System.out.println(date);
        return CrudUtil.crudUtil("SELECT customer_id,date,time,order_id,final_total FROM orders WHERE date LIKE ?", date + "%");
    }

    public static ResultSet getDataForOrderId(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_id,date,time,order_id,final_total FROM orders WHERE order_id=?", id);
    }

    public static ResultSet searchIDOrCustomerId(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT order_id FROM orders WHERE order_id LIKE ? OR customer_id LIKE ?", text + "%", text + "%");
    }


}
