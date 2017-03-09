package com.task.voting.web.rest;

import com.task.voting.AuthorizedUser;
import com.task.voting.model.Vote;
import com.task.voting.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Aspire on 04.03.2017.
 */
@RestController
@RequestMapping(VoteRestController.REST_URL)
public class VoteRestController {
    static final String REST_URL = "/rest/vote";

    @Autowired
    private VoteService service;

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@Valid @RequestBody Vote vote) {
        service.delete(vote, AuthorizedUser.get().getUser());
    }

    @GetMapping("/{id}")
    public List<Vote> getAll(@PathVariable("id") int userId) {
        return service.getAll(userId);
    }

    @GetMapping("/{id}/{dateTime}")
    public Vote get(@PathVariable("id") int userId,
                    @PathVariable("dateTime") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime localDateTime) {
        return service.get(userId, localDateTime);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Vote vote, @PathVariable("id") int userId) {
        service.save(vote, AuthorizedUser.id());
//        service.update(vote);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Valid @RequestBody Vote vote) {
        Vote created = service.save(vote, AuthorizedUser.id());

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

//    @PostMapping(consumes ="text/plain;charset=UTF-8")
//    public ResponseEntity<Vote> createWithLocation(
//            @RequestParam(value = "userId") int userId,
//            @RequestParam(value = "dateTime") @DateTimeFormat LocalDateTime ldt,
//            @RequestParam(value = "cafeId") int cafeId
//    ){
//        Vote created = service.save(userId, ldt, cafeId);
//        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path(REST_URL + "/{id}/{dateTime}")
//                .buildAndExpand(created.getId(), ldt).toUri();
//
//        return ResponseEntity.created(uriOfNewResource).body(created);
//    }
}
