package tech.chickies.notificationserver.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

public class MailSendingRequest {
    @Data
    @AllArgsConstructor
    public class EmailDTO {
        private String to;
        private String from;
        private String template;
        private String content = "";
        private Map<String, Object> context;
    }

}
