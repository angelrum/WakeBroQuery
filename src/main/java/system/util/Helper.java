package system.util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

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
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

    //Плавное появление
    public static <T extends Node> void opacityPropertyWithAnimation(T t, Integer duration) {
        int startValue = 0;
        int endValue = 1;

        if (t.opacityProperty().getValue()==1) {
            startValue = 1;
            endValue = 0;
        }

        KeyValue keyValue1 = new KeyValue(t.opacityProperty(), startValue);
        KeyValue keyValue2 = new KeyValue(t.opacityProperty(), endValue);

        KeyFrame frame1 = new KeyFrame(Duration.ZERO, keyValue1);
        KeyFrame frame2 = new KeyFrame(Duration.millis(duration), keyValue2);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(frame1, frame2);
        timeline.play();
    }
}
