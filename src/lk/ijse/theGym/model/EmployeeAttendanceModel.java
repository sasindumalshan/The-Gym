package lk.ijse.theGym.model;

import lk.ijse.theGym.dto.EmployeeAttendanceDTO;
import lk.ijse.theGym.dto.projection.AttendanceProjection;
import lk.ijse.theGym.dto.projection.EmployeeAttendanceProjection;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeAttendanceModel {

    public static boolean save(EmployeeAttendanceDTO employeeAttendanceDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO employee_attendance VALUES (?,?,?)",
                employeeAttendanceDTO.getDate(),
                employeeAttendanceDTO.getTime(),
                employeeAttendanceDTO.getEmployee_id());
    }

    public static ArrayList<AttendanceProjection> findAttendanceByDate(String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT employee_attendance.date,employee_attendance.time,employee_attendance.employee_id,employee.fist_name,employee.last_name FROM employee INNER JOIN employee_attendance ON employee.employee_id = employee_attendance.employee_id WHERE employee_attendance.date=?";
        return setProjection(CrudUtil.crudUtil(sql, date));
    }

    public static ArrayList<AttendanceProjection> findAttendanceByEmployeeId(String employeeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT employee_attendance.date,employee_attendance.time,employee_attendance.employee_id,employee.fist_name,employee.last_name FROM employee INNER JOIN employee_attendance ON employee.employee_id = employee_attendance.employee_id WHERE employee_attendance.employee_id=?";
        return setProjection(CrudUtil.crudUtil(sql, employeeId));
    }

    public static ArrayList<AttendanceProjection> findAttendanceByDateAndFistNameLikeOrLastNameOrDateLikeOrEmployeeId(String date, String searchText) throws SQLException, ClassNotFoundException {
        String sql = "SELECT employee_attendance.date,employee_attendance.time,employee_attendance.employee_id,employee.fist_name,employee.last_name FROM employee INNER JOIN employee_attendance ON employee.employee_id = employee_attendance.employee_id WHERE employee_attendance.date=? AND employee.fist_name LIKE ? OR employee_attendance.date=? AND employee.last_name LIKE ? OR employee_id LIKE ?";
        return setProjection(CrudUtil.crudUtil(sql, date, searchText, date, searchText, searchText));
    }

    public static String getAttendanceCount(String employeeId, String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM employee_attendance WHERE employee_id=? AND date LIKE ?", employeeId, date + "%");
        return setString(resultSet);
    }

    public static String countAttendanceByDate(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.crudUtil("SELECT COUNT(*) FROM employee_attendance WHERE date=?", date);
        return setString(resultSet);
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

    private static String setString(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return "0";
    }


    public static ArrayList<EmployeeAttendanceProjection> findAttendanceAndEmployee() throws SQLException, ClassNotFoundException {
        String sql = "SELECT employee.employee_id,employee.fist_name,employee.last_name,employee.roll,employee_attendance.date,employee_attendance.time FROM employee_attendance INNER JOIN Employee ON Employee_Attendance.employee_id = Employee.employee_id";
        return setEmployeeProjection(CrudUtil.crudUtil(sql));
    }

    public static ArrayList<EmployeeAttendanceProjection> findEmployeeAttendance(String searchText) throws SQLException, ClassNotFoundException {
        String sql="SELECT employee.employee_id,employee.fist_name,employee.last_name,employee.roll,employee_attendance.date,employee_attendance.time FROM employee_attendance INNER JOIN Employee ON Employee_Attendance.employee_id = Employee.employee_id WHERE Employee.employee_id LIKE ? OR employee.fist_name LIKE ? OR employee.last_name LIKE ?";
        return setEmployeeProjection(CrudUtil.crudUtil(sql, searchText + "%", searchText + "%", searchText + "%"));
    }

    public static ArrayList<EmployeeAttendanceProjection> findAttendanceEmployeeByDate(String date) throws SQLException, ClassNotFoundException {
        String sql = "SELECT employee.employee_id,employee.fist_name,employee.last_name,employee.roll,employee_attendance.date,employee_attendance.time FROM employee_attendance INNER JOIN Employee ON Employee_Attendance.employee_id = Employee.employee_id WHERE employee_attendance.date LIKE ?";
        return setEmployeeProjection(CrudUtil.crudUtil(sql,date+"%"));
    }

    private static ArrayList<EmployeeAttendanceProjection> setEmployeeProjection(ResultSet resultSet) throws SQLException {
        ArrayList<EmployeeAttendanceProjection> list = new ArrayList<>();
        while (resultSet.next()) {
            EmployeeAttendanceProjection employeeAttendanceProjection = new EmployeeAttendanceProjection();

            employeeAttendanceProjection.setId(resultSet.getString(1));
            employeeAttendanceProjection.setFistName(resultSet.getString(2));
            employeeAttendanceProjection.setLastName(resultSet.getString(3));
            employeeAttendanceProjection.setRoll(resultSet.getString(4));
            employeeAttendanceProjection.setAttendanceDate(resultSet.getString(5));
            employeeAttendanceProjection.setAttendanceTime(resultSet.getString(6));

            list.add(employeeAttendanceProjection);
        }
        return list;
    }

   /* public static ResultSet getAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee_id FROM employee_attendance  WHERE date=?", DateTimeUtil.dateNow());
    }*/
   /* public static ResultSet getSearchId(String date, String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee_id FROM employee_attendance WHERE date=? AND employee_id LIKE ?",date,id);
    }
   public static ResultSet searchFistName(String text) throws SQLException, ClassNotFoundException {
       return CrudUtil.crudUtil("SELECT employee.employee_id,employee.fist_name,employee.last_name,employee.roll,employee_attendance.date,employee_attendance.time FROM employee_attendance INNER JOIN Employee ON Employee_Attendance.employee_id = Employee.employee_id WHERE Employee.fist_name=?",text);
   }

    public static ResultSet searchLastName(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee.employee_id,employee.fist_name,employee.last_name,employee.roll,employee_attendance.date,employee_attendance.time FROM employee_attendance INNER JOIN Employee ON Employee_Attendance.employee_id = Employee.employee_id WHERE Employee.last_name=?",text);
    }*/
}
