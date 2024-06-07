package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Course;
import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.dto.FindAnotherUserCoursesReponseDTO;
import ddareunging.ddareunging_server.dto.FindMyCoursesResponseDTO;
import ddareunging.ddareunging_server.repository.CourseRepository;
import ddareunging.ddareunging_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class AnotherUserCourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public AnotherUserCourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public FindAnotherUserCoursesReponseDTO getCoursesByAnotherUser(Long userId) {
        // 다른 사용자의 코스 조회

        User anotherUser = userRepository.findUserByUserId(userId);
        List<Course> courses = courseRepository.findByUserUserId(userId);

        courses.forEach(course -> {
            User user = course.getUser();
            if (user != null) {
                // User 엔티티를 명시적으로 초기화하여 nickname을 가져옴
                course.setUserNickname(user.getNickname());
            }
        });

        return FindAnotherUserCoursesReponseDTO.builder()
                .user(anotherUser)
                .courses(courses).build();
    }
}