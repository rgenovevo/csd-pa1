package server;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Root resource (exposed at "/" path)
 */
@Path("/")
public class ServerResource {

    /**
     * Operation to create a new account with respective id.
     *
     * @param account - String with id of the account to create.
     * @return Response with status and output message of operation.
     */
    @POST
    @Path("account/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response CreateAccount(@PathParam("id") String account) {
        //account.create();
        loadmoney();

        String output = "New account created. {ID = " + account + "}";
        return Response.status(200).entity(output).build();
    }

    /**
     * Obtains the current balance of the account in argument, with authentication and integrity attestation proofs.
     *
     * @param account - id of account to get balance
     * @return Response with status and output message of operation.
     */
    @GET
    @Path("account/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response GetBalance(@PathParam("id") String account) {
        int balance = 10; //account.balance();

        String output = "Account balance retrieved. { ID = " + account + " ; Balance = " + balance + " }";
        return Response.status(200).entity(output).build();
    }

    /**
     * Load starting standard money value into account on creation.
     *      START_VAL - an integer with starting money value
     */
    private static final int START_MONEY = 10;
    private void loadmoney() {
        //account.add(START_MONEY);
    }

    /**
     * Send value from the origin contract to the destination contract.
     *
     * @param origin - id of account to send from
     * @param destination - id of account to send to
     * @param value - amount of money to send
     * @return Response with status and output message of operation.
     */
    @PUT
    @Path("transaction/{origin}/{destin}/{value}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response SendTransaction(@PathParam("origin") String origin, @PathParam("destin") String destination, @PathParam("value") String value) {
        //account.transaction(origin, destination, value);

        String output = "Transaction from account " + origin + " to " + destination + " with money value of " + value + ".";
        return Response.status(200).entity(output).build();
    }


    /** TODO: add other methods, authentication and validation
     *   GetExtract();
     *   GetTotalValue();
     *   GetGlobalLedgerValue();
     *   GetLedger();
     */

}
