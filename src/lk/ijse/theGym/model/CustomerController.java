package lk.ijse.theGym.model;

import lk.ijse.theGym.to.Customer;
import lk.ijse.theGym.util.CrudUtil;
import lk.ijse.theGym.util.DateTimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController {
    public static boolean idExists(String id) throws SQLException, ClassNotFoundException {
        ResultSet set= CrudUtil.crudUtil("SELECT customer_id FROM customer WHERE customer_id=?",id);
        return set.next();
    }

    public static boolean isIdExists(String id) throws SQLException, ClassNotFoundException {
        ResultSet set= CrudUtil.crudUtil("SELECT customer_id FROM customer WHERE customer_id=?",id);
        return set.next();
    }

    public static ResultSet getIdForData(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer.customer_id,customer.fist_name,customer.last_name,customer.e_mail,customer.nic,package.package_price FROM customer INNER JOIN package ON customer.package_id = package.package_Id WHERE customer.customer_id=?",id);
    }

    public static boolean updatePackage(String id,String packId) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE customer SET package_id=?,package_activate_date=? WHERE customer_id=?",packId, DateTimeUtil.dateNow(),id);
    }

    public static ResultSet getAllYears() throws SQLException, ClassNotFoundException {

        return CrudUtil.crudUtil("SELECT DISTINCT year FROM customer_payment");
    }

    public static ResultSet getIdData(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM customer WHERE customer_id=?",id);
    }

    public static ResultSet getAll() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM customer");
    }

    public static ResultSet getLastId() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_id FROM customer order by LENGTH(customer_id),customer_id");
    }
    public static ResultSet getPackgeUsageCount(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM customer WHERE package_id=?",id);
    }

    public static boolean addCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                customer.getCustomer_id(),
                customer.getFist_name(),
                customer.getLast_name(),
                customer.getAddress_street(),
                customer.getAddress_city(),
                customer.getAddress_lene(),
                customer.getE_mail(),
                customer.getBirthday(),
                customer.getNic(),
                customer.getContact_number(),
                customer.getGender(),
                customer.getPackage_id(),
                customer.getPackage_activate_date(),
                customer.getDate()
        );
    }

    public static boolean removeCustomer(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM customer WHERE customer_id=?",text);
    }

    public static ResultSet getAllForId(String id) throws SQLException, ClassNotFoundException {
        System.out.println("C C"+id);
        return CrudUtil.crudUtil("SELECT * FROM customer WHERE customer_id=?",id);
    }

    public static ResultSet getSearchIdData(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_id FROM customer WHERE customer_id LIKE ?",id+"%");
    }

    public static ResultSet getSearchNameData(String name) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM customer WHERE fist_name LIKE ? OR  last_name LIKE ?",name+"%",name+"%");
    }

    public static ResultSet getSearchAllData(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM customer WHERE customer_id=?",id);
    }

    public static ResultSet getCustomerCount() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM customer");
    }

    public static ResultSet getPackage(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT  package_Id FROM customer WHERE customer_id=?",id);
    }
}
