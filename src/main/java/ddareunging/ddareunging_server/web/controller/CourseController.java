package ddareunging.ddareunging_server.web.controller;

import ddareunging.ddareunging_server.domain.Course;
import ddareunging.ddareunging_server.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @Controller
@RestController
@RequestMapping("/api/courses")
// @CrossOrigin(origins = "http://localhost:8080")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return courseService.findById(id);
    }

//    @GetMapping("/{id}/details")
//    public Course getCourseDetails(@PathVariable Long id) {
//        return courseService.findByIdWithWaypoints(id);
//    }

    @PostMapping
    public Course createCourse(@RequestBody Course course, @RequestParam Long userId) {
        return courseService.createCourse(course, userId);
    }

    // 코스 찜하기 기능
    @PostMapping("/{id}/like")
    public Course likeCourse(@PathVariable Long id) {
        return courseService.likeCourse(id);
    }

    @PostMapping("/{id}/unlike")
    public Course unlikeCourse(@PathVariable Long id) {
        return courseService.unlikeCourse(id);
    }

    // 코스 출발, 경유, 도착
//    @GetMapping("/{courseId}")
//    public Course getCourseDetails(@PathVariable Long courseId) {
//        return courseService.findById(courseId);
//    }

}
