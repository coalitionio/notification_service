package tech.chickies.notificationserver.mk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KitchenRes implements Serializable {
    private int no;
    private String id;
    private String name;
    private String address;
    private String imgUrl;
    private Integer noOfDish;
    private Integer noOfTray;
    private Integer noOfMeal;
    private Float rating;
}