package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.domain.Course;
import ddareunging.ddareunging_server.domain.Like;
import ddareunging.ddareunging_server.domain.Spot;
import ddareunging.ddareunging_server.domain.User;
import ddareunging.ddareunging_server.domain.enums.SpotType;
import ddareunging.ddareunging_server.dto.*;
import ddareunging.ddareunging_server.repository.CourseRepository;
import ddareunging.ddareunging_server.repository.LikeRepository;
import ddareunging.ddareunging_server.repository.SpotRepository;
import ddareunging.ddareunging_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final SpotRepository spotRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, LikeRepository likeRepository, UserRepository userRepository, SpotRepository spotRepository) {
        this.courseRepository = courseRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.spotRepository = spotRepository;
    }

    @Transactional
    public FindCoursesResponseDTO getCoursesByTheme(Integer theme) throws Exception {
        // 테마로 코스 조회

        List<Course> courses = courseRepository.findCoursesByTheme(theme);

        if (courses.isEmpty()) {
            // 테마를 찾을 수 없는 경우
            throw new IllegalArgumentException("존재하지 않는 테마입니다");
        }
        // 현재 서비스에 등록된 테마는 두 가지
        // 추후 테마가 추가되어도 서비스에서 자체적으로 제공하는 테마별 추천 코스가 있다고 가정하고 courses가 null인 경우 존재하지 않는 테마입니다라는 메세지를 반환하도록 함

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
    public FindMyCoursesResponseDTO getCoursesByUser(Long userId) throws Exception {
        // 나만의 코스 조회

        User user = userRepository.findUserByUserId(userId);

        if (user == null) {
            // 사용자를 찾을 수 없는 경우
            throw new IllegalArgumentException("존재하지 않는 사용자입니다");
        }

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
    public FindMyLikedCoursesResponseDTO getLikedCoursesByUser(Long userId) throws Exception {
        // 내가 찜한 코스 조회

        User user = userRepository.findUserByUserId(userId);

        if (user == null) {
            // 사용자를 찾을 수 없는 경우
            throw new IllegalArgumentException("존재하지 않는 사용자입니다");
        }

        List<Like> likes = likeRepository.findLikeByUserId(userId);

        if (likes.isEmpty()) {
            // 비어 있는 경우, 응답 DTO에 메시지만 설정하여 반환
            return FindMyLikedCoursesResponseDTO.builder()
                    .userId(userId)
                    .courses(new ArrayList<>()) // 비어있는 list 반환
                    .message("찜한 코스가 없습니다.").build();
        } // 조회된 코스가 없는 경우

        List<LikedCourseDTO> likedCoursesDTO = likes.stream()
                .map(like -> {
                    Long likeId = like.getLikeId();
                    Course course = like.getCourse();
                    String userNickname = course.getUser().getNickname(); // 해당 course가 참조하는 user의 nickname, 즉 코스를 만든 사람의 닉네임을 가져옴
                    return new LikedCourseDTO(likeId, course.getCourseId(), course.getCourseName(), course.getCourseImage(), course.getCourseLike(), course.getTheme(), userNickname);
                })
                .collect(Collectors.toList());
        // 이후 찜한 코스를 삭제할 때 likeId가 필요하므로 LikedCourseDTO를 생성하여 처리함

        return FindMyLikedCoursesResponseDTO.builder()
                .userId(userId)
                .courses(likedCoursesDTO)
                .message("찜한 코스가 정상적으로 조회되었습니다.").build();
    }

    @Transactional
    public void deleteLikeCourseAndUpdateCourseLike(Long likeId) throws Exception {
        // 찜한 코스 삭제

        Like like = likeRepository.findLikeByLikeId(likeId);

        if (like == null) {
            // 잘못된 likeId가 입력되었을 경우
            throw new IllegalArgumentException("존재하지 않는 likeId입니다");
        }

        // 찜한 코스 삭제
        likeRepository.deleteLikeByLikeId(likeId);

        // course 엔티티의 courseLike 값 감소
        Course course = like.getCourse();
        System.out.println("course is : " + course);
        course.setCourseLike(course.getCourseLike() - 1);
        courseRepository.save(course);
    }

    @Transactional
    public RegisterNewCourseResponseDTO postNewCourse(Long userId, RegisterNewCourseRequestDTO registerNewCourseRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        Course course = courseRepository.save(Course.of(user, registerNewCourseRequestDTO));

        List<SpotDTO> spots = registerNewCourseRequestDTO.spots();

        boolean hasStart = spots.stream().anyMatch(spot -> spot.spotType() == SpotType.출발지);
        boolean hasEnd = spots.stream().anyMatch(spot -> spot.spotType() == SpotType.도착지);
        // 출발지와 도착지는 필수적으로 포함하고 있어야 하므로 검증하는 로직 추가

        if (!hasStart && !hasEnd) {
            throw new IllegalArgumentException("출발지와 도착지는 필수로 포함되어야 합니다.");
        } else if (!hasStart) {
            throw new IllegalArgumentException("출발지는 필수로 포함되어야 합니다.");
        } else if (!hasEnd) {
            throw new IllegalArgumentException("도착지는 필수로 포함되어야 합니다.");
        }

        for (SpotDTO spotDTO : spots) {
            Spot spot = new Spot(null, spotDTO.spotType(), spotDTO.spotName(), spotDTO.spotLat(), spotDTO.spotLng(), course);
            spotRepository.save(spot);
        }
        // spotId는 AUTO_INCREMENT 설정이 되어있으므로 null로 설정

        return new RegisterNewCourseResponseDTO(course.getCourseId());
    }
}