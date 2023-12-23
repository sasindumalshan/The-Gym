package lk.ijse.theGym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sasindu Malshan
 * @project The-Gym
 * @date 12/23/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class EmployeeSalaryDetailsDTO {
    private String employee_id;
    private String date;
    private double price;
    private String salary_id;

}
