package system.controller.page.ClientEditPage;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.springframework.util.StringUtils;
import system.controller.SpringContextUtil;
import system.controller.page.helper.NumberTextFieldEditorBuilder;
import system.controller.page.listener.ActiveListener;
import system.controller.page.listener.Command;
import system.controller.page.listener.Controller;
import system.model.Client;
import system.service.ClientService;
import system.view.FactoryPage;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import static system.util.ValidationUtil.*;
import static system.controller.page.helper.FontAwesomeIconHelper.*;
/**
 * Created by vladimir on 23.05.2018.
 */
public class ClientEditPageController implements Controller {

    private ClientService service = SpringContextUtil.getInstance().getBean(ClientService.class);

    @FXML protected JFXTreeTableView<ClientTreeObject> tableView;
    @FXML protected JFXTreeTableColumn<ClientTreeObject, String> fname;
    @FXML protected JFXTreeTableColumn<ClientTreeObject, String> lname;
    @FXML protected JFXTreeTableColumn<ClientTreeObject, String> sname;
    @FXML protected JFXTreeTableColumn<ClientTreeObject, String> telnumber;
    @FXML protected JFXTreeTableColumn<ClientTreeObject, String> city;
    @FXML protected JFXTreeTableColumn<ClientTreeObject, String> dateCreate;
    @FXML protected JFXTreeTableColumn<ClientTreeObject, String> userCreate;
    @FXML protected JFXTreeTableColumn<ClientTreeObject, StackPane> delicon;
    @FXML protected JFXTextField search;

    private Set<ClientTreeObject> buffer = new HashSet<>();

    private Set<Client> delete = new HashSet<>();

    private Stage stage;

    @FXML public void initialize() {
        initTableView();
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<ClientTreeObject, T> column, Function<ClientTreeObject, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClientTreeObject, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
    }

    private List<ClientTreeObject> getTreeObjectList() {
        List<Client> clients = service.getAll();
        return clients
                .stream()
                .map(ClientTreeObject::new)
                .collect(Collectors.toList());
    }

    private ClientTreeObject getEditClient(TreeTableColumn.CellEditEvent<ClientTreeObject, String> event) {
        return event.getTreeTableView()
                .getTreeItem(event.getTreeTablePosition()
                        .getRow()).getValue();
    }

