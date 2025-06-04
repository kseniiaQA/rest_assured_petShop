package shop;

import utils.PetStoreTestBase;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rest.client.TestClient;
import rest.model.request.order.Order;
import rest.model.request.pet.PetOrder;
import rest.model.response.order.OrderValidatableResponse;


public class CreateOrder extends PetStoreTestBase {

    private long id;

    @BeforeClass
    public void setUp() {
        id = testClient.create(PetOrder.defaultOf())
                .checkStatusCode(200)
                .getId();
    }

    @Test(dataProvider = "createOrders")
    public void testCreateOrder(Order order) throws InterruptedException {
        order.setPetId(id);
        Thread.sleep(5000);
        TestClient client = new TestClient();
        OrderValidatableResponse response = client.create(order)
                .checkStatusCode(200)
                .checkIdNotNull()
                .checkOrder(order);
        Thread.sleep(5000);
        long orderId = response.getId();
        Thread.sleep(5000);
        client.readOrder(orderId)
                .checkStatusCode(200)
                .checkId(orderId)
                .checkOrder(order);
    }

    @DataProvider
    public Object[][] createOrders() {
        return new Object[][] {
                { Order.defaultOf()},

        };
    }
}