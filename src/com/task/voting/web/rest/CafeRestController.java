package com.task.voting.web.rest;

import com.task.voting.model.Cafe;
import com.task.voting.service.CafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.task.voting.util.ValidationUtil.checkNew;

/**
 * Created by Aspire on 04.03.2017.
 */
@RestController
@RequestMapping(value = CafeRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CafeRestController {
    static final String REST_URL = "/rest/cafes";

    @Autowired
    private CafeService service;

    @GetMapping("/{id}")
    public Cafe get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }

    @GetMapping
    public List<Cafe> getAll() {
        return service.getAll();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Cafe cafe) {
        service.update(cafe);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cafe> createWithLocation(@Valid @RequestBody Cafe cafe) {
        checkNew(cafe);
        Cafe created = service.save(cafe);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
