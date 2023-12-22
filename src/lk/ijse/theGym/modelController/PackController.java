package lk.ijse.theGym.modelController;

import lk.ijse.theGym.to.Package;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PackController {
    public static ResultSet getDetail() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT package_Id,package_type,package_price,package_duration FROM package");
    }

    public static ResultSet getID(String valueOf) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT package_id FROM package WHERE package_type=?",valueOf);
    }

    public static ResultSet getAllPackageType() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT package_type FROM package");
    }

    public static ResultSet getName(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT package_type from package WHERE package_Id=?",id);
    }

    public static ResultSet getLastId() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT package_Id FROM package ORDER BY LENGTH(package_Id),package_Id");
    }

    public static boolean addPackage(Package pack) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO package VALUES (?,?,?,?)",
                pack.getPackage_price(),
                pack.getPackage_type(),
                pack.getPackage_duration(),
                pack.getPackage_Id()
                );
    }

    public static boolean update(Package aPackage) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE package SET package_price=? WHERE package_Id=?",
                aPackage.getPackage_price(),
                aPackage.getPackage_Id()
                );
    }

    public static ResultSet getAll() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM package");
    }
}
