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
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ScheduleDetailsDTO {
    private String exercises_id;
    private String steps;
    private String schedule_id;

}
