package lk.ijse.theGym.modelController;

import lk.ijse.theGym.to.Employee;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeController {
    public static String empID;

    public static ResultSet ScrollPaneLoadData(String ids) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM employee WHERE employee_id=?", ids);
    }

    public static ResultSet loadAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee_id FROM employee");
    }

    public static ResultSet getLastId() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee_id FROM employee ORDER BY LENGTH(employee_id),employee_id");
    }

    public static boolean addData(Employee employee) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO employee VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?)",
                employee.getEmployee_id(),
                employee.getFist_name(),
                employee.getLast_name(),
                employee.getUsr_name(),
                employee.getPassword(),
                employee.getRoll(),
                employee.getAddress_street(),
                employee.getAddress_city(),
                employee.getAddress_lene(),
                employee.getE_mail(),
                employee.getBirthday(),
                employee.getNic(),
                employee.getContact_number(),
                employee.getDate(),
                employee.getSalary_Id()
        );

    }

    public static ResultSet getAllData() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM employee WHERE employee_id=?", empID);
    }

    public static boolean RemoveEmployee(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE  FROM employee WHERE employee_id=?", text);
    }

    public static ResultSet getAllDetails() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM employee");
    }

    public static boolean Update(Employee employee) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE employee SET fist_name=?,last_name=?,usr_name=?,password=?,roll=?,address_street=?,address_city=?,address_lene=?,e_mail=?,birthday=?,nic=?,contact_number=?,salary_Id=? WHERE employee_id=?",
                employee.getFist_name(),
                employee.getLast_name(),
                employee.getUsr_name(),
                employee.getPassword(),
                employee.getRoll(),
                employee.getAddress_street(),
                employee.getAddress_city(),
                employee.getAddress_lene(),
                employee.getE_mail(),
                employee.getBirthday(),
                employee.getNic(),
                employee.getContact_number(),
                employee.getSalary_Id(),
                empID
        );
    }

    public static ResultSet searchId(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM employee WHERE employee_id=?",id);
    }

    public static ResultSet searchFistName(String FName) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM employee WHERE fist_name=?",FName);
    }

    public static ResultSet searchLastName(String LName) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT * FROM employee WHERE last_name=?",LName);
    }


    public static ResultSet getAllEmployeeCount() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM employee");
    }

    public static ResultSet searchDate(String dateNow) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee.employee_id,employee.fist_name,employee.last_name,employee.roll,employee_attendance.date,employee_attendance.time FROM employee_attendance INNER JOIN Employee ON Employee_Attendance.employee_id = Employee.employee_id WHERE employee_attendance.date=?",dateNow);
    }

    public static boolean idExists(String id) throws SQLException, ClassNotFoundException {
        ResultSet set=CrudUtil.crudUtil("SELECT employee_id FROM employee WHERE employee_id=?",id);
        return set.next();
    }

    public static ResultSet getAllIds() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT employee_id FROM  employee");
    }

    public static ResultSet getDataForId(String cus_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT fist_name,last_name ,nic,employee_id,salary_id FROM employee WHERE employee_id=?",cus_id);
    }

    public static ResultSet getSalaryCount(String salary_id) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM employee WHERE salary_Id=?",salary_id);
    }

    public static ResultSet getCoachCunt() throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT COUNT(*) FROM employee");
    }

    public static ResultSet getSearchData(String text) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("SELECT  employee_id FROM employee WHERE employee_id LIKE ? OR fist_name LIKE ? OR last_name LIKE ?",text+"%",text+"%",text+"%");
    }
}
