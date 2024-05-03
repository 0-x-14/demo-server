package ddareunging.ddareunging_server.controller;

import ddareunging.ddareunging_server.dto.MakeNewCourseRequest;
import ddareunging.ddareunging_server.dto.MakeNewCourseResponse;
import ddareunging.ddareunging_server.service.MakeCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/course/make-course")
public class MakeCourseController {
    private final MakeCourseService makeCourseService;

    @PostMapping("")
    public ResponseEntity<MakeNewCourseResponse> makeCourse(@RequestBody MakeNewCourseRequest Course) {
        log.info("controller 실행");
        return ResponseEntity.status(HttpStatus.CREATED).body(makeCourseService.makeCourse(Course));
    }
}