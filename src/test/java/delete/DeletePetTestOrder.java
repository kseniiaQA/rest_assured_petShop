package delete;

import utils.PetStoreTestBase;
import org.testng.annotations.Test;
import rest.model.request.pet.PetOrder;
import rest.model.response.pet.PetValidatableResponse;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class DeletePetTestOrder extends PetStoreTestBase {

    @Test
    public void deletePet() throws InterruptedException {
        PetValidatableResponse response = testClient.create(PetOrder.defaultOf())
                .checkStatusCode(200);
        long petId = response.getId();
        Thread.sleep(5000);
        testClient.delete(petId);
        Thread.sleep(5000);
        try {
        testClient.readDeletedPet(petId)
                .checkStatusCode(404);
            fail("Ожидалось исключение IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("PetOrder not found with id:" + petId, e.getMessage());
        }
    }

    @Test
    public void deletePetNegative() throws InterruptedException {
        try {
            testClient.delete(0);
            fail("Ожидалось исключение IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("PetOrder not found with id: 0", e.getMessage());
        }
        Thread.sleep(5000);
    }
}

