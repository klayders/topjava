package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Test
    void getGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(SecurityUtil.authUserId()), Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2));
    }
    @Test
    void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1)));
    }
    @Test
    void testUpdate() throws Exception{
        Meal updated = getUpdated();
        updated.setDescription("YXA");
        mockMvc.perform(put(REST_URL + MEAL1_ID).
                contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
        assertMatch(mealService.get(MEAL1_ID, SecurityUtil.authUserId()), updated);
    }
    @Test
    void testCreated() throws Exception{
        Meal created = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)))
                .andExpect(status().isCreated());
        Meal returned = TestUtil.readFromJson(action, Meal.class);
        created.setId(returned.getId());
        assertMatch(returned, created);
        assertMatch(mealService.getAll(SecurityUtil.authUserId()), Arrays.asList(created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }
    @Test
    void testGetBetween() throws Exception{
        ResultActions resultActions = mockMvc.perform(get(REST_URL + "between?startDateTime=2015-05-30T00:00&endDateTime=2015-05-31T20:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));

    }
    @Test
    void testGetBetweenDates() throws Exception{
        ResultActions resultActions = mockMvc.perform(get(REST_URL + "filter?startDate=2015-05-30&startTime=01:00&endDate=2015-05-31&endTime=20:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }
}