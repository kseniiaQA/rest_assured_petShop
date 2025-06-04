package utils;

import rest.client.TestClient;

public class PetStoreTestBase {
    protected static TestClient testClient;

    static {
        testClient = new TestClient();;
    }
}
