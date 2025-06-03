package rest.model.response.pet;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import rest.model.request.Pet;


public class PetValidatableResponse {

    private PetResponse model;
    private Response response;

    public PetValidatableResponse(Response response) {
        this.response = response;
        if (response.asString().length() > 0) {
            model = response.as(PetResponse.class);
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

    public PetValidatableResponse checkPet(Pet expected) {
        Assert.assertEquals(model.getPhotoUrls(), expected.getPhotoUrls(), "Url does not match");
        Assert.assertEquals(model.getName(), expected.getName(), "Name does not match");
        Assert.assertEquals(model.getCategory(), expected.getCategory(), "Category does not match");
        return this;
    }

    public PetValidatableResponse checkId(long id) {
        response.then().body("id", Matchers.equalTo(id));
        return this;
    }

    public PetValidatableResponse checkErrorResponse(PetResponse expected) {
        response.then().body("timestamp", Matchers.notNullValue());
        Assert.assertEquals(model.getError(), expected.getError(), "Error message does not match");
        Assert.assertEquals(model.getPath(), expected.getPath(), "Path does not match");
        return this;
    }

    public Long getId() {
        return response.jsonPath().getLong("id");
    }
}
