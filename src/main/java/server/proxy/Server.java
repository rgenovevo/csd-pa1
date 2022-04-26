package server.proxy;

import jakarta.ws.rs.core.Application;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorConfigLoader;
import server.replica.ServerReplica;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.security.KeyStore;
import java.security.KeyStoreException;

/**
 * Main class.
 *
 */
public class Server {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "https://localhost:8080/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer(String uri) throws Exception {
        // create a resource config that scans for JAX-RS resources and providers
        // in grizzly package
        final ResourceConfig rc = new ResourceConfig().packages("server.proxy");

        final String certs = System.getProperty("user.dir") + "/certs/server";

        // SSL context configurator
        final SSLContext sslContext = SslConfigurator.newInstance()
                .securityProtocol("TLSv1.2")
                .trustStoreFile(certs + "/server.truststore")
                .trustStorePassword("qwerty")
                .keyStoreFile(certs + "/server.keystore")
                .keyStorePassword("qwerty")
                .createSSLContext();

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(uri), rc, true, new SSLEngineConfigurator(sslContext).setClientMode(false));
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Usage: Server <server host> <server port>");
        String uri;
        if(args.length < 2)
            uri = BASE_URI;
        else
            uri = "https://" + args[0] + ":" + args[1] + "/";

        final HttpServer server = startServer(uri);
        System.out.println("Jersey app started with endpoints available at " + uri
                + "\nHit Ctrl-C to stop it...");
        System.in.read();
        server.stop();
    }
}

