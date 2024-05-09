package ddareunging.ddareunging_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApiTestController {
    @RequestMapping(value = "/**/{path:[^.]*}")
    public String redirect() {
        return "forward:/index.html";
    }
}
