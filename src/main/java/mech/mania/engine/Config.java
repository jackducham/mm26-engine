package mech.mania.engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    static {
        try {
            // config.properties is located in src/resources/config.properties
            String configFileName = "config.properties";
            InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(configFileName);
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + configFileName + "' not found in the classpath");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static String getHost() {
//        return properties.getProperty("host");
//    }

//    public static String getAwsHostAndPort() {
//        // TODO: fill this out
//        String host = "";
//        String port = "";
//        return String.format("https://%s:%s", host, port);
//    }

    public static int getMillisBetweenTurns() {
        return Integer.parseInt(properties.getProperty("millisBetweenTurns"));
    }

    public static String getInfraPort() {
        return properties.getProperty("infraPort");
    }

    public static String getVisualizerPort() {
        return properties.getProperty("visualizerPort");
    }

    public static int getNumTurns() {
        return Integer.parseInt(properties.getProperty("numTurns"));
    }
}
