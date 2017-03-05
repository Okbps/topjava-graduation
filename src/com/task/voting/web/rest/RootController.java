package com.task.voting.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController extends AbstractUserController {

    @GetMapping("/")
    public String root() {
        return "index";
    }

}
