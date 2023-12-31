package lk.ijse.theGym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Sasindu Malshan
 * @project The-Gym
 * @date 12/29/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CoachDTO {
    private String coach_id;
    private String fist_name;
    private String last_name;
    private String address_street;
    private String address_city;
    private String address_lene;
    private String e_mail;
    private String birthday;
    private String nic;
    private String contact_number;
    private String gender;
    private String Register_date;
    private String salary_id;
    private String coach_type;

}
