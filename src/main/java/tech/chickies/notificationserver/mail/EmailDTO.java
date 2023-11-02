package tech.chickies.notificationserver.mail;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class EmailDTO {
    private String to;
    private List<String> tos;
    private String from;
    private String template;
    private String content;
    private Map<String, Object> context;
}
