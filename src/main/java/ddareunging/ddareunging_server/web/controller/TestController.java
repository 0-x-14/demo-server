package ddareunging.ddareunging_server.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class TestController {

    @GetMapping(value = "/test/hello")
    @ResponseBody
    public String helloRuckus(Model model) {
        return "됐나?";
    }

}