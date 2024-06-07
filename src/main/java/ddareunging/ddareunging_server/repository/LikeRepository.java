package ddareunging.ddareunging_server.repository;

import ddareunging.ddareunging_server.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long>  {
    @Query("SELECT l FROM Like l WHERE l.user.userId = :userId")
    List<Like> findLikeByUserId(Long userId);
}
