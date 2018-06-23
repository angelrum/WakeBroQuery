package system.controller.page.helper;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import system.model.Ticket;

import java.util.List;
import java.util.Set;

/**
 * Created by vladimir on 25.05.2018.
 */
public class FontAwesomeIconHelper {

    public static FontAwesomeIconView getIconView(FontAwesomeIcon awesomeIcon) {
        return getIconView(awesomeIcon, "1.5em");
    }

    public static FontAwesomeIconView getIconView(FontAwesomeIcon awesomeIcon, String size) {
        FontAwesomeIconView icon = new FontAwesomeIconView(awesomeIcon);
        icon.getStyleClass().add("icon");
        //icon.setSize(size);
        return icon;
    }

    public static <T extends Node> StackPane getPane(T t) {
        StackPane pane = new StackPane();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(t);
        return pane;
    }

    public static <T> StackPane getButtonDelete(T t, Set<T> list, TableView<T> view) {
        FontAwesomeIconView icon = FontAwesomeIconHelper.getIconView(FontAwesomeIcon.TRASH_ALT);
        icon.setOnMouseClicked(event -> {
            list.add(t);
            view.getItems().remove(t);
        });
        return getPane(icon);
    }
}
