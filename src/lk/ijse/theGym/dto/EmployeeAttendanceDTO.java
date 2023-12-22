package lk.ijse.theGym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sasindu Malshan
 * @project The-Gym
 * @date 12/22/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class EmployeeAttendanceDTO {
    private String date;
    private String time;
    private String employee_id;

}
