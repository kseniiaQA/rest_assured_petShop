package rest.model.response.pet;

import lombok.*;
import lombok.experimental.Accessors;
import rest.model.request.pet.PetCreate;

@Accessors(chain = true)
@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PetCreateResponse extends PetCreate {

    private long id;

    @EqualsAndHashCode.Exclude
    private String status;
    private String error;
    private String path;

}
