package client;

import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.*;
import org.glassfish.jersey.client.ClientConfig;
import shared.Account;

import java.net.URI;

public class TestClient {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/";

    // ID of account for test
    public static final String ID = "123";

    // Web target with base URI
    public static WebTarget target;

    public static void main(String[] args)  {
        Client client = ClientBuilder.newClient( new ClientConfig());
        target = client.target(getBaseURI());

        /* Create new account */
        createAccount();

        /* Get account balance */
        getBalance();

        /* Send transaction money from an account to other */
        sendTransaction();
    }

    private static void createAccount() {
        Invocation.Builder inv = target.path("account").
                path(ID).
                request();

        // TODO: Create a Json file to use Account class
        Response response = inv.post(Entity.entity("json", MediaType.TEXT_PLAIN));

        String output = response.readEntity(String.class);

        String request = inv.accept(MediaType.TEXT_PLAIN).
                post(Entity.entity("json", MediaType.TEXT_PLAIN)).
                toString();

        System.out.println();
        System.out.println("Request:    " + request);
        System.out.println("Output:     " + output);
    }

    private static void getBalance() {
        Invocation.Builder inv = target.path("account").
                path(ID).
                request();

        Response response = inv.get();
        String output = response.readEntity(String.class);

        String request = inv.accept(MediaType.TEXT_PLAIN).
                get(Response.class).
                toString();

        System.out.println();
        System.out.println("Request:    " + request);
        System.out.println("Output:     " + output);
    }

    private static void sendTransaction() {
        Invocation.Builder inv = target.path("transaction").
                path(ID).
                path("321").
                path("10").
                request();

        // TODO: Create a Json file to use Account class
        Response response = inv.put(Entity.entity("json", MediaType.TEXT_PLAIN));

        String output = response.readEntity(String.class);

        String request = inv.accept(MediaType.TEXT_PLAIN).
                put(Entity.entity("json", MediaType.TEXT_PLAIN)).
                toString();

        System.out.println();
        System.out.println("Request:    " + request);
        System.out.println("Output:     " + output);
    }



    /** TODO:
     *   GetExtract();
     *   GetTotalValue();
     *   GetGlobalLedgerValue();
     *   GetLedger();
     */

    private static URI getBaseURI() {
        return UriBuilder.fromUri(BASE_URI).build();
    }
}
