package rest.model.request.pet;


import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@NoArgsConstructor

public class UserCreate {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private int userStatus;

    public UserCreate(UserCreate userCreate) {
        this.id = userCreate.id;
        this.username = userCreate.username;
        this.firstName = userCreate.firstName;
        this.lastName = userCreate.lastName;
        this.email = userCreate.email;
        this.password = userCreate.password;
        this.phone = userCreate.phone;
        this.userStatus = userCreate.userStatus;
    }

}
