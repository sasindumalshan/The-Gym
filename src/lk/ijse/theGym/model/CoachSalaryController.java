package lk.ijse.theGym.model;

import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoachSalaryController {
    public static ResultSet getAll(String date) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM coach_salary_details WHERE date LIKE ?",date+"%");
    }
}
