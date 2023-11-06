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
public class UserRes implements Serializable {
    private String id;
    private String email;
    private String fullName;
    private String avatarUrl;
    private String phone;
}
