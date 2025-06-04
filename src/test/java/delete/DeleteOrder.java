package delete;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import rest.client.TestClient;
import rest.model.request.order.Order;
import rest.model.request.pet.PetCreate;
import rest.model.response.order.OrderValidatableResponse;
import utils.PetStoreTestBase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;


public class DeleteOrder extends PetStoreTestBase {
    private long id;
    Order order = new Order();
    TestClient client = new TestClient();


    @BeforeClass
    public void setUp() {
        id = testClient.createPet(PetCreate.defaultOf())
                .checkStatusCode(200)
                .getId();
    }

    @Test
    public void deleteOrder() throws InterruptedException {
        order.setPetId(id);
        Thread.sleep(5000);
        OrderValidatableResponse response = client.createOrder(order)
                .checkStatusCode(200)
                .checkIdNotNull()
                .checkOrder(order);
        Thread.sleep(5000);
        long orderId = response.getId();
        Thread.sleep(10000);
        testClient.deleteOrder(orderId);
        response.checkStatusCode(200);
    }

    @Test
    public void deleteOrderNegative() throws InterruptedException {
        try {
            testClient.deleteOrder(0);
            fail("Ожидалось исключение IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Order not found with id: 0", e.getMessage());
        }
        Thread.sleep(5000);
    }
    }

