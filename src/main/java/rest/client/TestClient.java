package rest.client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import props.TestConfig;
import rest.model.request.order.Order;
import rest.model.request.pet.PetCreate;
import rest.model.request.pet.PetUpdateRequest;
import rest.model.request.pet.UserCreate;
import rest.model.response.order.OrderValidatableResponse;
import rest.model.response.pet.DeletedPetValidatableResponse;
import rest.model.response.pet.PetValidatableResponse;
import rest.model.response.pet.UploadImageResponse;
import rest.model.response.user.DeletedUserValidatableResponse;
import rest.model.response.user.UserValidatableResponse;


import java.io.File;
import static io.restassured.RestAssured.given;


@AllArgsConstructor
public class TestClient {

    static {
        // Устанавливаем JSON парсер по умолчанию
        RestAssured.defaultParser = Parser.JSON;
    }

    private String baseUri;
    private String basePath;

    public TestClient() {
        this(TestConfig.Uri.value, TestConfig.Path.value);
    }

    private RequestSpecification getRequestSpec() {
        RestAssured.useRelaxedHTTPSValidation();
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

    private RequestSpecification getRequestSpecForUpdate() {
        RestAssured.useRelaxedHTTPSValidation();
        return RestAssured.given()
                .noContentType()
                .baseUri(baseUri)
                .basePath(basePath)
                .log().all(); // Логируем все запросы
    }

    private RequestSpecification getRequestSpecForUpdate(Object body) {
        return getRequestSpecForUpdate()
                .body(body); // Устанавливаем тело запроса
    }


    public UserValidatableResponse createUser(UserCreate userCreate) {
        Response response = getRequestSpec(userCreate).when()
                .post("/user/");
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().log().all();
        if (!response.getContentType().contains("application/json")) {
            throw new IllegalStateException("Unexpected Content-Type: " + response.getContentType());
        }
        return new UserValidatableResponse(response);
    }

    public PetValidatableResponse createPet(PetCreate petCreate) {
        Response response = getRequestSpec(petCreate).when()
                .post("/pet/");
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());
        response.then().log().all();
        if (!response.getContentType().contains("application/json")) {
            throw new IllegalStateException("Unexpected Content-Type: " + response.getContentType());
        }
        return new PetValidatableResponse(response);
    }

    public OrderValidatableResponse createOrder(Order order) {
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

    public PetValidatableResponse updatePet(long id, PetUpdateRequest petUpdateRequest) {
        Response response = getRequestSpecForUpdate(petUpdateRequest).when()
                .post("/pet/{id}", id);
        response.then().log().all();
        return new PetValidatableResponse(response);
    }


    public PetValidatableResponse putPet(long id, PetCreate petCreate) {
        Response response = getRequestSpec(petCreate).when()
                .put("/pet/", id);
        response.then().log().all();
        return new PetValidatableResponse(response);
    }

    public UploadImageResponse updateImageForPet(long petId, File imageFile, String additionalMetadata) {
        Response response = getRequestSpec()
                .multiPart("file", imageFile)
                .multiPart("additionalMetadata", additionalMetadata)
                .when()
                .post("/pet/{petId}/uploadImage", petId);

        response.then().log().all()
                .statusCode(200);
        return response.as(UploadImageResponse.class);
    }

    public UserValidatableResponse readUser(long id) {
        Response response = getRequestSpec().when().
                get("/user/{id}", id);

        response.then().log().all();
        return new UserValidatableResponse(response);
    }

    public PetValidatableResponse readPet(long id) {
        Response response = getRequestSpec().when().
                get("/pet/{id}", id);

        response.then().log().all();
        return new PetValidatableResponse(response);
    }
    public DeletedPetValidatableResponse readDeletedPet(long id) {
        Response response = getRequestSpec().when().
                get("/pet/{id}", id);
        response.then().log().all();
        return new DeletedPetValidatableResponse(response);
    }

    public DeletedUserValidatableResponse readDeletedUser(long id) {
        Response response = getRequestSpec().when().
                get("/user/{id}", id);
        response.then().log().all();
        return new DeletedUserValidatableResponse(response);
    }
 

    public OrderValidatableResponse readOrder(long id) {
        Response response = getRequestSpec().when().
                get("store/order/{id}", id);

        response.then().log().all();
        return new OrderValidatableResponse(response);
    }


    public void deletePet(long id) {
        Response getResponse = getRequestSpec()
                .when()
                .delete("/pet/{id}", id);

        if (getResponse.getStatusCode() == 404) {
            throw new IllegalArgumentException("PetCreate not found with id: " + id);
        }
        Response deleteResponse = getRequestSpec()
                .when()
                .delete("/pet/{id}", id);
        if (deleteResponse.getStatusCode() != 200) {
            throw new RuntimeException("Failed to delete pet: " + deleteResponse.getBody().asString());
        }
    }

    public void deleteOrder(long id) {
        Response getResponse = getRequestSpec()
                .when()
                .delete("store/order/{id}", id);

        if (getResponse.getStatusCode() == 404) {
            throw new IllegalArgumentException("Order not found with id: " + id);
        }
        Response deleteResponse = getRequestSpec()
                .when()
                .delete("store/order/{id}", id);
        if (deleteResponse.getStatusCode() != 200) {
            throw new RuntimeException("Failed to delete oder: " + deleteResponse.getBody().asString());
        }
    }


    public void deleteUser(long id) {
        Response getResponse = getRequestSpec()
                .when()
                .delete("/user/{id}", id);

        if (getResponse.getStatusCode() == 404) {
            throw new IllegalArgumentException("UserCreate not found with id: " + id);
        }
        Response deleteResponse = getRequestSpec()
                .when()
                .delete("/user/{id}", id);
        if (deleteResponse.getStatusCode() != 200) {
            throw new RuntimeException("Failed to delete user: " + deleteResponse.getBody().asString());
        }
    }
}