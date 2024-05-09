package ddareunging.ddareunging_server.web.controller;

import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.repository.UserRepository;
import ddareunging.ddareunging_server.service.KakaoService;
import ddareunging.ddareunging_server.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
//@RestController
@Controller
@RequestMapping("")


public class LoginController {

    @Value("${kakaoApi.serviceKey}")
    private String client_id;


    @Autowired
    private KakaoService kakaoService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, HttpSession session, Model model) {
        try {
            String accessToken = kakaoService.getAccessTokenFromKakao(client_id, code);
            HashMap<String, Object> userInfo = kakaoService.getUserInfo(accessToken);
            Optional<User> existingUser = userRepository.findById(Long.parseLong(userInfo.get("id").toString()));

            if (existingUser.isPresent()) {
                session.setAttribute("user", existingUser.get());
                model.addAttribute("user", existingUser.get());
                return "home"; // 이미 존재하는 회원이면 home 화면으로
            } else {
                User newUser = userService.saveUser(userInfo); // 새로운 사용자 저장
                session.setAttribute("user", newUser);
                model.addAttribute("user", newUser);
                return "userInfo"; // 새로운 회원이면 userInfo 화면으로
            }
        } catch (IOException e) {
            log.error("Error during the authentication process: " + e.getMessage());
            model.addAttribute("error", "Authentication failed due to server error.");
            return "error";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        String accessToken = (String) session.getAttribute("kakaoToken");
        String clientId = client_id;  // 카카오 앱 키를 여기에 입력
        // String redirectUri = "https://ddareunging.sepnon3.shop";  // 로그아웃 후 리다이렉트될 URI
        String redirectUri = "http://localhost:8080";

        if (accessToken != null && !accessToken.isEmpty()) {
            try {
                kakaoService.logoutKakao(accessToken);
                log.info("Logout successful for accessToken: {}", accessToken);
            } catch (IOException e) {
                log.error("Error during Kakao logout", e);
            }
        }

        // 세션 무효화
        session.invalidate();

        // 카카오 로그아웃 페이지로 리다이렉트
        String logoutUrl = "https://kauth.kakao.com/oauth/logout?client_id=" + clientId + "&logout_redirect_uri=" + redirectUri;
        return "redirect:" + logoutUrl;

    }


}


