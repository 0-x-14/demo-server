package ddareunging.ddareunging_server.web.controller;
import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.repository.UserRepository;
import ddareunging.ddareunging_server.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class UserProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // @PostMapping("/updateProfile")
    @PostMapping("/signup")
    public String updateProfile(@RequestParam("nickname") String nickname,
                                HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Session expired. Please log in again.");
            return "redirect:";
        }

        try {
            userService.updateUserProfile(user, nickname);
            // 바로 세션에 업데이트된 user 객체를 저장
            session.setAttribute("user", user);
            redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update profile: " + e.getMessage());
        }

        return "redirect:/userInfo"; // 수정된 userInfo 페이지로 리다이렉트
    }

    @GetMapping("/userInfo")
    public String showUserInfo(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:";
        }
        model.addAttribute("user", user);
        return "userinfo";  // userInfo.html로 리다이렉트
    }

}

