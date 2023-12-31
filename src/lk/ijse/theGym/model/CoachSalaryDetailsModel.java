package lk.ijse.theGym.model;

import lk.ijse.theGym.dto.CoachSalaryDetailsDTO;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoachSalaryDetailsModel {

    public static boolean save(CoachSalaryDetailsDTO coachSalaryDetailsDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO coach_salary_details VALUES (?,?,?,?)",
                coachSalaryDetailsDTO.getCoach_id(),
                coachSalaryDetailsDTO.getDate(),
                coachSalaryDetailsDTO.getPrice(),
                coachSalaryDetailsDTO.getSalary_id()
        );
    }

    public static List<String> findPriceCoachSalaryDetailsLikeDate(String year) throws SQLException, ClassNotFoundException {
        return setStringList(CrudUtil.crudUtil("SELECT price FROM coach_salary_details WHERE date LIKE ?",year+ "%"));
    }

    public static List<String> findDistinctDateCoachSalaryDetails() throws SQLException, ClassNotFoundException {
        return setStringList(CrudUtil.crudUtil("SELECT DISTINCT date FROM coach_salary_details"));
    }

    public static String sumSalaryByDate(String date) throws SQLException, ClassNotFoundException {
        return setString(CrudUtil.crudUtil("SELECT SUM(price) FROM coach_salary_details WHERE date=?",date));
    }

    public static String sumSalaryLikeDate(String date) throws SQLException, ClassNotFoundException {
        return setString(CrudUtil.crudUtil("SELECT SUM(price)FROM coach_salary_details WHERE date LIKE ?",date));
    }

    public static boolean existThisMonth(String date, String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT * FROM coach_salary_details WHERE coach_id=? AND date LIKE ?", id, date + "%");
        return resultSet.next() ;
    }

    private static String setString(ResultSet resultSet) throws SQLException {
        if (resultSet.next())return resultSet.getString(1);
        return null;
    }

    private static List<String> setStringList(ResultSet resultSet) throws SQLException {
        List<String>list=new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        };
        return list;
    }

}
