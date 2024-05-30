package ddareunging.ddareunging_server.repository;

import ddareunging.ddareunging_server.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findCoursesByTheme(Integer theme);
    List<Course> findByUserUserId(Long userId);

}