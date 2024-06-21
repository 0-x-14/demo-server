package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Course;
//import ddareunging.ddareunging_server.domain.Spot;
import ddareunging.ddareunging_server.domain.Spot;
import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.repository.CourseRepository;
//import ddareunging.ddareunging_server.repository.SpotRepository;
import ddareunging.ddareunging_server.repository.SpotRepository;
import ddareunging.ddareunging_server.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpotRepository spotRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    // 돌아가는 기존코드!! 경유지 때문에 잠시 주석처리
//    public Course findById(Long id) {
//        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
//    }

    public Course findById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        List<Spot> spots = spotRepository.findByCourseCourseId(id);
        course.setSpots(spots);
        return course;
    }

    // 경유지
//    @Transactional
//    public Course findById(Long id) {
//        Course course = courseRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Course not found"));
//        List<Spot> spots = spotRepository.findByCourseCourseId(id);
//        course.setSpots(spots);
//        return course;
//    }

    public Course createCourse(Course course, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        course.setUser(user);
        return courseRepository.save(course);
    }

    // 코스 찜하기 기능
    public Course likeCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setCourseLike(course.getCourseLike() + 1);
        return courseRepository.save(course);
    }

    public Course unlikeCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setCourseLike(course.getCourseLike() - 1);
        return courseRepository.save(course);
    }


    // 출발, 도착, 경유지
//    public Course findByIdWithWaypoints(Long id) {
//        Course course = findById(id);
//        List<Spot> spots = spotRepository.findByCourseId(id);
//        course.setWaypoints(spots);  // Course 엔티티에 Waypoints 필드를 추가해야 합니다.
//        return course;
//    }

}
