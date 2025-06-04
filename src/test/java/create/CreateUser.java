package create;

import lombok.Data;
import lombok.experimental.Accessors;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rest.client.TestClient;
import rest.model.request.pet.UserCreate;
import rest.model.response.user.UserValidatableResponse;
import utils.PetStoreTestBase;

@Accessors(chain = true)
@Data
public class CreateUser extends PetStoreTestBase {

    @DataProvider(name = "userData")
    public Object[][] userDataProvider() {
        UserCreate user1 = new UserCreate()
                .setId(1)
                .setUsername("user1")
                .setFirstName("First")
                .setLastName("Last")
                .setEmail("user1@example.com")
                .setPassword("pass")
                .setPhone("1234567890")
                .setUserStatus(1);

        return new Object[][] {
                { user1 }
        };
    }

    @Test(dataProvider = "userData")
    public void testCreateUser(UserCreate userCreate) throws InterruptedException {
        TestClient client = new TestClient();
        UserValidatableResponse response = client.createUser(userCreate)
                .checkStatusCode(200)
                .checkUser(userCreate);
    }
}
