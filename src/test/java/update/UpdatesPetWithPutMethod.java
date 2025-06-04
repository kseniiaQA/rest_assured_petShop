package update;

import org.testng.annotations.Test;
import rest.model.request.pet.PetCreate;
import rest.model.response.pet.PetValidatableResponse;
import utils.PetStoreTestBase;

public class UpdatesPetWithPutMethod extends PetStoreTestBase {

    @Test()
    public void updatePet() throws InterruptedException {
        PetCreate petCreate = PetCreate.defaultOf();

        PetValidatableResponse response = testClient.createPet(petCreate)
                .checkStatusCode(200);
        long petId = response.getId();
        Thread.sleep(5000);

        PetCreate updatedPetCreate = new PetCreate();
        updatedPetCreate.setId(petId);
        updatedPetCreate.setName("Updated Name");
        updatedPetCreate.setStatus("available");

        testClient.putPet(petId, updatedPetCreate);
        Thread.sleep(5000);
        response.checkStatusCode(200);
        testClient.readPet(petId)
                .checkStatusCode(200)
                .checkId(petId)
                .checkPet(updatedPetCreate);
    }
}