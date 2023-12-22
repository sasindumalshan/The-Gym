package lk.ijse.theGym.dto;

import lombok.*;

/**
 * @author Sasindu Malshan
 * @project The-Gym
 * @date 12/21/2023
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ItemDTO {
    private String item_id;
    private String item_name;
    private String category;
    private int qut;
    private double price;
    private String brand;
    private String description;

}
