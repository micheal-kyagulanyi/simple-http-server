package net.eziedev.httpserver;

import net.eziedev.httpserver.config.Configuration;
import net.eziedev.httpserver.config.ConfigurationManager;
import net.eziedev.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *Driver class for the Http Server
 *
 */
public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main( String[] args ) {

        LOGGER.info( "Server starting..." );

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using port: {}", configuration.getPort());
        LOGGER.info("Using WebRoot: {}", configuration.getWebroot());

        try {
            ServerListenerThread serverListenerThread =
                    new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
