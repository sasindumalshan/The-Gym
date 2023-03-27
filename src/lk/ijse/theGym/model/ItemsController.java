package lk.ijse.theGym.model;


import lk.ijse.theGym.to.Items;
import lk.ijse.theGym.to.SupplierOrderDetails;
import lk.ijse.theGym.util.CrudUtil;
import lk.ijse.theGym.view.data.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemsController {
    public static String itemCode;

    public static boolean addItem(Items items) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO item VALUES (?,?,?,?,?,?,?)", items.getItem_id(), items.getItem_name(), items.getCategory(), items.getQut(), items.getPrice(), items.getBrand(), items.getDescription());
    }

    public static ResultSet ScrollPaneLoadData(String itemCode) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM item WHERE item_id=?", itemCode);
    }

    public static ResultSet loadAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT item_id FROM item");
    }

    public static ResultSet viewAllItemDetails() throws SQLException, ClassNotFoundException {

        return CrudUtil.crudUtil("SELECT * FROM item WHERE item_id=?", itemCode);
    }

    public static boolean updateItem(Items items) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE item SET item_name=?, category=?,price=?,brand=?,description=? WHERE item_id=?", items.getItem_name(), items.getCategory(), items.getPrice(), items.getBrand(), items.getDescription(), items.getItem_id());
    }

    public static boolean deleteItem(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE  FROM item WHERE item_id=?", text);
    }

    public static ResultSet CountOfAllItems() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM item");

    }

    public static ResultSet limitedStock() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(qut) FROM item WHERE qut < 5");
    }

    public static ResultSet searchId(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM item WHERE item_id LIKE ? OR item_name LIKE ?", text+"%",text+"%");
    }

    public static ResultSet searchName(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM item WHERE item_name=?", text);

    }

    public static boolean setOrderUpdateDetails(SupplierOrderDetails details) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE item SET qut=qut+? ,price=? WHERE item_id=?",
                details.getQut(),
                details.getPrice(),
                details.getItem_code()

        );
    }

    public static ResultSet getItemName(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT item_name FROM item WHERE item_id=?",id);
    }

    public static ResultSet getAllItems() throws SQLException, ClassNotFoundException {

        return CrudUtil.crudUtil("SELECT *FROM item");
    }

    public static ResultSet getAllCategory() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT DISTINCT category FROM item");
    }

    public static ResultSet getSelectedCategoryData(String category) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM item WHERE category=?",category);
    }

    public static ResultSet getSearchID(String itemCode) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM item WHERE item_id LIKE ?",itemCode);
    }

    public static ResultSet getSearchName(String name) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM item WHERE item_name LIKE ?",name);
    }

    public static ResultSet getSearchBrand(String brand) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM item WHERE brand LIKE ?",brand);
    }

    public static ResultSet getItemsForId(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM item WHERE item_id=?",id);
    }

    public static boolean isExistId(String id) throws SQLException, ClassNotFoundException {
        ResultSet set=CrudUtil.crudUtil("SELECT *from item WHERE item_id=?",id);
        return set.next();
    }

    public static boolean updateQty(ArrayList<Order> orderDetails) throws SQLException, ClassNotFoundException {
        for (int i = 0; i <orderDetails.size() ; i++) {
            if (CrudUtil.crudUtil("UPDATE item SET qut=qut-? WHERE item_id=? ",
                    orderDetails.get(i).getQty(),
                    orderDetails.get(i).getId()
            )){
                System.out.println((i+1)+" is Updated !");
            }else {
                System.out.println((i+1)+" is Updated fails !");
                return false;
            }
        }
        System.out.println(" All Updated !");
        return true;
    }

    public static boolean supplierUpdateQty(ArrayList<SupplierOrderDetails> orderDetails) throws SQLException, ClassNotFoundException {
        for (int i = 0; i < orderDetails.size(); i++) {
            if (CrudUtil.crudUtil("UPDATE item SET qut=qut+? WHERE item_id=?",
                    orderDetails.get(i).getQut(),
                    orderDetails.get(i).getItem_code()
                    )){
                System.out.println((i+1)+" is Updated !");
            }else {
                System.out.println((i+1)+" is Updated fails !");
                return false;
            }
        }
        System.out.println(" All Updated !");
        return true;
    }
}
