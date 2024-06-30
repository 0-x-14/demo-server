package ddareunging.ddareunging_server.repository;

import ddareunging.ddareunging_server.domain.Like;
import ddareunging.ddareunging_server.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Like l WHERE l.user.userId = :userId")
    void deleteLikesByUserId(@Param("userId") Long userId);
  
    @Query("SELECT l FROM Like l WHERE l.user.userId = :userId")
    List<Like> findLikeByUserId(Long userId);

    void deleteLikeByLikeId(Long likeId);

    Like findLikeByLikeId(Long likeId);
}