package lk.ijse.theGym.model;

import lk.ijse.theGym.dto.CoachSalaryDetailsDTO;
import lk.ijse.theGym.to.CoachSalaryDetails;
import lk.ijse.theGym.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoachSalaryModel {

    public static List<CoachSalaryDetailsDTO> findCoachSalaryDetails(String date) throws SQLException, ClassNotFoundException {
        return setDTOs(CrudUtil.crudUtil("SELECT * FROM coach_salary_details WHERE date LIKE ?",date+"%"));
    }

    private static List<CoachSalaryDetailsDTO> setDTOs(ResultSet resultSet) throws SQLException {
        List<CoachSalaryDetailsDTO> list=new ArrayList<>();
        while (resultSet.next()){
            CoachSalaryDetailsDTO dto=new CoachSalaryDetailsDTO();
            dto.setCoach_id(resultSet.getString(1));
            dto.setDate(resultSet.getString(2));
            dto.setPrice(resultSet.getString(3));
            dto.setSalary_id(resultSet.getString(4));

            list.add(dto);
        }
        return list;
    }
}
