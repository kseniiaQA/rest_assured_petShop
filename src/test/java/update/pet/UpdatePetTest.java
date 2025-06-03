package update.pet;

import create.pet.PetData;
import create.pet.PetStoreTestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rest.model.request.Pet;


public class UpdatePetTest extends PetStoreTestBase {

    private long id;

    @BeforeClass
    public void setUp() {
        id = testClient.create(Pet.defaultOf())
                .checkStatusCode(200)
                .getId();
    }

    @Test(dataProvider = "positive", dataProviderClass = PetData.class)
    public void testUpdatePet(Pet pet) throws InterruptedException {
        testClient.update(id, pet)
                .checkStatusCode(200)
                .checkId(id)
                .checkPet(pet);
        Thread.sleep(5000);
        testClient.read(id)
                .checkStatusCode(200)
                .checkId(id)
                .checkPet(pet);
    }
}