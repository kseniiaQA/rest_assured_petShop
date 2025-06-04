package create;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rest.client.TestClient;
import rest.model.request.pet.PetCreate;
import rest.model.response.pet.PetValidatableResponse;

public class CreatePet {

    @Test(dataProvider = "createPets")
    public void testCreatePet(PetCreate petCreate) throws InterruptedException {
        TestClient client = new TestClient();
        PetValidatableResponse response = client.createPet(petCreate)
                .checkStatusCode(200)
                .checkIdNotNull()
                .checkPet(petCreate);
        Thread.sleep(5000);
        long petId = response.getId();
        Thread.sleep(5000);
        client.readPet(petId)
                .checkStatusCode(200)
                .checkId(petId)
                .checkPet(petCreate);
    }

    @DataProvider
    public Object[][] createPets() {
        return new Object[][] {
                { PetCreate.defaultOf()},
                { PetCreate.defaultOf().setCategory(new PetCreate.Category(0, null))},
                { PetCreate.defaultOf().setName(RandomStringUtils.randomAlphabetic(256)) },
                { PetCreate.defaultOf().setName(RandomStringUtils.randomAlphabetic(0)) },
        };
    }
}
