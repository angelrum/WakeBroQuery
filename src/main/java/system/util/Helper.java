package system.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by vladimir on 06.05.2018.
 */
public class Helper {
    private static Properties properties;

    public static Properties getProperties(InputStream input) {
        if (properties==null) {
            try {
                properties = new Properties();
                properties.load(new InputStreamReader(input, "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }
}
