package rest.model.response.pet;

import io.restassured.response.Response;
import org.testng.Assert;
import java.util.Map;

public class DeletedPetValidatableResponse {

    private Response response;
    private DeletePetResponseCreate model;

    public DeletedPetValidatableResponse(Response response) {
        this.response = response;
        if (response.asString().length() > 0) {
            model = response.as(DeletePetResponseCreate.class);
        }
    }

    public DeletedPetValidatableResponse checkStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
        return this;
    }

    public DeletedPetValidatableResponse checkDeletedPet() {
        Map<String, Object> responseMap = response.jsonPath().getMap("$");
        Assert.assertEquals(3, responseMap.size(), "Количество параметров в ответе должно быть 4");
        return this;
    }
}
