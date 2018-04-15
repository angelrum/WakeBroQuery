package system.util;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import system.model.AbstractBaseEntity;
import system.service.exception.NotFoundException;

import java.util.List;

/**
 * Created by vladimir on 20.03.2018.
 */
public class ValidationUtil {

    public static <T> List<T> checkNullOrEmptyList(List<T> list) throws NotFoundException {
        String msg = "List is null";
        checkNotFound(list!=null, msg);
        checkNotFound(!list.isEmpty(), msg);
        return list;
    }

    public static <T> T checkNotNull(T t) {
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


}
