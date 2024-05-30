package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Course;
import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.dto.FindCoursesResponseDTO;
import ddareunging.ddareunging_server.dto.FindMyCoursesResponseDTO;
import ddareunging.ddareunging_server.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public FindCoursesResponseDTO getCoursesByTheme(Integer theme) {
        // 테마로 코스 조회

        List<Course> courses = courseRepository.findCoursesByTheme(theme);

        courses.forEach(course -> {
            User user = course.getUser();
            if (user != null) {
                // User 엔티티를 명시적으로 초기화하여 nickname을 가져옴
                course.setUserNickname(user.getNickname());
            }
        });

        return FindCoursesResponseDTO.builder()
                .theme(theme)
                .courses(courses).build();
    }

    @Transactional
    public FindMyCoursesResponseDTO getCoursesByUser(Long userId) {
        // 나만의 코스 조회

        List<Course> courses = courseRepository.findByUserUserId(userId);

        courses.forEach(course -> {
            User user = course.getUser();
            if (user != null) {
                // User 엔티티를 명시적으로 초기화하여 nickname을 가져옴
                course.setUserNickname(user.getNickname());
            }
        });

        return FindMyCoursesResponseDTO.builder()
                .userId(userId)
                .courses(courses).build();
    }
}
