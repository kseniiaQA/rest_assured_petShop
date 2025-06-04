package rest.model.response.user;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@NoArgsConstructor
public class UserCreateResponse {

    private int code;
    private String type;
    private String message;
}
