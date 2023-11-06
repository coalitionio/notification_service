package tech.chickies.notificationserver.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class EmailDTO {
    private String to;
    private String subject;
    private List<String> tos;
    private String from;
    private String template;
    private String content;
    private Map<String, Object> context;
}
