package ddareunging.ddareunging_server.controller;

import ddareunging.ddareunging_server.dto.FindAnotherUserCoursesReponseDTO;
import ddareunging.ddareunging_server.dto.FindCoursesResponseDTO;
import ddareunging.ddareunging_server.dto.FindMyCoursesResponseDTO;
import ddareunging.ddareunging_server.dto.FindMyLikedCoursesResponseDTO;
import ddareunging.ddareunging_server.service.AnotherUserCourseService;
import ddareunging.ddareunging_server.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/likedcourse")
    public ResponseEntity<FindMyLikedCoursesResponseDTO> getLikedCoursesByUser(@RequestParam("user-id") Long userId) {
        return ResponseEntity.ok(courseService.getLikedCoursesByUser(userId));
    }
    // 내가 찜한 코스 조회

    @DeleteMapping("/likedcourse/delete/{like-id}")
    public ResponseEntity<String> deleteLikeByLikeId(@PathVariable("like-id") Long likeId) {
        try {
            courseService.deleteCourse(likeId);
            return ResponseEntity.ok("찜한 코스가 정상적으로 삭제되었습니다");
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("찜한 코스 삭제 중 오류가 발생했습니다.: " + e.getMessage());
        }
    }
    // 찜한 코스 삭제

    @GetMapping("/usercourse")
    public ResponseEntity<FindAnotherUserCoursesReponseDTO> getCoursesByAnotherUser(@RequestParam("user-id") Long userId) {
        return ResponseEntity.ok(anotherUserCourseService.getCoursesByAnotherUser(userId));
    }
    // 사용자별 코스 조회 (다른 사용자의 프로필을 눌러서 해당 사용자가 제작한 코스를 조회하는 API
    // 이전의 mycourse와는 달리 조회하는 사용자의 정보도 같이 반환해야 함
}
