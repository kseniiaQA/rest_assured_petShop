package create.pet;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rest.client.TestClient;
import rest.model.request.Pet;
import rest.model.response.pet.PetValidatableResponse;


public class CreatePetTest {

    @Test(dataProvider = "createPets")
    public void testCreatePet(Pet pet) throws InterruptedException {
        TestClient client = new TestClient();
        PetValidatableResponse response = client.create(pet)
                .checkStatusCode(200)
                .checkIdNotNull()
                .checkPet(pet);
        Thread.sleep(5000);
        long petId = response.getId();
        client.read(petId)
                .checkStatusCode(200)
                .checkId(petId)
                .checkPet(pet);
    }

    @DataProvider
    public Object[][] createPets() {
        return new Object[][] {
                { Pet.defaultOf()},
        };
    }
}
