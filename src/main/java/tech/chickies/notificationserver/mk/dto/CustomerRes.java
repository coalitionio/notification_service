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
public class CustomerRes implements Serializable {
    private int no;
    private String id;
    private String status;
    private Integer orderQuantity;
    private Double spentMoney;
    private String userId;
    private UserRes user;
}
