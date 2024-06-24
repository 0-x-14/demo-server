package ddareunging.ddareunging_server.web.controller;

import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.repository.UserRepository;
import ddareunging.ddareunging_server.service.MyPageService;
import ddareunging.ddareunging_server.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final MyPageService myPageService;

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

    @GetMapping("/notice")
    public ResponseEntity<?> getNotice() {
        try {
            return ResponseEntity.ok(myPageService.getNotice());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("공지사항 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    // 공지사항 조회

    @GetMapping("/myReply")
    public ResponseEntity<?> getRepliesByUserId(@RequestParam("user-id") Long userId) {
        try {
            return ResponseEntity.ok(myPageService.getRepliesByUserId(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("내가 작성한 댓글 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    // 내가 작성한 댓글 조회

}
