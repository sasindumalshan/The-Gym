package lk.ijse.theGym.model;

import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.to.SupplierOrder;
import lk.ijse.theGym.to.SupplierOrderDetails;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderDetailsController {
    public static ResultSet getMonthlyOrders() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM supplier_order_details");
    }

//    public static boolean setOrder(SupplierOrderDetails supplierOrderDetails) throws SQLException, ClassNotFoundException {
//        return CrudUtil.crudUtil("INSERT INTO supplier_order_details VALUES (?,?,?,?,?,?)",
//                supplierOrderDetails.getOrder_id(),
//                supplierOrderDetails.getSupplier_id(),
//                supplierOrderDetails.getItem_code(),
//                supplierOrderDetails.getQut(),
//                supplierOrderDetails.getDate(),
//                supplierOrderDetails.getPrice()
//                );
//    }

    public static ResultSet getLastId() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT order_id  FROM supplier_order_details ORDER BY order_id  DESC LIMIT 1");
    }

    public static ResultSet getAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT order_id FROM supplier_order_details");
    }

    public static ResultSet getSupIdItemId(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT Suppler_Order.order_id,Supplier.supplier_id,Supplier.company_name ,COUNT(item_code),Suppler_Order.total FROM suppler_order INNER JOIN supplier  ON Suppler_Order.suppler_id=Supplier.supplier_id  INNER JOIN supplier_order_details ON Supplier_Order_Details.order_id=Suppler_Order.order_id WHERE Suppler_Order.order_id=?",id);
    }

    public static ResultSet getDetails(String orderId) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM supplier_order_details WHERE order_id=?",orderId);
    }

    public static ResultSet getTotalOnDay(String day) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT SUM(total) FROM suppler_order WHERE date=?",day);
    }
    public static ResultSet getFinalTotalOnYear(String year) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT total FROM suppler_order WHERE date LIKE ?",year+"%");

    }

    public static ResultSet getMonthlyReport(String date) throws SQLException, ClassNotFoundException {
        System.out.println(date);
        return CrudUtil.crudUtil("SELECT SUM(total) FROM Suppler_Order WHERE date LIKE ?",date);
    }

    public static ResultSet getSearchName(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT order_id FROM Suppler_Order INNER JOIN supplier ON Supplier.supplier_id=Suppler_Order.suppler_id WHERE company_name LIKE ?",id+"%") ;
    }

    public static ResultSet getSearchId(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT order_id FROM Suppler_Order WHERE order_id LIKE ?",id+"%");
    }

    public static ResultSet getSupDetaolsIdItemId(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT Suppler_Order.order_id,Supplier.supplier_id,Supplier.company_name ,COUNT(item_code),Suppler_Order.total FROM suppler_order INNER JOIN supplier  ON Suppler_Order.suppler_id=Supplier.supplier_id  INNER JOIN supplier_order_details ON Supplier_Order_Details.order_id=Suppler_Order.order_id WHERE Suppler_Order.order_id=?",s);
    }

    public static boolean setOrderDetails(ArrayList<SupplierOrderDetails> orderDetails, SupplierOrder supplierOrder) throws SQLException, ClassNotFoundException {
        for (int i = 0; i < orderDetails.size(); i++) {
            if(CrudUtil.crudUtil("INSERT INTO supplier_order_details VALUES (?,?,?,?)",
                    supplierOrder.getOrder_id(),
                        orderDetails.get(i).getItem_code(),
                        orderDetails.get(i).getQut(),
                        orderDetails.get(i).getPrice()
                    ) ){
                System.out.println("set"+i);
            }else {
                System.out.println("error");
                return false;
            }
        }

        return true;
    }
}
