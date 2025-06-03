package delete.pet;

import create.pet.PetStoreTestBase;
import org.testng.annotations.Test;
import rest.model.request.Pet;
import rest.model.response.pet.PetValidatableResponse;

public class DeletePetTest extends PetStoreTestBase {

    @Test
    public void deletePet() throws InterruptedException {
        PetValidatableResponse response = testClient.create(Pet.defaultOf())
                .checkStatusCode(200);
        long petId = response.getId();
        Thread.sleep(5000);
        testClient.delete(petId, "unknown");
        Thread.sleep(5000);
        testClient.read(petId)
                .checkStatusCode(200);
    }
}

