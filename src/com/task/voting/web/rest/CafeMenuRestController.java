package com.task.voting.web.rest;

import com.task.voting.model.CafeMenu;
import com.task.voting.service.CafeMenuService;
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
@RequestMapping(CafeMenuRestController.REST_URL)
public class CafeMenuRestController {
    static final String REST_URL = "/rest/cafemenu";

    @Autowired
    private CafeMenuService service;

    @GetMapping("/{id}")
    public CafeMenu get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }

    @GetMapping
    public List<CafeMenu> getAll() {
        return service.getAll();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody CafeMenu cafeMenu, @PathVariable("id") int id) {
        service.update(cafeMenu);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CafeMenu> createWithLocation(@Valid @RequestBody CafeMenu cafeMenu) {
        checkNew(cafeMenu);
        CafeMenu created = service.save(cafeMenu);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
