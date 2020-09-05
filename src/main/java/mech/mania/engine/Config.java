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

    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
