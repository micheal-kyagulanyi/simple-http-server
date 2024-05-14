package net.eziedev.httpserver;

import net.eziedev.httpserver.config.Configuration;
import net.eziedev.httpserver.config.ConfigurationManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

        try {
            ServerSocket serverSocket = new ServerSocket(configuration.getPort());
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String html = "<html><head><title>Simple Java HTTP Server</title></head><body><h1>This page was served by Simple Java HTTP Server</h1></body></html>";
            final String CRLF = "\n\r";
            String response = "HTTP/1.1 200 OK" + CRLF +
                    "Content-Length: " + html.getBytes().length + CRLF + CRLF +
                    html + CRLF + CRLF;

            outputStream.write(response.getBytes());


            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
