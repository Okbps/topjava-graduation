package com.task.voting.web.rest;

import com.task.voting.TestUtil;
import com.task.voting.model.Role;
import com.task.voting.model.User;
import com.task.voting.service.UserService;
import com.task.voting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

import static com.task.voting.TestData.*;
import static com.task.voting.TestUtil.userHttpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    @Autowired
    private UserService service;


    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + USER_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentMatcher(USER1));
    }

    @Test
    public void testGetNotFound() throws Exception {
        mockMvc.perform(get(REST_URL + 1)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetByEmail() throws Exception {
        mockMvc.perform(get(REST_URL + "by?name=" + USER1.getName())
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentMatcher(USER1));
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + USER2.getId())
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isOk());

        USER_MATCHER.assertCollectionEquals(
                Arrays.asList(USER1, USER3, USER4, USER5),
                service.getAll()
        );
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        mockMvc.perform(delete(REST_URL + 1)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetForbidden() throws Exception {
        mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER2)))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        User updated = new User(USER2);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        mockMvc.perform(put(REST_URL + USER2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        USER_MATCHER.assertEquals(updated, service.get(USER2.getId()));
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        User updated = new User(USER2);
        updated.setName("");
        mockMvc.perform(put(REST_URL + USER2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        User expected = new User(null, "New", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        User returned = USER_MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        USER_MATCHER.assertEquals(expected, returned);
        USER_MATCHER.assertCollectionEquals(
                Arrays.asList(USER1, USER3, USER4, USER2, expected, USER5),
                service.getAll()
        );
    }

    @Test
    public void testCreateInvalid() throws Exception {
        User expected = new User(null, null, "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentListMatcher(USER1, USER3, USER4, USER2, USER5)));
    }

    @Test
    public void testUpdateDuplicate() throws Exception {
        User updated = new User(USER2);
        updated.setName("Admin");
        mockMvc.perform(put(REST_URL + USER2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    public void testCreateDuplicate() throws Exception {
        User expected = new User(null, "Natallia", "newPass", Role.ROLE_USER, Role.ROLE_ADMIN);
        mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1))
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isConflict());
    }
}