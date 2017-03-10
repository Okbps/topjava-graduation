package com.task.voting.web.rest;

import com.task.voting.model.Vote;
import com.task.voting.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Aspire on 10.03.2017.
 */
@RestController
@RequestMapping(value = ReportsRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportsRestController {
    static final String REST_URL = "/rest/reports";

    @Autowired
    private VoteService service;

    @GetMapping
    //http://blog.mwaysolutions.com/2014/06/05/10-best-practices-for-better-restful-api/
    public List<Vote> getReport(
            @RequestParam(name="name", required = false) String name,
            @RequestParam(name="date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate)
    {
        return null;
    }

}
