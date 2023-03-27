package lk.ijse.theGym.model;

import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.to.CoachSalaryDetails;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoachSalaryDetailsController {
    public static ResultSet getSumOfSalaryOnDay(String date) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT SUM(price) FROM coach_salary_details WHERE date=?",date);
    }
    public static ResultSet getFinalTotalOnYear(String year) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT price FROM coach_salary_details WHERE date LIKE ?");
        statement.setString(1,   year+ "%");
        return statement.executeQuery();

    }

    public static ResultSet getMonthlyReport(String date) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT SUM(price)FROM coach_salary_details WHERE date LIKE ?",date);
    }

    public static boolean addDetails(CoachSalaryDetails coachSalaryDetails) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO coach_salary_details VALUES (?,?,?,?)",
                coachSalaryDetails.getEmployee_id(),
                coachSalaryDetails.getDate(),
                coachSalaryDetails.getPrice(),
                coachSalaryDetails.getSalary_id()
                );
    }

    public static ResultSet getDays() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT DISTINCT date FROM coach_salary_details");
    }

    public static ResultSet exsistThisMonth(String date, String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM coach_salary_details WHERE coach_id=? AND date LIKE ?",id,date+"%");
    }
}
