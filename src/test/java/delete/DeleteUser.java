package delete;

import org.testng.annotations.Test;
import rest.model.response.user.DeletedUserValidatableResponse;
import utils.PetStoreTestBase;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class DeleteUser extends PetStoreTestBase {

    @Test
    public void deletePetNegative() throws InterruptedException {
        DeletedUserValidatableResponse resp = null;
        try {
            testClient.deleteUser(0);
            resp.checkStatusCode(404);
            fail("Ожидалось исключение IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("UserCreate not found with id: 0", e.getMessage());
        }
    }
}
