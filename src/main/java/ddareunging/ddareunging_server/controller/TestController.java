package ddareunging.ddareunging_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class TestController {

    @RequestMapping(value = "/test/hello")
    @ResponseBody
    public String helloRuckus(Model model) {
        return "됐나?";
    }

}