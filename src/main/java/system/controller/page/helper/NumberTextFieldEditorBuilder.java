package system.controller.page.helper;

import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

/**
 * Created by vladimir on 24.05.2018.
 *
 * Для возможности редактирования по формату мобильного телефона
 */
public class NumberTextFieldEditorBuilder extends TextFieldEditorBuilder {

    private NumberTextField textField;

    @Override
    public Region createNode(String value, EventHandler<KeyEvent> keyEventsHandler, ChangeListener<Boolean> focusChangeListener) {
        super.createNode(value, keyEventsHandler, focusChangeListener);
        textField = new NumberTextField();
        this.textField.setOnKeyPressed(keyEventsHandler);
        this.textField.focusedProperty().addListener(focusChangeListener);
        setValue(value);
        return this.textField;
    }

    @Override
    public void setValue(String value) {
        this.textField.setText(value);
    }

    @Override
    public String getValue() {
        return this.textField.getText();
    }
}
