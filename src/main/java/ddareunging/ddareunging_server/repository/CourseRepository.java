package ddareunging.ddareunging_server.repository;

import ddareunging.ddareunging_server.domain.Course;
import ddareunging.ddareunging_server.domain.enums.CourseTheme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findCoursesByTheme(CourseTheme theme);
    List<Course> findCoursesByUserUserId(Long userId);
}