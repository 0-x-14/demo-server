package ddareunging.ddareunging_server.controller;

import ddareunging.ddareunging_server.dto.MakeNewCourseRequest;
import ddareunging.ddareunging_server.dto.MakeNewCourseResponse;
import ddareunging.ddareunging_server.service.MakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/course/make-course")
public class MakeCourseController {
    private final MakeCourseService makeCourseService;

    @PostMapping("")
    public ResponseEntity<MakeNewCourseResponse> makeCourse(@RequestBody MakeNewCourseRequest course) {
        return ResponseEntity.status(HttpStatus.CREATED).body(makeCourseService.makeCourse(course));
    }
}