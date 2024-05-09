package ddareunging.ddareunging_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class ApiTestController {
    @RequestMapping(value = "/**/{path:[^.]*}")
    public String redirect() {
        return "forward:/index.html";
    }
}
