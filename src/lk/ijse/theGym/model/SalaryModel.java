package lk.ijse.theGym.model;

import lk.ijse.theGym.dto.SalaryDTO;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryModel {

    public static boolean save(SalaryDTO salaryDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("INSERT INTO salary VALUES (?,?,?)",
                salaryDTO.getSalary_Id(),
                salaryDTO.getRole(),
                salaryDTO.getSalary()
        );
    }

    public static boolean update(SalaryDTO salaryDTO) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("UPDATE salary SET salary=? ,role=? WHERE salary_Id=?",
                salaryDTO.getSalary(),
                salaryDTO.getRole(),
                salaryDTO.getSalary_Id()
        );
    }

    public static SalaryDTO findSalaryById(String salaryId) throws SQLException, ClassNotFoundException {
        return setDTO(CrudUtil.crudUtil("SELECT * FROM salary WHERE salary_Id=?", salaryId));
    }

    public static SalaryDTO findSalaryByRole(String role) throws SQLException, ClassNotFoundException {
        return setDTO(CrudUtil.crudUtil("SELECT * FROM Salary WHERE role=?", role));
    }

    public static List<SalaryDTO> findSalary() throws SQLException, ClassNotFoundException {
        return setDTOs(CrudUtil.crudUtil("SELECT * FROM salary"));
    }

    public static List<String> findSalaryIdOrderByLength() throws SQLException, ClassNotFoundException {
        return setStringList(CrudUtil.crudUtil("SELECT salary_Id FROM  salary ORDER BY LENGTH(salary_Id),salary_Id"));
    }

    private static SalaryDTO setDTO(ResultSet resultSet) throws SQLException {
        SalaryDTO salaryDTO = new SalaryDTO();
        if (resultSet.next()) {
            salaryDTO.setSalary_Id(resultSet.getString(1));
            salaryDTO.setRole(resultSet.getString(2));
            salaryDTO.setSalary(Double.parseDouble(resultSet.getString(3)));
        }
        return salaryDTO;
    }

    private static List<SalaryDTO> setDTOs(ResultSet resultSet) throws SQLException {
        List<SalaryDTO> list = new ArrayList<>();
        while (resultSet.next()) {
            SalaryDTO salaryDTO = new SalaryDTO();
            salaryDTO.setSalary_Id(resultSet.getString(1));
            salaryDTO.setRole(resultSet.getString(2));
            salaryDTO.setSalary(Double.parseDouble(resultSet.getString(3)));
            list.add(salaryDTO);
        }
        return list;
    }

    private static List<String> setStringList(ResultSet resultSet) throws SQLException {
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(1));
        }
        return list;
    }
}
