package demo.template.sb3_3template.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "ok";
    }

    @GetMapping("/valid-session")
    public String validSession() {
        return "/valid-session";
    }

    @GetMapping("/invalid-session")
    public String invalidSession() {
        return "/invalid-session";
    }

}
