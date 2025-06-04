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
public class DeletePetResponseCreate extends PetCreate {

    @EqualsAndHashCode.Exclude
    private int code;
    private String type;
    private String message;


}





