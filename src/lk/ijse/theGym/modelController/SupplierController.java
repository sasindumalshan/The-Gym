package lk.ijse.theGym.modelController;

import lk.ijse.theGym.to.Supplier;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierController {
    public static ResultSet getAllSupplier() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM supplier");
    }

    public static ResultSet getAllId() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT supplier_id FROM supplier");
    }

    public static ResultSet getSupplierDetails(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM supplier WHERE supplier_id=?", id);
    }

    public static ResultSet getLarstId() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT supplier_id FROM supplier ORDER BY LENGTH(supplier_id),supplier_id");
    }

    public static boolean addNewSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO supplier VALUES (?,?,?,?,?)",
                supplier.getCompany_name(),
                supplier.getSupplier_id(),
                supplier.getEmail(),
                supplier.getMobile_no(),
                supplier.getLocation()
        );
    }

    public static boolean removeSupplier(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM supplier WHERE supplier_id=?",id);
    }

    public static boolean updateSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE supplier SET  company_name=?,email=?,location=?,mobile_no=? WHERE supplier_id=?",
              supplier.getCompany_name(),
                supplier.getEmail(),
                supplier.getLocation(),
                supplier.getMobile_no(),
                supplier.getSupplier_id()
                );
    }

    public static ResultSet getComName(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT company_name FROM supplier WHERE supplier_id=?",id);
    }

    public static ResultSet getSearchName(String name) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT supplier_id FROM supplier WHERE company_name LIKE ?",name+"%");
    }

    public static ResultSet getSearchId(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT supplier_id FROM supplier WHERE supplier.supplier_id LIKE ?",id+"%");
    }

    /*public static ResultSet searchSupplierId(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM supplier WHERE supplier_id=?",id);
    }

    public static ResultSet searchSupplierName(String name) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM supplier WHERE company_name=?",name);
    }*/
}
