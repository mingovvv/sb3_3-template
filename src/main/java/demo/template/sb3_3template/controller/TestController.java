package demo.template.sb3_3template.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(Principal principal) {
//        log.info("principal: {}", principal.getName());
        return "ok";
    }

    @GetMapping("/valid-session")
    public String validSession(Principal principal) {
//        log.info("principal: {}", principal.getName());
        return "/valid-session";
    }

    @GetMapping("/invalid-session")
    public String invalidSession(Principal principal) {
//        log.info("principal: {}", principal.getName());
        return "/invalid-session";
    }

}
