package rest.model.request.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetUpdateRequest {
    private long id;
    private String name;
    private String status;
}