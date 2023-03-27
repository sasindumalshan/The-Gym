package lk.ijse.theGym.model;

import lk.ijse.theGym.to.CustomerAttendance;
import lk.ijse.theGym.util.CrudUtil;
import lk.ijse.theGym.util.DateTimeUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerAttendanceController {
    public static ResultSet getTodayAttendance(String dateNow) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_attendance.date,customer_attendance.time,customer_attendance.customer_id,customer.fist_name,customer.last_name FROM customer_attendance INNER JOIN customer ON customer_attendance.customer_id = customer.customer_id WHERE customer_attendance.date =?",dateNow);
    }

    public static ResultSet getSearchName(String date, String name) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_attendance.customer_id  FROM customer INNER JOIN customer_attendance ON customer_attendance.customer_id = customer.customer_id WHERE customer_attendance.date=? AND customer.fist_name LIKE ? OR customer_attendance.date=? AND customer.last_name LIKE ?",date,name,date,name);
    }

    public static ResultSet getSearchId(String date, String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_id FROM customer_attendance WHERE date=? AND customer_id LIKE ?",date,id);
    }

    public static ResultSet getIdDate(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_attendance.date,customer_attendance.time,customer_attendance.customer_id,customer.fist_name,customer.last_name FROM customer_attendance INNER JOIN customer ON customer_attendance.customer_id = customer.customer_id WHERE customer_attendance.customer_id=?",s);
    }

    public static ResultSet getTodayAttendanceCount() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM customer_attendance WHERE date=?", DateTimeUtil.dateNow());
    }

    public static ResultSet getAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT customer_id FROM customer_attendance WHERE date=?",DateTimeUtil.dateNow());
    }

    public static boolean setAttendance(CustomerAttendance customerAttendance) throws SQLException, ClassNotFoundException {
        System.out.println(customerAttendance.getCustomer_id());
        System.out.println(customerAttendance.getDate());
        System.out.println(customerAttendance.getTime());
        return CrudUtil.crudUtil("INSERT INTO customer_attendance VALUES (?,?,?)",customerAttendance.getDate(),customerAttendance.getTime(),customerAttendance.getCustomer_id());
    }

    public static ResultSet getTodayAttendanceCount(String dateNow) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM customer_attendance WHERE date=?",dateNow);
    }
}
