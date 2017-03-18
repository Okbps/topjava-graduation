package com.task.voting.web.rest;

import com.task.voting.TestData;
import com.task.voting.TestUtil;
import com.task.voting.model.Cafe;
import com.task.voting.service.CafeService;
import com.task.voting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.task.voting.TestData.*;
import static com.task.voting.TestUtil.userHttpBasic;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Aspire on 05.03.2017.
 */
@SuppressWarnings("ALL")
public class CafeRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = CafeRestController.REST_URL + '/';

    @Autowired
    private CafeService service;

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(CAFE_MATCHER.contentListMatcher(CAFES));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + CAFE1_ID)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(CAFE_MATCHER.contentMatcher(TestData.CAFE1));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        Cafe updated = getUpdatedCafe();

        mockMvc.perform(put(REST_URL + CAFE1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk());

        assertEquals(updated, service.get(CAFE1_ID));
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        Cafe created = getCreatedCafe();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created))
                .with(userHttpBasic(USER1))
        );

        Cafe returned = CAFE_MATCHER.fromJsonAction(action);
        created.setId(returned.getId());

        CAFE_MATCHER.assertEquals(created, returned);
        CAFE_MATCHER.assertCollectionEquals(Arrays.asList(CAFE3, created, CAFE1, CAFE2), service.getAll());
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + CAFE1_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk());
        CAFE_MATCHER.assertCollectionEquals(Arrays.asList(CAFE3, CAFE2), service.getAll());
    }
}