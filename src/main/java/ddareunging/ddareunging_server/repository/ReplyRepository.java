package ddareunging.ddareunging_server.repository;

import ddareunging.ddareunging_server.domain.Reply;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByCourseCourseId(Long courseId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Reply r WHERE r.user.userId = :userId")
    void deleteRepliesByUserId(@Param("userId") Long userId);
  
    List<Reply> findRepliesByUserUserId(Long userId);
}
