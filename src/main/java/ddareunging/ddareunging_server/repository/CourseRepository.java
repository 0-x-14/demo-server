package ddareunging.ddareunging_server.repository;

import ddareunging.ddareunging_server.domain.Course;

import ddareunging.ddareunging_server.domain.Like;
import jakarta.transaction.Transactional;
import ddareunging.ddareunging_server.domain.enums.CourseTheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Course c WHERE c.user.user_id = :userId")
    void deleteCoursesByUserId(@Param("userId") Long userId);
  
    List<Course> findCoursesByTheme(CourseTheme theme);
    List<Course> findCoursesByUserUserId(Long userId);
}
