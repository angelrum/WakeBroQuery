package system.controller.page.helper;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * Created by vladimir on 25.05.2018.
 */
public class FontAwesomeIconHelper {

    public static FontAwesomeIconView getIconView(FontAwesomeIcon awesomeIcon) {
        FontAwesomeIconView icon = new FontAwesomeIconView(awesomeIcon);
        icon.setStyleClass("icon");
        return icon;
    }

    public static <T extends Node> StackPane getPane(T t) {
        StackPane pane = new StackPane();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(t);
        return pane;
    }
}
