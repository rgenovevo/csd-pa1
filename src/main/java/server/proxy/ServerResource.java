package server.proxy;

import bftsmart.tom.ServiceProxy;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import server.ServerRequestType;

import java.io.*;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Root resource (exposed at "/" path)
 */
@Path("/")
@Produces(MediaType.TEXT_PLAIN)
@Singleton
public class ServerResource {
    private ServiceProxy serviceProxy;

    public ServerResource() {
        serviceProxy = new ServiceProxy(new SecureRandom().nextInt());
    }

    /**
     * Load starting standard money value into account on creation.
     *      LOAD_MONEY - an integer with starting money value
     */
    private static final int LOAD_MONEY = 10;

    /**
     * Operation to create a new account with respective id.
     *
     * @param account - String with id of the account to create.
     * @return Response with status and output message of operation.
     */
    @POST
    @Path("account/{id}")
    public Response CreateAccount(@PathParam("id") String account) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {

            objOut.writeObject(ServerRequestType.CREATE);
            objOut.writeObject(account);
            objOut.writeInt(LOAD_MONEY);

            objOut.flush();
            //byteOut.flush();

            byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());

            Response response = getResponse(new String[] {account}, ServerRequestType.CREATE, reply);
            if(response == null)
                throw new IOException();

            return response;

        } catch (IOException e) {
            System.out.println("Exception creating account: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtains the current balance of the account in argument, with authentication and integrity attestation proofs.
     *
     * @param account - id of account to get balance
     * @return Response with status and balance as a string output.
     */
    @GET
    @Path("account/{id}")
    public Response GetBalance(@PathParam("id") String account) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {

            objOut.writeObject(ServerRequestType.BALANCE);
            objOut.writeObject(account);

            objOut.flush();
            //byteOut.flush();

            byte[] reply = serviceProxy.invokeUnordered(byteOut.toByteArray());

            Response response = getResponse(new String[] {account}, ServerRequestType.BALANCE, reply);
            if(response == null)
                throw new IOException();

            return response;

        } catch (IOException e) {
            System.out.println("Exception getting balance: " + e.getMessage());
        }
        return null;
    }

    /**
     * Send value from the origin contract to the destination contract.
     *
     * @param origin - id of account to send from
     * @param destination - id of account to send to
     * @param money - amount of money to send
     * @return Response with status and output message of operation.
     */
    @PUT
    @Path("transaction/{origin}/{destin}/{money}")
    public Response SendTransaction(@PathParam("origin") String origin, @PathParam("destin") String destination, @PathParam("money") String money) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {

            objOut.writeObject(ServerRequestType.TRANSACTION);
            objOut.writeObject(origin);
            objOut.writeObject(destination);
            objOut.writeInt(Integer.parseInt(money));

            objOut.flush();
            //byteOut.flush();

            byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());

            Response response = getResponse(new String[] {origin, destination, money}, ServerRequestType.TRANSACTION, reply);
            if(response == null)
                throw new IOException();

            return response;

        } catch (IOException e) {
            System.out.println("Exception on transaction: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtain the current ledger.
     *
     * @return Response with status and ledger as a string output.
     */
    @GET
    @Path("ledger")
    public Response GetLedger() {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {

            objOut.writeObject(ServerRequestType.LEDGER);

            objOut.flush();
            //byteOut.flush();

            byte[] reply = serviceProxy.invokeUnordered(byteOut.toByteArray());

            Response response = getResponse(null, ServerRequestType.LEDGER, reply);
            if(response == null)
                throw new IOException();

            return response;

        } catch (IOException e) {
            System.out.println("Exception getting ledger: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtains the total amount of value registered in the ledger, corresponding to the total amount of values of all current accounts.
     *
     * @return Response with status and global ledger value as a string output.
     */
    @GET
    @Path("global")
    public Response GetGlobalLedgerValue() {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {

            objOut.writeObject(ServerRequestType.GLOBAL);

            objOut.flush();
            //byteOut.flush();

            byte[] reply = serviceProxy.invokeUnordered(byteOut.toByteArray());

            Response response = getResponse(null, ServerRequestType.GLOBAL, reply);
            if(response == null)
                throw new IOException();

            return response;

        } catch (IOException e) {
            System.out.println("Exception getting global ledger value: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtains the total amount of value given a set of accounts in the argument
     *
     * @param ids - string with the ids to get total value
     * @return Response with status and total value as a string output.
     */
    @GET
    @Path("total/{ids}")
    public Response GetTotalValue(@PathParam("ids") String ids) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {

            String[] idsarray = ids.split("-");

            objOut.writeObject(ServerRequestType.TOTAL);
            objOut.writeObject(idsarray);

            objOut.flush();
            //byteOut.flush();

            byte[] reply = serviceProxy.invokeUnordered(byteOut.toByteArray());

            Response response = getResponse(idsarray, ServerRequestType.TOTAL, reply);
            if(response == null)
                throw new IOException();

            return response;

        } catch (IOException e) {
            System.out.println("Exception getting total value: " + e.getMessage());
        }
        return null;
    }

    /**
     * Obtains current extract of an account
     *
     * @param id - id of the account to get extract
     * @return Response with status and account extract as a string output.
     */
    @GET
    @Path("extract/{id}")
    public Response GetExtract(@PathParam("id") String id) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {

            objOut.writeObject(ServerRequestType.EXTRACT);
            objOut.writeObject(id);

            objOut.flush();
            //byteOut.flush();

            byte[] reply = serviceProxy.invokeUnordered(byteOut.toByteArray());

            Response response = getResponse(null, ServerRequestType.EXTRACT, reply);
            if(response == null)
                throw new IOException();

            return response;

        } catch (IOException e) {
            System.out.println("Exception getting ledger: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get response for request type
     *
     * @param arg - arguments to pass for output
     * @param rtype - type of server request
     * @param reply - byte array with the reply
     * @return
     */
    private Response getResponse(String[] arg, ServerRequestType rtype, byte[] reply) {
        if (reply == null)
            return Response.serverError().build();
        else {
            if (reply.length == 0)
                return Response.status(Response.Status.NO_CONTENT)
                        .entity("")
                        .build();

            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
                 ObjectInput objIn = new ObjectInputStream(byteIn)) {

                if(!objIn.readBoolean())
                    return Response.status(Response.Status.NOT_IMPLEMENTED)
                            .entity("Method not implemented.")
                            .build();

                String output;
                int value;

                switch(rtype) {
                    case CREATE:
                        if (objIn.readBoolean())
                            return Response.status(Response.Status.CREATED)
                                    .entity("New account created. {ID = " + arg[0] + "}")
                                    .build();
                        else
                            return Response.status(Response.Status.CONFLICT)
                                    .entity("Account already exists.")
                                    .build();
                    case BALANCE:
                        value = objIn.readInt();
                        if(value >= 0)
                            return Response.status(Response.Status.FOUND)
                                    .entity("Account balance retrieved. { ID = " + arg[0] + " ; Balance = " + value + " }")
                                    .build();
                        else
                            return Response.status(Response.Status.NOT_FOUND)
                                    .entity("Account doesn't exists.")
                                    .build();
                    case TRANSACTION:
                        if(objIn.readBoolean())
                            return Response.status(Response.Status.ACCEPTED)
                                    .entity("Transaction from account " + arg[0] + " to " + arg[1] + " with money value of " + arg[2] + ".")
                                    .build();
                        else
                            return Response.status(Response.Status.NOT_ACCEPTABLE)
                                    .entity("Account (origin or destination) doesn't exists or origin doesn't have balance.")
                                    .build();
                    case LEDGER:
                        output = (String)objIn.readObject();
                        if(output.isEmpty())
                            output = " Empty...";

                        return Response.status(Response.Status.FOUND)
                                .entity(output)
                                .build();
                    case GLOBAL:
                        value = objIn.readInt();
                        return Response.status(Response.Status.FOUND)
                                .entity("Global ledger value: " + value)
                                .build();
                    case TOTAL:
                        value = objIn.readInt();
                        return Response.status(Response.Status.FOUND)
                                .entity("Total value of the accounts {IDs = " + Arrays.toString(arg) + "}: " + value)
                                .build();
                    case EXTRACT:
                        output = (String)objIn.readObject();
                        if(output.equals("NULL"))
                            return Response.status(Response.Status.NOT_FOUND)
                                    .entity("Account doesn't exists.")
                                    .build();
                        else
                            return Response.status(Response.Status.FOUND)
                                    .entity(output)
                                    .build();
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void close() {
        serviceProxy.close();
    }

}
