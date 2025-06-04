package update;

import utils.PetStoreTestBase;
import org.testng.annotations.Test;
import rest.model.request.pet.PetCreate;
import rest.model.request.pet.PetUpdateRequest;
import rest.model.response.pet.PetValidatableResponse;


public class UpdatePetWithPostMethod extends PetStoreTestBase {

    @Test()
    public void updatePet() throws InterruptedException {
        PetValidatableResponse response = testClient.createPet(PetCreate.defaultOf())
                .checkStatusCode(200);
        long petId = response.getId();
        Thread.sleep(5000);
        PetUpdateRequest petUpdate = new PetUpdateRequest(petId, "name", "pending");
        testClient.updatePet(petId, petUpdate);
        Thread.sleep(5000);
        response.checkStatusCode(200);

    }
}