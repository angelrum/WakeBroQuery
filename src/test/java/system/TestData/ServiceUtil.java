package system.TestData;

import system.model.Role;
import system.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by vladimir on 30.04.2018.
 */
public class ServiceUtil {

    public static <T> void assertMatchIgnorFields(T actual, T expected, String...fields) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, fields);
    }

    public static <T> void assertMatchIgnorFields(Iterable<T> actual, List<T> expected, String...fields) {
        assertThat(actual).usingElementComparatorIgnoringFields(fields).isEqualTo(expected);
    }

    public static <T> void assertMatch(Iterable<T> actual, T...roles) {
        Set<T> roleSet = new HashSet<>(Arrays.asList(roles));
        assertThat(actual).isEqualTo(roleSet);
    }
}
