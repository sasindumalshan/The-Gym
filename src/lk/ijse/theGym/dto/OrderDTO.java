package lk.ijse.theGym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sasindu Malshan
 * @project The-Gym
 * @date 12/21/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderDTO {
    private String order_id;
    private String time;
    private String date;
    private double final_total;
    private String customer_id;

}
