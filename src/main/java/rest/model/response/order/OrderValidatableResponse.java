package rest.model.response.order;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import rest.model.request.order.Order;

public class OrderValidatableResponse {

    private OrderResponse model;
    private Response response;


    public OrderValidatableResponse(Response response) {
        this.response = response;
        if (response.asString().length() > 0) {
            model = response.as(OrderResponse.class);
        }
    }

    public OrderValidatableResponse checkStatusCode(int statusCode) {
        response.then().statusCode(statusCode);
        return this;
    }

    public OrderValidatableResponse checkIdNotNull() {
        response.then().body("id", Matchers.notNullValue());
        return this;
    }

    public OrderValidatableResponse checkOrder(Order expected) {
        Assert.assertEquals(model.getQuantity(), expected.getQuantity(), "Url does not match");
        Assert.assertEquals(model.getShipDate(), expected.getShipDate(), "Name does not match");
        Assert.assertEquals(model.getPetId(), expected.getPetId(), "Category does not match");
        return this;
    }

    public OrderValidatableResponse checkId(long id) {
        response.then().body("id", Matchers.equalTo(id));
        return this;
    }

    public Long getId() {
        return response.jsonPath().getLong("id");
    }
}
