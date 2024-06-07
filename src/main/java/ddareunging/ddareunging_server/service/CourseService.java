package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Course;
import ddareunging.ddareunging_server.domain.Like;
import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.dto.CourseDTO;
import ddareunging.ddareunging_server.dto.FindCoursesResponseDTO;
import ddareunging.ddareunging_server.dto.FindMyCoursesResponseDTO;
import ddareunging.ddareunging_server.dto.FindMyLikedCoursesResponseDTO;
import ddareunging.ddareunging_server.repository.CourseRepository;
import ddareunging.ddareunging_server.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final LikeRepository likeRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, LikeRepository likeRepository) {
        this.courseRepository = courseRepository;
        this.likeRepository = likeRepository;
    }

    @Transactional
    public FindCoursesResponseDTO getCoursesByTheme(Integer theme) {
        // 테마로 코스 조회

        List<Course> courses = courseRepository.findCoursesByTheme(theme);

        List<CourseDTO> coursesDTO = courses.stream()
                .map(course -> {
                    String userNickname = course.getUser().getNickname();
                    return new CourseDTO(course.getCourseId(), course.getCourseName(), course.getCourseImage(), course.getCourseLike(), course.getTheme(), userNickname);
                })
                .collect(Collectors.toList());

        return FindCoursesResponseDTO.builder()
                .theme(theme)
                .courses(coursesDTO)
                .message("테마별 코스가 정상적으로 조회되었습니다.").build();
    }

    @Transactional
    public FindMyCoursesResponseDTO getCoursesByUser(Long userId) {
        // 나만의 코스 조회

        List<Course> courses = courseRepository.findCoursesByUserUserId(userId);

        if (courses.isEmpty()) {
            // 비어 있는 경우, 응답 DTO에 메시지만 설정하여 반환
            return FindMyCoursesResponseDTO.builder()
                    .userId(userId)
                    .courses(new ArrayList<>()) // 비어있는 list 반환
                    .message("나만의 코스 만들기").build();
        } // 조회된 코스가 없는 경우

        List<CourseDTO> myCoursesDTO = courses.stream()
                .map(course -> {
                    String userNickname = course.getUser().getNickname();
                    return new CourseDTO(course.getCourseId(), course.getCourseName(), course.getCourseImage(), course.getCourseLike(), course.getTheme(), userNickname);
                })
                .collect(Collectors.toList());

        return FindMyCoursesResponseDTO.builder()
                .userId(userId)
                .courses(myCoursesDTO)
                .message("나만의 코스가 정상적으로 조회되었습니다.").build();
    }

    @Transactional
    public FindMyLikedCoursesResponseDTO getLikedCoursesByUser(Long userId) {
        // 내가 찜한 코스 조회

        List<Like> likes = likeRepository.findLikeByUserId(userId);

        if (likes.isEmpty()) {
            // 비어 있는 경우, 응답 DTO에 메시지만 설정하여 반환
            return FindMyLikedCoursesResponseDTO.builder()
                    .userId(userId)
                    .courses(new ArrayList<>()) // 비어있는 list 반환
                    .message("찜한 코스가 없습니다.").build();
        } // 조회된 코스가 없는 경우

        List<CourseDTO> likedCoursesDTO = likes.stream()
                .map(like -> {
                    Course course = like.getCourse();
                    String userNickname = course.getUser().getNickname(); // 해당 course가 참조하는 user의 nickname, 즉 코스를 만든 사람의 닉네임을 가져옴
                    return new CourseDTO(course.getCourseId(), course.getCourseName(), course.getCourseImage(), course.getCourseLike(), course.getTheme(), userNickname);
                })
                .collect(Collectors.toList());

        return FindMyLikedCoursesResponseDTO.builder()
                .userId(userId)
                .courses(likedCoursesDTO)
                .message("찜한 코스가 정상적으로 조회되었습니다.").build();

        // 다른 코스 조회 API들처럼 courses(courses)로 처리할 경우 HttpMessageConversionException 문제가 발생하므로 DTO를 만들어서 처리했음
    }
}