package system.controller.page.helper.TableCell;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

import java.util.Set;

/**
 * Created by vladimir on 16.05.2018.
 */
public class TextFieldCell <S, T>  extends AbstractBaseTableCell<S, T> {
    private JFXTextField textField;
    // --- converter
    private ObjectProperty<StringConverter<T>> converter =
            new SimpleObjectProperty<StringConverter<T>>(this, "converter");

    public TextFieldCell(Set<S> upd, ObservableList<T> values, String field) {
        super(upd, values, field);
        setConverter((StringConverter<T>) new DefaultStringConverter());
    }

    public TextFieldCell(Set<S> upd, ObservableList<T> values, String field, StringConverter<T> converter) {
        super(upd, values, field);
        setConverter(converter);
    }


    @Override
    void createControlClass(ObservableList<T> values) {
        textField = new JFXTextField();
        //Изменение при нажатие клавиши Enter
        textField.setOnAction(event -> {
            saveChangedValue();
        });

        //Изменение при расфокусировки
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                setText(textField.getText());
            else
                saveChangedValue();
        });
    }

    private void saveChangedValue() {
        if (getText()==null ||
                !getText().equals(textField.getText())) {
            T t = getConverter().fromString(textField.getText());
            int index = getIndex();
            S s = getTableView().getItems().get(index);
            setValue(s, t);
            addUpdateItem(s);
            setText(getConverter().toString(t));
            commitEdit(t);
            textField.setText(getText()); //Для числовых значений, что бы на момент редактирования все было в одном формате 200.0
        }
    }

    private void setConverter(final StringConverter<T> value) {
        converterProperty().set(value);
    }

    public final ObjectProperty<StringConverter<T>> converterProperty() {
        return converter;
    }

    public final StringConverter<T> getConverter() {
        return converterProperty().get();
    }

    @Override
    public void commitEdit(T newValue) {
        super.commitEdit(newValue);
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (textField != null) {
            textField.setText(getConverter().toString(getItem()));
        }
        textField.selectAll();
        // requesting focus so that key input can immediately go into the
        // TextField (see RT-28132)
        textField.requestFocus();
    }

    @Override
    void insertValue(T item) {
        textField.setText(getConverter().toString(item));
        setGraphic(textField);
    }
}
