package tech.chickies.notificationserver.mk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseObject<T>  implements Serializable {
    private String statusCode;

    private String message;

    private T data;
    private int pageNumber = 0;

    private int pageSize = 0;

    private int totalCount = 1;
}
