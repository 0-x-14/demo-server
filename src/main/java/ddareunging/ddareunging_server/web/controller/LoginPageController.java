package ddareunging.ddareunging_server.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:3000")
public class LoginPageController {

    @Value("${kakaoApi.serviceKey}")
    private String client_id;

    @Value("${kakaoApi.redirect_uri}")
    private String redirect_uri;

    @GetMapping("")
    public String loginPage(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
        model.addAttribute("location", location);

        return "login";
    }

}
