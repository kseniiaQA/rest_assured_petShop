package update;

import utils.PetStoreTestBase;
import org.testng.annotations.Test;
import rest.model.request.pet.PetOrder;
import rest.model.request.pet.PetUpdateRequest;
import rest.model.response.pet.PetValidatableResponse;


public class UpdatePetTestOrder extends PetStoreTestBase {

    @Test()
    public void updatePet() throws InterruptedException {
        PetValidatableResponse response = testClient.create(PetOrder.defaultOf())
                .checkStatusCode(200);
        long petId = response.getId();
        Thread.sleep(5000);
        PetUpdateRequest petUpdate = new PetUpdateRequest(petId, "name", "pending");
        testClient.update(petId, petUpdate);
        Thread.sleep(5000);
        response.checkStatusCode(200);

    }
}