package rest.model.response.pet;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import rest.model.request.pet.PetCreate;

import java.util.Map;


public class PetValidatableResponse {

    private PetCreateResponse model;
    private Response response;


    public PetValidatableResponse(Response response) {
        this.response = response;
        if (response.asString().length() > 0) {
            model = response.as(PetCreateResponse.class);
        }
    }

    public PetValidatableResponse checkStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
        return this;
    }

    public PetValidatableResponse checkIdNotNull() {
        response.then().body("id", Matchers.notNullValue());
        return this;
    }

    public PetValidatableResponse checkPet(PetCreate expected) {
        Map<String, Object> responseMap = response.jsonPath().getMap("$");
        Assert.assertEquals(6, responseMap.size(), "Количество параметров в ответе должно быть 6");
        Assert.assertEquals(model.getPhotoUrls(), expected.getPhotoUrls(), "Url does not match");
        Assert.assertEquals(model.getName(), expected.getName(), "Name does not match");
        Assert.assertEquals(model.getCategory(), expected.getCategory(), "Category does not match");
        Assert.assertEquals(model.getStatus(), expected.getStatus(), "Name does not match");
        return this;
    }

    public PetValidatableResponse checkId(long id) {
        response.then().body("id", Matchers.equalTo(id));
        return this;
    }

    public PetValidatableResponse checkErrorResponse(PetCreateResponse expected) {
        response.then().body("timestamp", Matchers.notNullValue());
        Assert.assertEquals(model.getError(), expected.getError(), "Error message does not match");
        Assert.assertEquals(model.getPath(), expected.getPath(), "Path does not match");
        return this;
    }

    public Long getId() {
        return response.jsonPath().getLong("id");
    }
}
