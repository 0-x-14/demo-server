package ddareunging.ddareunging_server.controller;

import ddareunging.ddareunging_server.dto.FindAnotherUserCoursesReponseDTO;
import ddareunging.ddareunging_server.dto.FindCoursesResponseDTO;
import ddareunging.ddareunging_server.dto.FindMyCoursesResponseDTO;
import ddareunging.ddareunging_server.service.AnotherUserCourseService;
import ddareunging.ddareunging_server.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    private final AnotherUserCourseService anotherUserCourseService;

    @GetMapping("")
    public ResponseEntity<FindCoursesResponseDTO> getCoursesByTheme(@RequestParam("theme-number") Integer theme) {
        return ResponseEntity.ok(courseService.getCoursesByTheme(theme));
    }
    // 테마에 따른 조회

    @GetMapping("/mycourse")
    public ResponseEntity<FindMyCoursesResponseDTO> getCoursesByUser(@RequestParam("user-id") Long userId) {
        return ResponseEntity.ok(courseService.getCoursesByUser(userId));
    }
    // 나만의 코스 조회

    @GetMapping("/usercourse")
    public ResponseEntity<FindAnotherUserCoursesReponseDTO> getCoursesByAnotherUser(@RequestParam("user-id") Long userId) {
        return ResponseEntity.ok(anotherUserCourseService.getCoursesByAnotherUser(userId));
    }
    // 사용자별 코스 조회 (다른 사용자의 프로필을 눌러서 해당 사용자가 제작한 코스를 조회하는 API
    // 이전의 mycourse와는 달리 조회하는 사용자의 정보도 같이 반환해야 함
}
