package ddareunging.ddareunging_server.web.controller;

import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.repository.UserRepository;
import ddareunging.ddareunging_server.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/mypage")
public class MyPageController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // 회원 전용 마이페이지
    @GetMapping("")
    public String showMyPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:";
        }
        model.addAttribute("user", user);
        return "mypage";
    }


    // 회원 프로필 업데이트
    @PostMapping("/updateMyPage")
    public String updateMyPage(@RequestParam("nickname") String newNickname,
                               HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Session expired. Please log in again.");
            return "redirect:";
        }


        try {
            userService.updateMyPage(user, newNickname);
            // 바로 세션에 업데이트된 user 객체를 저장
            session.setAttribute("user", user);
            redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update profile: " + e.getMessage());
        }
        //return "redirect:/updateInfo";
        return "redirect:/mypage";

    }

    // 정보 업데이트 페이지를 보여주는 메서드
    @GetMapping("/updateInfo")
    public String showUpdateInfo(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:";
        }
        model.addAttribute("user", user);
        return "mypage-updateInfo";  // 정보를 업데이트하는 뷰로 리다이렉트
    }



}
