package rest.model.response.pet;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class DeletePetResponse {
    private long code;
    private String type;
    private String message;


    public DeletePetResponse() {
    }

}
