package net.eziedev.httpserver.config;

import com.fasterxml.jackson.databind.JsonNode;
import net.eziedev.httpserver.exceptions.HttpConfigurationException;
import net.eziedev.httpserver.util.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {

    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {}

    public static ConfigurationManager getInstance() {
        if (myConfigurationManager == null) {
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }

    /**
     * Used to load a configuration file by the path provided
     */
    public void loadConfigurationFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while((i = fileReader.read()) != -1) {
                sb.append((char)i);
            }
        } catch (IOException ioe) {
            throw new HttpConfigurationException(ioe);
        }

        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing configuration file", e);
        }

        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing configuration file, internal", e);
        }
    }

    /**
     * Returns the current loaded configuration
     */
    public Configuration getCurrentConfiguration() {
        if (myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No Current Configuration Set.");
        }
        return myCurrentConfiguration;
    }
}
