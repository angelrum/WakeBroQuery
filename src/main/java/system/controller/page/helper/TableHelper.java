package system.controller.page.helper;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.util.StringUtils;
import system.model.AbstractBaseEntity;
import system.service.AbstractService;
import system.view.FactoryPage;

import java.util.Set;

public class TableHelper {

    public static <S extends AbstractBaseEntity, T> S getEditObject(TableColumn.CellEditEvent<S, T> event, Set<S> buffer) {
        TablePosition<S, T> pos = event.getTablePosition();
        S s = pos.getTableView().getItems().get(pos.getRow());
        buffer.add(s);
        return s;
    }

    private static <S extends AbstractBaseEntity> void save(Set<S> update, Set<S> delete, AbstractService<S> service)
                                                                                                throws IllegalArgumentException {
        for (S s : update) service.save(s);
        for (S s : delete) {
            if (!s.isNew()) service.delete(s.getId());
        }
    }

    public static <S> void createObject(TableView<S> tableView, S s) {
        tableView.getItems().add(s);
        tableView.refresh();
    }

    public static <S extends AbstractBaseEntity> void saveObjects(Set<S> update, Set<S> delete, AbstractService<S> service, Stage stage, String err) {
        if (StringUtils.hasLength(err)) {
            FactoryPage.getInstance().showAlert(err, stage);
        } else {
            try {
                save(update, delete, service);
                stage.close();
            } catch (IllegalArgumentException e) {
                FactoryPage.getInstance().showAlert("Ошибка при сохранение данных", stage);
            }
        }
    }
}
