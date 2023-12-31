package lk.ijse.theGym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sasindu Malshan
 * @project The-Gym
 * @date 12/24/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SalaryDTO {
    private String salary_Id;
    private String role;
    private double salary;
}