    private void initTableView() {
        //Отображение
        setupCellValueFactory(fname,        ClientTreeObject::fnameProperty);
        setupCellValueFactory(lname,        ClientTreeObject::lnameProperty);
        setupCellValueFactory(sname,        ClientTreeObject::snameProperty);
        setupCellValueFactory(telnumber,    ClientTreeObject::telnumberProperty);
        setupCellValueFactory(city,         ClientTreeObject::cityProperty);
        setupCellValueFactory(dateCreate,   ClientTreeObject::dateProperty);
        setupCellValueFactory(userCreate,   ClientTreeObject::userProperty);
        setupCellValueFactory(delicon,      ClientTreeObject::getDeleteProperty);

        //Редактирование полей
        fname.setCellFactory(param ->
                new GenericEditableTreeTableCell<ClientTreeObject, String>(new TextFieldEditorBuilder()));
        lname.setCellFactory(param ->
                new GenericEditableTreeTableCell<ClientTreeObject, String>(new TextFieldEditorBuilder()));
        sname.setCellFactory(param ->
                new GenericEditableTreeTableCell<ClientTreeObject, String>(new TextFieldEditorBuilder()));
        city.setCellFactory(param ->
                new GenericEditableTreeTableCell<ClientTreeObject, String>(new TextFieldEditorBuilder()));
        telnumber.setCellFactory(param ->
                new GenericEditableTreeTableCell<ClientTreeObject, String>(new NumberTextFieldEditorBuilder()));

        fname.setOnEditCommit(event -> {
            ClientTreeObject client = getEditClient(event);
            client.getClient().setFirstname(event.getNewValue());
            buffer.add(client);
        });

        lname.setOnEditCommit(event -> {
            ClientTreeObject client = getEditClient(event);
            client.getClient().setLastname(event.getNewValue());
            buffer.add(client);
        });

        sname.setOnEditCommit(event -> {
            ClientTreeObject client = getEditClient(event);
            client.getClient().setSecondname(event.getNewValue());
            buffer.add(client);
        });

        telnumber.setOnEditCommit(event -> {
            ClientTreeObject client = getEditClient(event);
            client.getClient().setTelnumber(event.getNewValue());
            buffer.add(client);
        });

        city.setOnEditCommit(event -> {
            ClientTreeObject client = getEditClient(event);
            client.getClient().setCity(event.getNewValue());
            buffer.add(client);
        });

        ObservableList<ClientTreeObject> tableList = FXCollections.observableList(getTreeObjectList());
        tableView.setRoot(new RecursiveTreeItem<ClientTreeObject>(tableList, RecursiveTreeObject::getChildren));
        tableView.setShowRoot(false);

        search.textProperty().addListener((o, oldValue, newValue) -> {
            tableView.setPredicate(clientTreeObjectTreeItem -> {
                final Client client = clientTreeObjectTreeItem.getValue().getClient();
                return client.getFirstname().contains(newValue)
                        || client.getLastname().contains(newValue)
                        || client.getSecondname().contains(newValue)
                        || client.getCity().contains(newValue)
                        || client.getCreate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).contains(newValue)
                        || client.getCreate().format(DateTimeFormatter.ofPattern("ddMMyyyy")).contains(newValue)
                        || client.getUser().getLogin().contains(newValue)
                        || client.getTelnumber().contains(newValue)
                        || client.getTelnumber().replaceAll("[\\+|\\)|\\(|\\-]", "").contains(newValue);
            });
        });
    }

    @FXML public void clickOk() {
        String err = checkBufferByError();
        if (StringUtils.hasText(err))
            FactoryPage.getInstance().showAlert(err, stage);
        else {
            buffer.forEach(cto-> service.save(cto.getClient()));
            delete.forEach(cto->{
                if (!cto.isNew()) service.delete(cto.getId());
            });
            stage.close();
        }
    }

    @FXML public void clickCancel() {
        stage.close();
    }

    @FXML public void clickCreate() {
        TreeItem<ClientTreeObject> newItem = new TreeItem<>(new ClientTreeObject(new Client()));
        tableView.getRoot().getChildren().add(newItem);
    }

    private String checkBufferByError() {
        List<ClientTreeObject> list = tableView.getRoot().getChildren()
                .stream().map(TreeItem::getValue)
                .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        buffer.forEach(cto -> builder.append(checkClient(cto.getClient(), list.indexOf(cto))));
        return builder.toString();
    }

    private void removeElement(ClientTreeObject cto) {
        TreeItem<ClientTreeObject> treeItem = tableView.getRoot().getChildren()
                .stream()
                .filter(item -> item.getValue().equals(cto))
                .findFirst()
                .orElse(null);
        if (Objects.nonNull(treeItem)) {
            tableView.getRoot().getChildren().remove(treeItem);
            delete.add(treeItem.getValue().getClient());
        }
        buffer.remove(cto);
    }

     final class ClientTreeObject extends RecursiveTreeObject<ClientTreeObject> {
        final Client client;

        ClientTreeObject(Client client) {
            this.client = client;
        }

        StringProperty fnameProperty() {
            return new SimpleStringProperty(client.getFirstname());
        }

        StringProperty lnameProperty() {
            return new SimpleStringProperty(client.getLastname());
        }

        StringProperty snameProperty() {
            return new SimpleStringProperty(client.getSecondname());
        }

        StringProperty telnumberProperty() {
            return new SimpleStringProperty(client.getTelnumber());
        }

        StringProperty cityProperty() {
            return new SimpleStringProperty(client.getCity());
        }

        StringProperty dateProperty() {
            return new SimpleStringProperty(client.getCreate().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        }

        StringProperty userProperty() {
            if (Objects.nonNull(client.getUser()))
                return new SimpleStringProperty(client.getUser().getLogin());
            else
                return new SimpleStringProperty();
        }

        SimpleObjectProperty getDeleteProperty() {
            FontAwesomeIconView icon = getIconView(FontAwesomeIcon.TRASH_ALT);
            icon.setOnMouseClicked(event -> removeElement(this));
            return new SimpleObjectProperty(getPane(icon));
        }

        public Client getClient() {
            return client;
        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setListener(ActiveListener listener) {

    }

    @Override
    public void setActiveClient(Client client) {

    }

    @Override
    public void execute(Command command) {

    }
}
