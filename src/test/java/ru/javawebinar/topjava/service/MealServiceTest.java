package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(100004, 100000);
        assertMatch(meal, meal3);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        service.delete(1, 1);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> betweenDates = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 29), LocalDate.of(2015, Month.MAY, 30), AUHT_USER);
        assertMatch(betweenDates, meal3, meal2, meal1);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> betweenDateTimes = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
                LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
                AUHT_USER);
        assertMatch(betweenDateTimes, meal1);
    }

    @Test
    public void getAll() {
        List<Meal> mealList = service.getAll(AUHT_USER);
        assertMatch(mealList, meal3, meal2, meal1);
    }

    @Test
    public void update() {
        Integer id = 100004;
        Meal updateMeal = new Meal(service.get(id, AUHT_USER));
        updateMeal.setDescription("kyky");
        service.update(updateMeal, AUHT_USER);
        assertMatch(service.get(id, AUHT_USER), updateMeal);
    }

    @Test
    public void create() {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "actDesc", 1000);
        Meal create = service.create(meal, 100000);
        assertMatch(MealTestData.meal, meal);
    }
}