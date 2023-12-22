package lk.ijse.theGym.model;

import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.dto.ItemDTO;
import lk.ijse.theGym.modelController.SupplierOrderDetailsController;
import lk.ijse.theGym.to.SupplierOrder;
import lk.ijse.theGym.to.SupplierOrderDetails;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierOrderModel {

    public static List<String> findIdOrderByLength() throws SQLException, ClassNotFoundException {
        return setString(CrudUtil.crudUtil("SELECT order_id FROM Suppler_Order ORDER BY LENGTH(order_id),order_id"));
    }

    public static boolean placeSupplerOrder(ArrayList<SupplierOrderDetails> supplierOrderDetails, SupplierOrder supplierOrder) throws SQLException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            if (SupplierOrderModel.save(supplierOrder)) {
                System.out.println("Oder OK");
                if (SupplierOrderDetailsController.setOrderDetails(supplierOrderDetails, supplierOrder)) {

                    ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
                    for (SupplierOrderDetails details : supplierOrderDetails) {
                        ItemDTO itemDTO = ItemModel.findById(details.getItem_code());
                        itemDTO.setQut(itemDTO.getQut() - Integer.parseInt(details.getQut()));
                        itemDTOS.add(itemDTO);
                        boolean isUpdate = ItemModel.update(itemDTO);
                        if (!isUpdate) {
                            connection.commit();
                            return true;
                        } else {
                            connection.rollback();
                        }
                    }

                } else {
                    connection.rollback();
                }
            } else {
                connection.rollback();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            connection.rollback();
            throwables.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }

        return false;
    }

    private static boolean save(SupplierOrder supplierOrder) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO suppler_order VALUES (?,?,?,?,?)",
                supplierOrder.getOrder_id(),
                supplierOrder.getTotal(),
                supplierOrder.getSuppler_id(),
                supplierOrder.getDate(),
                supplierOrder.getTime()

        );
    }

    private static List<String> setString(ResultSet resultSet) throws SQLException {
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }



   /* public static ResultSet getAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT order_id FROM Suppler_Order");
    }*/
}
