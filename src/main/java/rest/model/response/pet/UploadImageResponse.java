package rest.model.response.pet;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
@ToString(callSuper = true)
@AllArgsConstructor
public class UploadImageResponse {

    private int code;
    private String type;
    private String message;


    public UploadImageResponse () {
    }

}
