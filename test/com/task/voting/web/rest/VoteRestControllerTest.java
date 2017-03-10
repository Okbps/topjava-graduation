package com.task.voting.web.rest;

import com.task.voting.model.Vote;
import com.task.voting.service.VoteService;
import com.task.voting.web.json.JsonUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.task.voting.TestData.*;
import static com.task.voting.TestUtil.userHttpBasic;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Aspire on 06.03.2017.
 */
public class VoteRestControllerTest extends AbstractControllerTest{
    private static final String REST_URL = VoteRestController.REST_URL + '/';

    @Autowired
    private VoteService service;

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL + USER_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentListMatcher(VOTES));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(
                REST_URL +
                        USER_ID +
                        "/" +
                        DateTimeFormatter.ISO_LOCAL_DATE.format(VOTE1.getId().getDateTime())
        ))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentMatcher(VOTE1));
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        Vote updated = getUpdatedVote();

        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk());

        Vote returned = service.get(USER_ID, updated.getId().getDateTime().toLocalDate());
        assertEquals(updated.getCafe(), returned.getCafe());
        assertEquals(updated.getId().getDateTime(), returned.getId().getDateTime());
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Vote invalid = new Vote(USER1, LocalDateTime.of(2017, 2, 8, 23, 5), CAFE3);
        mockMvc.perform(put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    @Transactional
    public void testCreate() throws Exception {
        Vote created = getCreatedVote();
        ResultActions action = mockMvc.perform(post(REST_URL)
                        .with(userHttpBasic(USER1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(created))
                        );

        Vote returned = VOTE_MATCHER.fromJsonAction(action);

        VOTE_MATCHER.assertEquals(created, returned);
        VOTE_MATCHER.assertCollectionEquals(Arrays.asList(VOTE1, created), service.getAll(USER_ID));
    }

    @Test
    @Transactional
    public void testCreateSameDate() throws Exception {
        Vote createdLater = new Vote(VOTE1.getId().getUser(),
                VOTE1.getId().getDateTime().plusHours(1),
                CAFE2);

        ResultActions action = mockMvc.perform(post(REST_URL)
                        .with(userHttpBasic(createdLater.getId().getUser()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(createdLater))
                        );

        Vote returned = VOTE_MATCHER.fromJsonAction(action);

        assertEquals(createdLater.getCafe(), returned.getCafe());
        assertEquals(createdLater.getId().getDateTime(), returned.getId().getDateTime());
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(VOTE1))
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk());
        VOTE_MATCHER.assertCollectionEquals(Arrays.asList(), service.getAll(USER_ID));
    }
}