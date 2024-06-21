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
@CrossOrigin(origins = "http://localhost:3000")


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
            // 토큰 받아와지는지 확인 코드
            session.setAttribute("kakaoToken", accessToken);
            log.info("Access token saved to session: {}", accessToken);  // 로그 추가

            if (existingUser.isPresent()) {
                session.setAttribute("user", existingUser.get());
                model.addAttribute("user", existingUser.get());
                return "home"; // 이미 존재하는 회원이면 home 화면으로
            } else {
                User newUser = userService.saveUser(userInfo); // 새로운 사용자 저장
                session.setAttribute("user", newUser);
                model.addAttribute("user", newUser);
                return "userinfo"; // 새로운 회원이면 userInfo 화면으로
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
        String redirectUri = "https://ddareunging.sepnon3.shop";  // 로그아웃 후 리다이렉트될 URI
        // String redirectUri = "http://localhost:8080";

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

    // 카카오 탈퇴
    @PostMapping("/unlink")
    public String unlink(HttpSession session, Model model) {
        String accessToken = (String) session.getAttribute("kakaoToken");
        log.info("Unlinking with access token: {}", accessToken);  // 로그 추가
        User user = (User) session.getAttribute("user");

        if (accessToken != null && !accessToken.isEmpty()) {
            try {
                kakaoService.unlinkKakao(accessToken);
                log.info("Unlink successful for accessToken: {}", accessToken);
                userService.deleteUser(user); // 사용자 정보 삭제
                session.invalidate(); // 세션 무효화
                model.addAttribute("message", "탈퇴가 완료되었습니다.");
                return "redirect:/"; // 홈 페이지로 리다이렉트
            } catch (IOException e) {
                log.error("Error during Kakao unlink", e);
                model.addAttribute("error", "탈퇴 중 오류가 발생했습니다.");
                return "error";
            }
        } else {
            model.addAttribute("error", "유효한 세션이 없습니다.");
            return "error";
        }
    }


}


