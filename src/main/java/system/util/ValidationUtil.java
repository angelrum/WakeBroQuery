package system.util;

import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import system.MainApp;
import system.model.AbstractBaseEntity;
import system.model.Ticket;
import system.service.exception.NotFoundException;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import static system.util.Helper.*;

/**
 * Created by vladimir on 20.03.2018.
 */
public class ValidationUtil {

    private static final InputStream TICKET_ERR = MainApp.class.getResourceAsStream("validation.properties");

    public static <T> List<T> checkNullOrEmptyList(List<T> list) throws NotFoundException {
        String msg = "List is null";
        checkNotFound(list!=null, msg);
        checkNotFound(!list.isEmpty(), msg);
        return list;
    }

    public static <T> T checkNotNull(T t) throws IllegalArgumentException {
        Assert.notNull(t, t.getClass().getSimpleName() + " must not be null");
        return t;
    }

    public static boolean checkIsNew(AbstractBaseEntity entity) {
        return entity.isNew();
    }

    public static <T> T checkNotFoundWithId(T t, int id) {
        checkNotFound(t,  "id=" + id);
        return t;
    }

    public static void checkNotFoundWithId(boolean flag, int id) {
        checkNotFound(flag, "id=" + id);
    }

    public static <T> T checkNotFound(T t, String msg) {
        checkNotFound(t!=null, "Not found entity with " + msg);
        return t;
    }

    public static void checkNotFound(boolean flag, String msg) {
        if (!flag)
            throw new NotFoundException(msg);
    }

    public static boolean checkTelNumber(String number) {
        //if (number.endsWith("_")) return false;
        String telnumber = number.replaceAll("[\\+|\\)|\\(|\\-|_]", "");
        if (StringUtils.hasLength(telnumber)
                && telnumber.length()==11)
            return true;
        return false;
    }

    public static <T> boolean indexExists(List<T> list, final int index) {
        int size = list.size();
        return index >= 0 && index < size;
    }

    public static String checkTicket(Ticket ticket, int pos) {
        StringBuilder builder = new StringBuilder();
        Properties err = getProperties(TICKET_ERR);
        checkNotNull(err);
        if (!StringUtils.hasText(ticket.getName()))
            insertErText(err, pos, "ticket.err.name", builder);
        if (Objects.isNull(ticket.getStartTime())
                || Objects.isNull(ticket.getEndTime()))
            insertErText(err, pos, "ticket.err.time.null", builder);
        else if (ticket.getStartTime().isAfter(ticket.getEndTime()))
            insertErText(err, pos, "ticket.err.time", builder);
        else if (ticket.getStartTime().compareTo(ticket.getEndTime())==0)
            insertErText(err, pos, "ticket.err.time_equals", builder);

        if (Objects.isNull(ticket.getStartDate()) && Objects.isNull(ticket.getEndDate())){
          //ignore
        } else if ((Objects.nonNull(ticket.getStartDate()) && Objects.isNull(ticket.getEndDate())
        || Objects.isNull(ticket.getStartDate()) && Objects.nonNull(ticket.getEndDate())))
            insertErText(err, pos, "ticket.err.duration_date", builder);
        else if (ticket.getStartDate().compareTo(ticket.getEndDate())==0)
            insertErText(err, pos, "ticket.err.date_equals", builder);
        else if (ticket.getStartDate().isAfter(ticket.getEndDate()))
            insertErText(err, pos, "ticket.err.date_bigger", builder);

        if (Objects.nonNull(ticket.getStartDate())
                && Objects.nonNull(ticket.getEndDate())
                && ticket.getMonth() > 0)
            insertErText(err, pos, "ticket.err.duration_date_month", builder);

        return builder.toString();
    }

    private static void insertErText(Properties err, int pos, String prop, StringBuilder builder) {
        builder.append(String.format(err.getProperty("ticket.err.format"), pos, err.getProperty(prop)));
    }

}
