package fast.campus.fcss14.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    // CSRF 보호 적용
    @PostMapping("/api/v1/hello")
    public String postHello() {
        return "POST /api/v1/hello";
    }

    // CSRF 보호 미적용 (CSRF 토큰 없이 호출 가능)
    @PostMapping("/api/v1/hi")
    public String postHi() {
        return "POST /api/v1/hi";
    }
}
