package lk.ijse.theGym.modelController;

import lk.ijse.theGym.to.Salary;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryController {
    public static ResultSet findSalaryId(String value) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT salary_Id FROM Salary WHERE role=?", value);
    }

    public static ResultSet getAllRoles() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT role FROM Salary");
    }

    public static ResultSet getAllSalaryIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT salary_Id FROM salary");
    }

    public static ResultSet getSalary(String id) throws SQLException, ClassNotFoundException {
        System.out.println(id);
        return CrudUtil.crudUtil("SELECT salary FROM salary WHERE salary_Id=?", id);
    }

    public static ResultSet getlastId() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT salary_Id FROM  salary ORDER BY LENGTH(salary_Id),salary_Id");
    }

    public static boolean addSalary(Salary salary) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO salary VALUES (?,?,?)",
                salary.getId(),
                salary.getRole(),
                salary.getSalary()
        );
    }

    public static ResultSet findSalary(String string) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT salary FROM salary WHERE salary_Id=?", string);
    }

    public static ResultSet getAll() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM salary");
    }

    public static boolean updateSalary(String id, String salaryPrice) throws SQLException, ClassNotFoundException {
        System.out.println(id);
        System.out.println(salaryPrice);
        return CrudUtil.crudUtil("UPDATE salary SET  salary=? WHERE salary_Id=?", salaryPrice, id);
    }
}
