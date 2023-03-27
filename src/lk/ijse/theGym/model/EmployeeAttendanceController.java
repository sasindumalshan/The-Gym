package lk.ijse.theGym.model;

import lk.ijse.theGym.db.DBConnection;
import lk.ijse.theGym.to.EmployeeAttendance;
import lk.ijse.theGym.util.CrudUtil;
import lk.ijse.theGym.util.DateTimeUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeAttendanceController {

    public static ResultSet getAllDetails() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee.employee_id,employee.fist_name,employee.last_name,employee.roll,employee_attendance.date,employee_attendance.time FROM employee_attendance INNER JOIN Employee ON Employee_Attendance.employee_id = Employee.employee_id");
    }

    public static ResultSet searchId(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee.employee_id,employee.fist_name,employee.last_name,employee.roll,employee_attendance.date,employee_attendance.time FROM employee_attendance INNER JOIN Employee ON Employee_Attendance.employee_id = Employee.employee_id WHERE Employee.employee_id LIKE ? OR employee.fist_name LIKE ? OR employee.last_name LIKE ?",text+"%",text+"%",text+"%");
    }

    public static ResultSet searchFistName(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee.employee_id,employee.fist_name,employee.last_name,employee.roll,employee_attendance.date,employee_attendance.time FROM employee_attendance INNER JOIN Employee ON Employee_Attendance.employee_id = Employee.employee_id WHERE Employee.fist_name=?",text);
    }

    public static ResultSet searchLastName(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee.employee_id,employee.fist_name,employee.last_name,employee.roll,employee_attendance.date,employee_attendance.time FROM employee_attendance INNER JOIN Employee ON Employee_Attendance.employee_id = Employee.employee_id WHERE Employee.last_name=?",text);
    }

    public static ResultSet getTodayAttendance(String format) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM employee_attendance WHERE date=?",format);
    }

    public static ResultSet searchDate(String s) throws SQLException, ClassNotFoundException {
        System.out.println(s);
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT employee.employee_id,employee.fist_name,employee.last_name,employee.roll,employee_attendance.date,employee_attendance.time FROM employee_attendance INNER JOIN Employee ON Employee_Attendance.employee_id = Employee.employee_id WHERE employee_attendance.date LIKE ?");
        statement.setString(1,   s + "%");
        return statement.executeQuery();

           }

    public static ResultSet getTodayEmpAttendance(String dateNow) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee_attendance.date,employee_attendance.time,employee_attendance.employee_id,employee.fist_name,employee.last_name FROM employee INNER JOIN employee_attendance ON employee.employee_id = employee_attendance.employee_id WHERE employee_attendance.date=?",dateNow);
    }

    public static ResultSet getsSearchName(String date, String name) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee_attendance.employee_id FROM employee INNER JOIN employee_attendance ON employee.employee_id = employee_attendance.employee_id WHERE employee_attendance.date=? AND employee.fist_name LIKE ? OR employee_attendance.date=? AND employee.last_name LIKE ?",date,name,date,name);
    }

    public static ResultSet getSearchId(String date, String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee_id FROM employee_attendance WHERE date=? AND employee_id LIKE ?",date,id);
    }

    public static ResultSet getIdData(String s) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee_attendance.date,employee_attendance.time,employee_attendance.employee_id,employee.fist_name,employee.last_name FROM employee INNER JOIN employee_attendance ON employee.employee_id = employee_attendance.employee_id WHERE employee_attendance.employee_id=?",s);
    }

    public static ResultSet getTodayAttendanceCount() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM employee_attendance WHERE date=?", DateTimeUtil.dateNow());
    }

    public static ResultSet getAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee_id FROM employee_attendance  WHERE date=?",DateTimeUtil.dateNow());
    }

    public static boolean setAttendance(EmployeeAttendance employeeAttendance) throws SQLException, ClassNotFoundException {
        System.out.println("id"+employeeAttendance.getEmployeeID());
        System.out.println("date"+employeeAttendance.getDate());
        System.out.println("time"+employeeAttendance.getTime());
        return CrudUtil.crudUtil("INSERT INTO employee_attendance VALUES (?,?,?)",employeeAttendance.getDate(),employeeAttendance.getTime(),employeeAttendance.getEmployeeID());
    }

    public static ResultSet getAttendanceCount(String emp_id) throws SQLException, ClassNotFoundException {
        String[] split = DateTimeUtil.dateNow().split("-");
        String month=split[0]+"-"+split[1];
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM employee_attendance WHERE employee_id=? AND date LIKE ?",emp_id,month+"%");
    }
}
