package com.task.voting.web.rest;

import com.task.voting.AuthorizedUser;
import com.task.voting.model.Vote;
import com.task.voting.service.VoteService;
import com.task.voting.to.CafeWithVotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.task.voting.util.ValidationUtil.checkResultTime;
import static com.task.voting.util.ValidationUtil.checkVoteTime;

/**
 * Created by Aspire on 04.03.2017.
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/rest/votes";

    @Autowired
    private VoteService service;

    @DeleteMapping("/{userId}/{date}")
    public void delete(@PathVariable("userId") int userId,
                       @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate) {
        Vote vote = service.get(userId, localDate.atTime(LocalTime.MIN), localDate.atTime(LocalTime.MAX));
        service.delete(vote, AuthorizedUser.getUser());
    }

    @GetMapping("/{userId}")
    public List<Vote> getAll(@PathVariable("userId") int userId) {
        return service.getAll(userId);
    }

    @GetMapping("/reports")
    public List<CafeWithVotes> getWinners(
            @RequestParam(name="date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate)
    {
        checkResultTime(localDate);
        return service.getWinners(localDate.atTime(LocalTime.MIN), localDate.atTime(LocalTime.MAX));
    }


    @GetMapping("/{userId}/{date}")
    public Vote get(@PathVariable("userId") int userId,
                    @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate) {
        return service.get(userId, localDate.atTime(LocalTime.MIN), localDate.atTime(LocalTime.MAX));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Valid @RequestBody Vote vote) {
        checkVoteTime(vote.getId().getDateTime());
        Vote saved = service.saveOrUpdate(vote, AuthorizedUser.getUser());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{userId}/{date}")
                .buildAndExpand(
                        saved.getId().getUser().getId(),
                        saved.getId().getDateTime().toLocalDate()
                )
                .toUri();

        return ResponseEntity.created(uriOfNewResource).body(saved);
    }
}
