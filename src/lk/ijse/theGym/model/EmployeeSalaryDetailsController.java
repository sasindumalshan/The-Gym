package lk.ijse.theGym.model;

import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.to.EmployeeSalaryDetails;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeSalaryDetailsController {
    public static ResultSet getSumOfSalaryOnDay(String date) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT SUM(price) FROM employee_salary_details WHERE date=?",date);
    }
    public static ResultSet getFinalTotalOnYear(String year) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT price FROM employee_salary_details WHERE date LIKE ?");
        statement.setString(1,   year+ "%");
        return statement.executeQuery();

    }

    public static ResultSet getMonthlyReport(String date) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT SUM(price)FROM employee_salary_details WHERE date LIKE ?",date);
    }

    public static boolean addDetails(EmployeeSalaryDetails employeeSalaryDetails) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO employee_salary_details VALUES (?,?,?,?)",
                employeeSalaryDetails.getEmployee_id(),
                employeeSalaryDetails.getDate(),
                employeeSalaryDetails.getPrice(),
                employeeSalaryDetails.getSalary_id()
                );
    }

    public static ResultSet getDays() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT DISTINCT date FROM employee_salary_details");
    }

    public static ResultSet existThisMonth(String date, String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM employee_salary_details WHERE employee_id=? AND date LIKE ?",id,date+"%");
    }
}
