package demo.template.sb3_3template.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "05. 인증", description = "인증 API")
@RestController
@RequestMapping("/v1/qna")
public class AuthController {

    @GetMapping("/auth")
    public String auth() {
        return "OK";
    }

}
