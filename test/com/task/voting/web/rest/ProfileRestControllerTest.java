package com.task.voting.web.rest;

import com.task.voting.TestUtil;
import com.task.voting.model.Role;
import com.task.voting.model.User;
import com.task.voting.service.UserService;
import com.task.voting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static com.task.voting.TestData.*;
import static com.task.voting.TestUtil.userHttpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class ProfileRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = ProfileRestController.REST_URL + '/';

    @Autowired
    private UserService service;

    @Test
    public void testGet() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentMatcher(USER2)));
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isOk());

        USER_MATCHER.assertCollectionEquals(
                Arrays.asList(USER1, USER3, USER4, USER5),
                service.getAll()
        );
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        User u = new User(null, "Natallia Bychkova", "newPassword", Role.ROLE_USER);

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER2))
                .content(JsonUtil.writeValue(u)))
                .andDo(print())
                .andExpect(status().isOk());

        u.setId(USER2.getId());

        USER_MATCHER.assertEquals(u, service.getByName("Natallia Bychkova"));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        User u = new User(null, null, null, Role.ROLE_USER);

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER2))
                .content(JsonUtil.writeValue(u)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testDuplicate() throws Exception {
        User u = new User(null, "Vera", "newPassword", Role.ROLE_USER);

        mockMvc.perform(put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER2))
                .content(JsonUtil.writeValue(u)))
                .andExpect(status().isConflict());
    }
}