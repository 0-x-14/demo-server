package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Course;
import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.dto.CourseDTO;
import ddareunging.ddareunging_server.dto.FindAnotherUserCoursesReponseDTO;
import ddareunging.ddareunging_server.repository.CourseRepository;
import ddareunging.ddareunging_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        try {
            // 다른 사용자의 코스 조회
            User anotherUser = userRepository.findUserByUserId(userId);
            if (anotherUser == null) {
                // 사용자를 찾을 수 없는 경우
                return FindAnotherUserCoursesReponseDTO.builder()
                        .user(null)
                        .courses(new ArrayList<>())
                        .message("존재하지 않는 사용자입니다").build();
            }

            List<Course> courses = courseRepository.findCoursesByUserUserId(userId);

            if (courses.isEmpty()) {
                // 비어 있는 경우, 응답 DTO에 메시지만 설정하여 반환
                return FindAnotherUserCoursesReponseDTO.builder()
                        .user(anotherUser)
                        .courses(new ArrayList<>()) // 비어있는 list 반환
                        .message("아직 코스를 만들지 않은 사용자입니다!").build();
            } // 조회된 코스가 없는 경우
            // 댓글창에서 사용자를 누를 경우, 제작한 코스가 없는 경우도 있을 것이라고 판단해서 추가하였음

            List<CourseDTO> coursesDTO = courses.stream()
                    .map(course -> {
                        String userNickname = course.getUser().getNickname();
                        return new CourseDTO(course.getCourseId(), course.getCourseName(), course.getCourseImage(), course.getCourseLike(), course.getTheme(), userNickname);
                    })
                    .collect(Collectors.toList());

            return FindAnotherUserCoursesReponseDTO.builder()
                    .user(anotherUser)
                    .courses(coursesDTO)
                    .message("사용자 코스 조회 성공").build();
        } catch (Exception e) {
            // 예외 처리
            return FindAnotherUserCoursesReponseDTO.builder()
                    .user(null)
                    .courses(new ArrayList<>())
                    .message("사용자 코스 조회 중 오류가 발생했습니다.: " + e.getMessage()).build();
        }
    }
}