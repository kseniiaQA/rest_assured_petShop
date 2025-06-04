package rest.model.response.pet;

import io.restassured.response.Response;

public class DeletedPetValidatableResponse {

    private Response response;
    private DeletePetResponseOrder deletedModel;


    public DeletedPetValidatableResponse(Response response) {
        this.response = response;
        if (response.asString().length() > 0) {
            deletedModel = response.as(DeletePetResponseOrder.class);
        }
    }


    public DeletedPetValidatableResponse checkStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
        return this;
    }
}
