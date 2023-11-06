package tech.chickies.notificationserver.mk.consumer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExportMessage {

    private String apiEndpoint;

    private String emailSender;
}

