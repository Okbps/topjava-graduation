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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Aspire on 06.03.2017.
 */
@SuppressWarnings("ALL")
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
    public void testGetWinners() throws Exception{
        mockMvc.perform(get(REST_URL+
                "/reports?date="+
                DateTimeFormatter.ISO_LOCAL_DATE.format(VOTE1.getId().getDateTime()))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(CAFE_W_VOTES_MATCHER.contentListMatcher(CAFES_W_VOTES));

    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        Vote updated = getUpdatedVote();

        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated))
                .with(userHttpBasic(updated.getId().getUser())));

        Vote returned = VOTE_MATCHER.fromJsonAction(action);

        VOTE_MATCHER.assertEquals(updated, returned);
    }

    @Test
    public void testUpdateInvalid() throws Exception {
        Vote invalid = new Vote(USER1, LocalDateTime.of(2017, 2, 8, 23, 5), CAFE3);
        mockMvc.perform(post(REST_URL)
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

        created.setUser(USER1);

        VOTE_MATCHER.assertEquals(created, returned);
        VOTE_MATCHER.assertCollectionEquals(Arrays.asList(VOTE1, created), service.getAll(USER_ID));
    }

    @Test
    @Transactional
    public void testCreateSameDate() throws Exception {
        Vote createdLater = getCreatedLater();

        ResultActions action = mockMvc.perform(post(REST_URL)
                        .with(userHttpBasic(createdLater.getId().getUser()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(createdLater))
                        );

        Vote returned = VOTE_MATCHER.fromJsonAction(action);

        VOTE_MATCHER.assertEquals(createdLater, returned);
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        mockMvc.perform(
                delete(REST_URL+"100000/2017-02-08")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk());
        VOTE_MATCHER.assertCollectionEquals(Arrays.asList(), service.getAll(USER_ID));
    }
}