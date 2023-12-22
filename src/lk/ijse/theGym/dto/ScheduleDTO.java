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
public class ScheduleDTO {
    private String customer_id;
    private String schedule_id;
    private String coach_id;

}
