package com.task.voting.web.rest;

import com.task.voting.model.CafeMenu;
import com.task.voting.service.CafeMenuService;
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
public class CafeMenuRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = CafeMenuRestController.REST_URL + '/';

    @Autowired
    private CafeMenuService service;

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentListMatcher(MENUS));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MENU_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentMatcher(CAFE_MENU1));
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        CafeMenu updated = getUpdatedMenu();

        mockMvc.perform(put(REST_URL + MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk());

        assertEquals(updated, service.get(MENU_ID));
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        CafeMenu created = getCreatedMenu();
        ResultActions action = mockMvc.perform(post(REST_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(created))
                        .with(userHttpBasic(USER1))
        );

        CafeMenu returned = MENU_MATCHER.fromJsonAction(action);
        created.setId(returned.getId());

        MENU_MATCHER.assertEquals(created, returned);
        MENU_MATCHER.assertCollectionEquals(
                Arrays.asList(CAFE_MENU5, CAFE_MENU3, CAFE_MENU2, CAFE_MENU1, CAFE_MENU4, CAFE_MENU6, created),
                service.getAll(null, null)
        );
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MENU_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk());
        MENU_MATCHER.assertCollectionEquals(
                Arrays.asList(CAFE_MENU5, CAFE_MENU3, CAFE_MENU2, CAFE_MENU4, CAFE_MENU6),
                service.getAll(null, null)
        );
    }
}