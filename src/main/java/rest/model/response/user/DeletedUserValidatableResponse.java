package rest.model.response.user;

import io.restassured.response.Response;
import org.testng.Assert;
import java.util.Map;

public class DeletedUserValidatableResponse {

    private Response response;
    private DeletedUserValidatableResponse model;

    public DeletedUserValidatableResponse(Response response) {
        this.response = response;
        if (response.asString().length() > 0) {
            model = response.as(DeletedUserValidatableResponse.class);
        }
    }

    public DeletedUserValidatableResponse checkDeletedUser() {
        Map<String, Object> responseMap = response.jsonPath().getMap("$");
        Assert.assertEquals(3, responseMap.size(), "Количество параметров в ответе должно быть 4");
        return this;
    }


    public DeletedUserValidatableResponse  checkStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
        return this;
    }
}
