package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int AUHT_USER = 100000;

    public static final Meal meal= new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "actDesc",
            1000);
    public static final Meal meal1 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "завтрак", 500);
    public static final Meal meal2 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "обед", 1000);
    public static final Meal meal3 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "ужин", 500);
    public static final Meal meal4 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "завтрак", 1000);
    public static final Meal meal5 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "обед", 500);
    public static final Meal meal6 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "ужин", 510);


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "id");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("id").isEqualTo(expected);
    }
}
