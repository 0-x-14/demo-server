package ddareunging.ddareunging_server.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class TestController {
    // 배포 서버 체크용 코드 임의 추가
    @GetMapping("/Test")
    public String TestCode() {
        return "테스트";
    }
}
