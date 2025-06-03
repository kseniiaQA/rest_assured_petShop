package update.pet;

import create.pet.PetData;
import create.pet.PetStoreTestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rest.model.request.Pet;
import rest.model.response.pet.UploadImageResponse;
import java.io.File;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UploadImageForPet extends PetStoreTestBase  {

    private long id;

    @BeforeClass
    public void setUp() {
        id = testClient.create(Pet.defaultOf())
                .checkStatusCode(200)
                .getId();
    }

    @Test(dataProvider = "positive", dataProviderClass = PetData.class)
    public void testUpdateImage(Pet pet) throws InterruptedException {
        File imageFile = new File("src/test/resources/test-image.jpg");
        String metadata = "Test image upload";

        UploadImageResponse uploadResponse = testClient.updateImage(id, imageFile, metadata);

        assertEquals(200, uploadResponse.getCode());
        assertEquals("unknown", uploadResponse.getType());
        assertTrue(uploadResponse.getMessage().contains("test-image.jpg"));

        Thread.sleep(3000);

        testClient.read(id)
                .checkStatusCode(200)
                .checkId(id)
                .checkPet(pet);
    }
}
