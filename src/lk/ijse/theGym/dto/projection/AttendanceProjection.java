package lk.ijse.theGym.dto.projection;

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
public class AttendanceProjection {
private String id;
private String fistName;
private String lastName;
private String date;
private String time;
}
