package rest.model.response.user;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import rest.model.request.pet.PetCreate;
import rest.model.request.pet.UserCreate;
import rest.model.response.pet.PetValidatableResponse;


import java.util.Map;

public class UserValidatableResponse {
    private UserCreateResponse model;
    private Response response;


    public UserValidatableResponse(Response response) {
        this.response = response;
        if (response.asString().length() > 0) {
            model = response.as(UserCreateResponse.class);
        }
    }

    public UserValidatableResponse checkStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
        return this;
    }


    public UserValidatableResponse checkIdNotNull() {
        response.then().body("id", Matchers.notNullValue());
        return this;
    }

    public UserValidatableResponse checkUser(UserCreate expected) {
        Map<String, Object> responseMap = response.jsonPath().getMap("$");
        Assert.assertEquals(3, responseMap.size(), "Количество параметров в ответе должно быть 3");
        return this;
    }

    public UserValidatableResponse checkId(long id) {
        response.then().body("id", Matchers.equalTo(id));
        return this;
    }


    public Long getId() {
        return response.jsonPath().getLong("id");
    }
}


