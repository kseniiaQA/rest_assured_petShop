package delete;

import io.restassured.response.Response;
import rest.model.request.pet.PetCreate;
import rest.model.response.pet.DeletedPetValidatableResponse;
import utils.PetStoreTestBase;
import org.testng.annotations.Test;
import rest.model.response.pet.PetValidatableResponse;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class DeletePet extends PetStoreTestBase {

    @Test
    public void deletePet() throws InterruptedException {
        PetValidatableResponse response = testClient.createPet(PetCreate.defaultOf())
                .checkStatusCode(200);
        long petId = response.getId();
        Thread.sleep(5000);
        testClient.deletePet(petId);
        Thread.sleep(5000);
        try {
        testClient.readDeletedPet(petId)
                .checkDeletedPet()
                .checkStatusCode(404);
            fail("Ожидалось исключение IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("PetCreate not found with id:" + petId, e.getMessage());
        }
    }
    @Test
    public void deletePetNegative() throws InterruptedException {
        try {
            testClient.deletePet(0);
            fail("Ожидалось исключение IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("PetCreate not found with id: 0", e.getMessage());
        }
        Thread.sleep(5000);
    }
}

