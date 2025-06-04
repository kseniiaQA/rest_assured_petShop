package rest.client;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.AllArgsConstructor;
import props.TestConfig;
import rest.model.request.order.Order;
import rest.model.request.pet.PetOrder;
import rest.model.request.pet.PetUpdateRequest;
import rest.model.response.order.OrderValidatableResponse;
import rest.model.response.pet.DeletedPetValidatableResponse;
import rest.model.response.pet.PetValidatableResponse;
import rest.model.response.pet.UploadImageResponse;


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

    public PetValidatableResponse create(PetOrder petOrder) {
        Response response = getRequestSpec(petOrder).when()
                .post("/petOrder/");

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

    public PetValidatableResponse update(long id, PetUpdateRequest petUpdateRequest) {
        Response response = getRequestSpecForUpdate(petUpdateRequest).when()
                .post("/pet/{id}", id);
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
                .statusCode(200);
        return response.as(UploadImageResponse.class);
    }

    public PetValidatableResponse read(long id) {
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


    public OrderValidatableResponse readOrder(long id) {
        Response response = getRequestSpec().when().
                get("store/order/{id}", id);

        response.then().log().all();
        return new OrderValidatableResponse(response);
    }


    public void delete(long id) {
        Response getResponse = getRequestSpec()
                .when()
                .get("/pet/{id}", id);

        if (getResponse.getStatusCode() == 404) {
            throw new IllegalArgumentException("PetOrder not found with id: " + id);
        }
        Response deleteResponse = getRequestSpec()
                .when()
                .delete("/pet/{id}", id);
        if (deleteResponse.getStatusCode() != 200) {
            throw new RuntimeException("Failed to delete pet: " + deleteResponse.getBody().asString());
        }
    }
}