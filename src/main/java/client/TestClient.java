package client;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.*;
import org.glassfish.jersey.client.ClientConfig;

import java.net.URI;
import java.util.Arrays;

public class TestClient {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/";

    // ID of account for test
    public static final String ORIGIN = "123";
    public static final String DESTINATION = "321";

    // Web target with base URI
    public static WebTarget target;

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient(new ClientConfig());
        target = client.target(getBaseURI());

        /* Initial ledger and global value*/
        getLedger();
        getGlobalLedgerValue();

        /* Create new account */
        createAccount(ORIGIN);
        createAccount(DESTINATION);

        createAccount(ORIGIN);

        /* Get account balance */
        getBalance(ORIGIN);

        getBalance("456");

        /* Send transaction money from an account to other */
        sendTransaction( DESTINATION, ORIGIN,"10");
        getBalance(ORIGIN);
        getBalance(DESTINATION);

        /* Obtain current ledger */
        getLedger();
        getBalance(ORIGIN);

        /* Transactions */
        sendTransaction(DESTINATION, ORIGIN, "10");
        getBalance(ORIGIN);
        getBalance(DESTINATION);

        sendTransaction(ORIGIN, DESTINATION, "10");
        getBalance(DESTINATION);
        getBalance(ORIGIN);

        createAccount("432");

        /* Obtain final ledger and global value*/
        getLedger();
        getGlobalLedgerValue();

        /* Obtain the total value of account ids */
        getTotalValue(ORIGIN + "-" + DESTINATION + "-654");

        /* Get extract from account */
        getExtract(ORIGIN);
        getExtract(DESTINATION);
    }

    private static void createAccount(String id) {
        Invocation.Builder inv = target.path("account")
                .path(id)
                .request();

        Response response = inv.accept(MediaType.TEXT_PLAIN)
                .post(Entity.entity("json", MediaType.TEXT_PLAIN));

        String output = response.readEntity(String.class);
        String request = response.toString();

        System.out.println();
        System.out.println("Request:    " + request);
        System.out.println("Output:     " + output);
    }

    private static void getBalance(String id) {
        Invocation.Builder inv = target.path("account")
                .path(id)
                .request();

        Response response = inv.accept(MediaType.TEXT_PLAIN)
                .get(Response.class);

        String output = response.readEntity(String.class);
        String request = response.toString();

        System.out.println();
        System.out.println("Request:    " + request);
        System.out.println("Output:     " + output);
    }

    private static void sendTransaction(String origin, String destination, String value) {
        Invocation.Builder inv = target.path("transaction")
                .path(origin)
                .path(destination)
                .path(value)
                .request();

        Response response = inv.accept(MediaType.TEXT_PLAIN)
                .put(Entity.entity("json", MediaType.TEXT_PLAIN));

        String output = response.readEntity(String.class);
        String request = response.toString();

        System.out.println();
        System.out.println("Request:    " + request);
        System.out.println("Output:     " + output);
    }

    private static void getLedger() {
        Invocation.Builder inv = target.path("ledger")
                .request();

        Response response = inv.accept(MediaType.TEXT_PLAIN)
                .get(Response.class);

        String output = response.readEntity(String.class);
        String request = response.toString();

        System.out.println();
        System.out.println("Request:    " + request);
        System.out.println("Output:     \n" + output);
    }

    private static void getGlobalLedgerValue() {
        Invocation.Builder inv = target.path("global")
                .request();

        Response response = inv.accept(MediaType.TEXT_PLAIN)
                .get(Response.class);

        String output = response.readEntity(String.class);
        String request = response.toString();

        System.out.println();
        System.out.println("Request:    " + request);
        System.out.println("Output:     " + output);
    }

    private static void getTotalValue(String ids) {
        Invocation.Builder inv = target.path("total")
                .path(ids)
                .request();

        Response response = inv.accept(MediaType.TEXT_PLAIN)
                .get(Response.class);

        String output = response.readEntity(String.class);
        String request = response.toString();

        System.out.println();
        System.out.println("Request:    " + request);
        System.out.println("Output:     " + output);
    }

    private static void getExtract(String id) {
        Invocation.Builder inv = target.path("extract")
                .path(id)
                .request();

        Response response = inv.accept(MediaType.TEXT_PLAIN)
                .get(Response.class);

        String output = response.readEntity(String.class);
        String request = response.toString();

        System.out.println();
        System.out.println("Request:    " + request);
        System.out.println("Output:     \n" + output);
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri(BASE_URI).build();
    }
}
