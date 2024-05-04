package ddareunging.ddareunging_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    // 배포 서버 체크용 코드 임의 추가
    @GetMapping("")
    @ResponseBody
    public String TestCode(@RequestParam("request") String request) {
        return request;
    }
}