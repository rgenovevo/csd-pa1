package server.replica;

import bftsmart.tom.MessageContext;
import bftsmart.tom.ServiceReplica;
import bftsmart.tom.server.defaultservices.DefaultSingleRecoverable;
import server.ServerRequestType;
import shared.Ledger;

import java.io.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerReplica extends DefaultSingleRecoverable {

    private Ledger ledger;
    private Logger logger;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: ServerReplica <server id>");
            System.exit(-1);
        }

        new ServerReplica(Integer.parseInt(args[0]));
    }

    public ServerReplica(int id) {
        ledger = new Ledger();
        logger = Logger.getLogger(ServerReplica.class.getName());

        // create and start a new instance of bft-smart server replica
        // access to un/ordered and snapshot override methods
        new ServiceReplica(id, this, this);
    }

    @Override
    public void installSnapshot(byte[] bytes) {
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
             ObjectInput objIn = new ObjectInputStream(byteIn)) {
            ledger = (Ledger)objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error while installing snapshot", e);
        }
    }

    @Override
    public byte[] getSnapshot() {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {
            objOut.writeObject(ledger);
            objOut.flush();
            return byteOut.toByteArray();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while taking snapshot", e);
        }
        return new byte[0];
    }

    @Override
    public byte[] appExecuteOrdered(byte[] bytes, MessageContext messageContext) {
        byte[] reply = null;
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
             ObjectInput objIn = new ObjectInputStream(byteIn);
             ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {
            ServerRequestType reqType = (ServerRequestType)objIn.readObject();

            String print;
            String account;
            int value;

            switch (reqType) {
                case CREATE:
                    account = (String)objIn.readObject();
                    value = objIn.readInt();

                    objOut.writeBoolean(true);
                    objOut.writeBoolean(ledger.createAccount(account, value));

                    break;
                case BALANCE:
                    account = (String)objIn.readObject();

                    value = ledger.getBalance(account);

                    objOut.writeBoolean(true);
                    objOut.writeInt(value);

                    break;
                case TRANSACTION:
                    String origin = (String)objIn.readObject();
                    String destination = (String)objIn.readObject();
                    value = objIn.readInt();

                    objOut.writeBoolean(true);
                    objOut.writeBoolean(ledger.addBlock(origin, destination, value));

                    break;
                case LEDGER:
                    print = ledger.getLedger();

                    objOut.writeBoolean(true);
                    objOut.writeObject(print);

                    break;
                case GLOBAL:
                    value = ledger.getGlobalLedgerValue();

                    objOut.writeBoolean(true);
                    objOut.writeInt(value);

                    break;
                case TOTAL:
                    String[] accounts = (String[])objIn.readObject();

                    value = ledger.getTotalValue(accounts);

                    objOut.writeBoolean(true);
                    objOut.writeInt(value);

                    break;
                case EXTRACT:
                    account = (String)objIn.readObject();

                    print = ledger.getExtract(account);

                    objOut.writeBoolean(true);
                    objOut.writeObject(print);

                    break;
                default:
                    objOut.writeBoolean(false);
            }

            objOut.flush();
            //byteOut.flush();
            reply = byteOut.toByteArray();


        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Ocurred during ledger operation execution", e);
        }
        return reply;
    }

    @Override
    public byte[] appExecuteUnordered(byte[] bytes, MessageContext messageContext) {
        byte[] reply = null;
        try (ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
             ObjectInput objIn = new ObjectInputStream(byteIn);
             ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut)) {
            ServerRequestType reqType = (ServerRequestType)objIn.readObject();
            String account;
            int value;
            switch (reqType) {
                case BALANCE:
                    account = (String)objIn.readObject();

                    value = ledger.getBalance(account);

                    objOut.writeBoolean(true);
                    objOut.writeInt(value);

                    break;
                case LEDGER:
                    String print = ledger.getLedger();

                    objOut.writeBoolean(true);
                    objOut.writeObject(print);

                    break;
                case GLOBAL:
                    value = ledger.getGlobalLedgerValue();

                    objOut.writeBoolean(true);
                    objOut.writeInt(value);

                    break;
                case TOTAL:
                    String[] accounts = (String[])objIn.readObject();

                    value = ledger.getTotalValue(accounts);

                    objOut.writeBoolean(true);
                    objOut.writeInt(value);

                    break;
                case EXTRACT:
                    account = (String)objIn.readObject();

                    print = ledger.getExtract(account);

                    objOut.writeBoolean(true);
                    objOut.writeObject(print);

                    break;
                default:
                    objOut.writeBoolean(false);
            }

            objOut.flush();
            //byteOut.flush();
            reply = byteOut.toByteArray();


        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Ocurred during ledger operation execution", e);
        }
        return reply;
    }
}
