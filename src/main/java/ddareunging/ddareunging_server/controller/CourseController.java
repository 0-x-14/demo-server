package ddareunging.ddareunging_server.controller;

import ddareunging.ddareunging_server.dto.*;
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
    public ResponseEntity<?> getCoursesByTheme(@RequestParam("theme-number") Integer theme) {
        try {
            return ResponseEntity.ok(courseService.getCoursesByTheme(theme));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("테마별 코스 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    // service단에서 try-catch문을 사용하고, controller에서는 return ResponseEntity.ok(courseService.getCoursesByTheme(theme));를 하는 기존의 코드는
    // 예외가 발생하여도 ok를 반환한다는 문제가 있을 것 같아 구조를 변경하였음

    @GetMapping("/mycourse")
    public ResponseEntity<?> getCoursesByUser(@RequestParam("user-id") Long userId) {
        try {
            return ResponseEntity.ok(courseService.getCoursesByUser(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("나만의 코스 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    // 나만의 코스 조회

    @GetMapping("/likedcourse")
    public ResponseEntity<?> getLikedCoursesByUser(@RequestParam("user-id") Long userId) {
        try {
            return ResponseEntity.ok(courseService.getLikedCoursesByUser(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("찜한 코스 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    // 내가 찜한 코스 조회

    @DeleteMapping("/likedcourse/delete/{like-id}")
    public ResponseEntity<String> deleteLikeByLikeId(@PathVariable("like-id") Long likeId) {
        try {
            courseService.deleteLikeCourseAndUpdateCourseLike(likeId);
            return ResponseEntity.ok("찜한 코스가 정상적으로 삭제되었습니다");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("찜한 코스 삭제 중 오류가 발생했습니다.: " + e.getMessage());
        }
    }
    // 찜한 코스 삭제

    @GetMapping("/usercourse")
    public ResponseEntity<?> getCoursesByAnotherUser(@RequestParam("user-id") Long userId) {
        try {
            return ResponseEntity.ok(anotherUserCourseService.getCoursesByAnotherUser(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사용자 코스 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    // 사용자별 코스 조회 (다른 사용자의 프로필을 눌러서 해당 사용자가 제작한 코스를 조회하는 API
    // 이전의 mycourse와는 달리 조회하는 사용자의 정보도 같이 반환해야 함

    @PostMapping("/makecourse")
    public ResponseEntity<?> postNewCourse(@RequestParam("user-id") Long userId, @RequestBody RegisterNewCourseRequestDTO course) {
        try {
            RegisterNewCourseResponseDTO responseDTO = courseService.postNewCourse(userId, course);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (IllegalArgumentException e) {
            // 잘못된 인자가 전달되었을 때의 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다: " + e.getMessage());
        } catch (Exception e) {
            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("코스 제작 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    // 코스 제작
}