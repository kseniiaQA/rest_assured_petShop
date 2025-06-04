package update;

import utils.PetData;
import utils.PetStoreTestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rest.model.request.pet.PetCreate;
import rest.model.response.pet.UploadImageResponse;
import java.io.File;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UploadImageForPetCreate extends PetStoreTestBase  {

    private long id;

    @BeforeClass
    public void setUp() {
        id = testClient.createPet(PetCreate.defaultOf())
                .checkStatusCode(200)
                .getId();
    }

    @Test(dataProvider = "positive", dataProviderClass = PetData.class)
    public void testUpdateImage(PetCreate petCreate) throws InterruptedException {
        File imageFile = new File("src/test/resources/test-image.jpg");
        String metadata = "Test image upload";

        UploadImageResponse uploadResponse = testClient.updateImageForPet(id, imageFile, metadata);

        assertEquals(200, uploadResponse.getCode());
        assertEquals("unknown", uploadResponse.getType());
        assertTrue(uploadResponse.getMessage().contains("test-image.jpg"));

        Thread.sleep(3000);

        testClient.readPet(id)
                .checkStatusCode(200)
                .checkId(id)
                .checkPet(petCreate);
    }
}
