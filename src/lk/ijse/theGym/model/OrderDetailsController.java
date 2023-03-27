package lk.ijse.theGym.model;

import lk.ijse.theGym.util.CrudUtil;
import lk.ijse.theGym.view.data.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsController {
    public static boolean setOrderDetails(ArrayList<Order> orderDetails, lk.ijse.theGym.to.Order order) throws SQLException, ClassNotFoundException {
        for (int i = 0; i < orderDetails.size(); i++) {
            if (CrudUtil.crudUtil("INSERT INTO order_details VALUES (?,?,?,?)",
                    order.getOrder_id(),
                    orderDetails.get(i).getId(),
                    orderDetails.get(i).getPrice(),
                    orderDetails.get(i).getQty()

                    )){
                System.out.println((i+1)+" Order Details added");
            }else {
                System.out.println((i+1)+" Order Details add fails !");
                return false;
            }
        }
        System.out.println("All Added ! ");
        return true;
    }

    public static ResultSet getAllYears() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT DISTINCT date FROM orders");
    }

    public static ResultSet getAllData(String date) throws SQLException, ClassNotFoundException {
        System.out.println(date);
        return CrudUtil.crudUtil("SELECT customer_id,date,time,order_id,final_total FROM orders WHERE date LIKE ?",date+"%");
    }

    public static ResultSet getDataForOrderId(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_id,date,time,order_id,final_total FROM orders WHERE order_id=?",id);
    }

    public static ResultSet searchIDOrCustomerId(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT order_id FROM orders WHERE order_id LIKE ? OR customer_id LIKE ?",text+"%",text+"%");
    }
}
