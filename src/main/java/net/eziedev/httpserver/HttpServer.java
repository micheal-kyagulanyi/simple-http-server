package net.eziedev.httpserver;

import net.eziedev.httpserver.config.Configuration;
import net.eziedev.httpserver.config.ConfigurationManager;

/**
 *Driver class for the Http Server
 *
 */
public class HttpServer {

    public static void main( String[] args ) {

        System.out.println( "Server starting..." );

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Using port: " + configuration.getPort());
        System.out.println("Using WebRoot: " + configuration.getWebroot());

    }
}
