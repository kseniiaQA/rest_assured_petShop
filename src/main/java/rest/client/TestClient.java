package rest.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import props.TestConfig;
import rest.model.request.Order;
import rest.model.request.Pet;
import rest.model.response.pet.DeletePetResponse;
import rest.model.response.order.OrderValidatableResponse;
import rest.model.response.pet.PetValidatableResponse;
import rest.model.response.pet.UploadImageResponse;


import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@AllArgsConstructor
public class TestClient {

    private String baseUri;
    private String basePath;

    public TestClient() {
        this(TestConfig.Uri.value, TestConfig.Path.value);
    }

    private RequestSpecification getRequestSpec() {
        return given()
                .baseUri(baseUri)
                .basePath(basePath)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .log().all();
    }

    private RequestSpecification getRequestSpec(Object body) {
        return getRequestSpec().
                body(body);
    }

    public PetValidatableResponse create(Pet pet) {
        Response response = getRequestSpec(pet).when()
                .post("/pet/");

        // Логируем статус и тело ответа для диагностики
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        response.then().log().all();

        // Проверяем, что Content-Type соответствует ожидаемому
        if (!response.getContentType().contains("application/json")) {
            throw new IllegalStateException("Unexpected Content-Type: " + response.getContentType());
        }

        return new PetValidatableResponse(response);
    }

    public OrderValidatableResponse create(Order order) {
        Response response = getRequestSpec(order).when()
                .post("/store/order");

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().log().all();

        if (!response.getContentType().contains("application/json")) {
            throw new IllegalStateException("Unexpected Content-Type: " + response.getContentType());
        }

        return new OrderValidatableResponse(response);
    }

    public PetValidatableResponse update(long id, Pet Pet) {
        Response response = getRequestSpec(Pet).when().
                post("/pet/{id}", id);
        response.then().log().all();
        return new PetValidatableResponse(response);
    }

    public UploadImageResponse updateImage(long petId, File imageFile, String additionalMetadata) {
        Response response = getRequestSpec()
                .multiPart("file", imageFile)
                .multiPart("additionalMetadata", additionalMetadata)
                .when()
                .post("/pet/{petId}/uploadImage", petId);

        response.then().log().all()
                .statusCode(200); // или другой код, если ожидается другой

        return response.as(UploadImageResponse.class);
    }

    public PetValidatableResponse read(long id) {
        Response response = getRequestSpec().when().
                get("/pet/{id}", id);

        response.then().log().all();
        return new PetValidatableResponse(response);
    }

    public OrderValidatableResponse readOrder(long id) {
        Response response = getRequestSpec().when().
                get("store/order/{id}", id);

        response.then().log().all();
        return new OrderValidatableResponse(response);
    }

    public DeletePetResponse delete(long id, String expectedType) {
        Response response = getRequestSpec()
                .when()
                .delete("/pet/{id}", id);
        response.then().log().all()
                .body("type", equalTo(expectedType));
        DeletePetResponse apiResponse = response.as(DeletePetResponse.class);
        return apiResponse;
    }
}
