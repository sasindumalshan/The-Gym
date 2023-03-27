package lk.ijse.theGym.model;

import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeSalaryController {
    public static ResultSet getAll(String date) throws SQLException, ClassNotFoundException {
        System.out.println(date);
        return CrudUtil.crudUtil("SELECT * FROM employee_salary_details WHERE date LIKE ?",date+"%");
    }
}
