package lk.ijse.theGym.model;

import lk.ijse.theGym.dto.CustomerAttendanceDTO;
import lk.ijse.theGym.dto.projection.AttendanceProjection;
import lk.ijse.theGym.to.CustomerAttendance;
import lk.ijse.theGym.util.CrudUtil;
import lk.ijse.theGym.util.DateTimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerAttendanceModel {

    public static boolean save(CustomerAttendanceDTO customerAttendanceDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO customer_attendance VALUES (?,?,?)",
                customerAttendanceDTO.getDate(),
                customerAttendanceDTO.getTime(),
                customerAttendanceDTO.getCustomer_id()
        );
    }

    public static ArrayList<AttendanceProjection> findAttendanceCustomerByDate(String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT customer_attendance.date,customer_attendance.time,customer_attendance.customer_id,customer.fist_name,customer.last_name FROM customer_attendance INNER JOIN customer ON customer_attendance.customer_id = customer.customer_id WHERE customer_attendance.date =?";
        return setProjection(CrudUtil.crudUtil(sql, date));
    }

    public static ArrayList<AttendanceProjection> findAttendanceByCustomerId(String customerId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT customer_attendance.date,customer_attendance.time,customer_attendance.customer_id,customer.fist_name,customer.last_name FROM customer_attendance INNER JOIN customer ON customer_attendance.customer_id = customer.customer_id WHERE customer_attendance.customer_id=?";
        return setProjection(CrudUtil.crudUtil(sql, customerId));
    }

    public static ArrayList<AttendanceProjection> findAttendanceByDateAndFistNameLikeOrLastNameOrDateLikeOrCustomerId(String date, String searchText) throws SQLException, ClassNotFoundException {
        String sql = "SELECT customer_attendance.customer_id  FROM customer INNER JOIN customer_attendance ON customer_attendance.customer_id = customer.customer_id WHERE customer_attendance.date=? AND customer.fist_name LIKE ? OR customer_attendance.date=? AND customer.last_name LIKE ? OR  customer_id LIKE ?";
        return setProjection(CrudUtil.crudUtil(sql, date, searchText, date, searchText, searchText));
    }

    public static String countAttendanceByDate(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM customer_attendance WHERE date=?", date);
        return setString(resultSet);
    }

    private static String setString(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }

    private static ArrayList<AttendanceProjection> setProjection(ResultSet resultSet) throws SQLException {
        ArrayList<AttendanceProjection> attendanceProjectionsList = new ArrayList<>();
        while (resultSet.next()) {
            AttendanceProjection attendanceProjection = new AttendanceProjection();

            attendanceProjection.setDate(resultSet.getString(1));
            attendanceProjection.setTime(resultSet.getString(2));
            attendanceProjection.setId(resultSet.getString(3));
            attendanceProjection.setFistName(resultSet.getString(4));
            attendanceProjection.setLastName(resultSet.getString(5));

            attendanceProjectionsList.add(attendanceProjection);
        }
        return attendanceProjectionsList;
    }

}
