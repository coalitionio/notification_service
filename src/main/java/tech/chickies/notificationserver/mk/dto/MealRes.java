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
public class MealRes implements Serializable {
    private String id;
    private int no;
    private String name;
    private Double price;
    private String  serviceFrom;
    private String serviceTo;
    private int serviceQuantity;
    private String closeTime;
    private KitchenRes kitchen;
    private TrayRes tray;
}
