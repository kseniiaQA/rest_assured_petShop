package create;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rest.client.TestClient;
import rest.model.request.pet.PetOrder;
import rest.model.response.pet.PetValidatableResponse;

public class CreatePetTestOrder {

    @Test(dataProvider = "createPets")
    public void testCreatePet(PetOrder petOrder) throws InterruptedException {
        TestClient client = new TestClient();
        PetValidatableResponse response = client.create(petOrder)
                .checkStatusCode(200)
                .checkIdNotNull()
                .checkPet(petOrder);
        Thread.sleep(5000);
        long petId = response.getId();
        Thread.sleep(5000);
        client.read(petId)
                .checkStatusCode(200)
                .checkId(petId)
                .checkPet(petOrder);
    }

    @DataProvider
    public Object[][] createPets() {
        return new Object[][] {
                { PetOrder.defaultOf()},
                { PetOrder.defaultOf().setCategory(new PetOrder.Category(0, null))},
                { PetOrder.defaultOf().setName(RandomStringUtils.randomAlphabetic(256)) },
                { PetOrder.defaultOf().setName(RandomStringUtils.randomAlphabetic(0)) },
        };
    }
}
