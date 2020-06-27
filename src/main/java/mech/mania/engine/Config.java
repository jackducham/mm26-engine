package mech.mania.engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    {
        try {
            // config.properties is located in src/resources/config.properties
            String configFileName = "config.properties";
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getHost() {
        return properties.getProperty("host");
    }

    public static String getAwsHostAndPort() {
        // TODO: fill this out
        String host = "";
        String port = "";
        return String.format("https://%s:%s", host, port);
    }

    public static int getMillisBetweenTurns() {
        return properties.getProperty("millisBetweenTurns");
    }
}
