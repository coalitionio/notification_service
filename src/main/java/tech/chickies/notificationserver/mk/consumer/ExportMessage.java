package tech.chickies.notificationserver.mk.consumer;

import jakarta.annotation.Nullable;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ExportMessage  {

    private String apiEndpoint;

    @Nullable
    private String emailSender = "phonglethanh2@gmail.com";
}

