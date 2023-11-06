package tech.chickies.notificationserver.mk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse implements Serializable {
    public String id;
    public int no ;
    public double totalPrice ;
    public int totalQuantity ;
    public double surcharge ;
    public String status ;
    public CustomerRes customer;
    public MealRes meal;
    public String createdDate;

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id='" + id + '\'' +
                ", no=" + no +
                ", totalPrice=" + totalPrice +
                ", totalQuantity=" + totalQuantity +
                ", surcharge=" + surcharge +
                ", status='" + status + '\'' +
                '}';
    }
}
