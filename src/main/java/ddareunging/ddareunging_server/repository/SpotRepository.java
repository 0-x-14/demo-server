package ddareunging.ddareunging_server.repository;

import ddareunging.ddareunging_server.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SpotRepository extends JpaRepository<Spot, Long> {
    List<Spot> findByCourseCourseId(Long courseId);
}
