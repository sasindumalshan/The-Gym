package lk.ijse.theGym.model;

import lk.ijse.theGym.dto.EmployeeSalaryDetailsDTO;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeSalaryDetailsModel {

    public static boolean save(EmployeeSalaryDetailsDTO employeeSalaryDetailsDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO employee_salary_details VALUES (?,?,?,?)",
                employeeSalaryDetailsDTO.getEmployee_id(),
                employeeSalaryDetailsDTO.getDate(),
                employeeSalaryDetailsDTO.getPrice(),
                employeeSalaryDetailsDTO.getSalary_id()
        );
    }

    public static List<String> findPriceByDate(String year) throws SQLException, ClassNotFoundException {
        return setList(CrudUtil.crudUtil("SELECT price FROM employee_salary_details WHERE date LIKE ?", year + "%"));

    }

    public static List<String> findDistinctDate() throws SQLException, ClassNotFoundException {
        return setList(CrudUtil.crudUtil("SELECT DISTINCT date FROM employee_salary_details"));
    }

    public static List<EmployeeSalaryDetailsDTO> findEmployeeSalaryDetailsByEmployeeIdAndDateLike(String date, String id) throws SQLException, ClassNotFoundException {
        return setDTOs(CrudUtil.crudUtil("SELECT * FROM employee_salary_details WHERE employee_id=? AND date LIKE ?", id, date + "%"));
    }

    public static List<EmployeeSalaryDetailsDTO> findEmployeeSalaryByLikeDate(String date) throws SQLException, ClassNotFoundException {
        return setDTOs( CrudUtil.crudUtil("SELECT * FROM employee_salary_details WHERE date LIKE ?",date+"%"));
    }

    public static String sumByDate(String date) throws SQLException, ClassNotFoundException {
        return setString(CrudUtil.crudUtil("SELECT SUM(price) FROM employee_salary_details WHERE date=?", date));
    }

    private static String setString(ResultSet result) throws SQLException {
        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    private static List<String> setList(ResultSet resultSet) throws SQLException {
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }

    private static List<EmployeeSalaryDetailsDTO> setDTOs(ResultSet resultSet) throws SQLException {
        List<EmployeeSalaryDetailsDTO> list=new ArrayList<>();
        while (resultSet.next()){

            EmployeeSalaryDetailsDTO dto=new EmployeeSalaryDetailsDTO();
            dto.setEmployee_id(resultSet.getString(1));
            dto.setDate(resultSet.getString(2));
            dto.setPrice(Double.parseDouble(resultSet.getString(3)));
            dto.setSalary_id(resultSet.getString(4));

            list.add(dto);
        }
        return list;
    }
}
