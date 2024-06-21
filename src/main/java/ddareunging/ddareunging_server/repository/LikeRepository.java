package ddareunging.ddareunging_server.repository;


import ddareunging.ddareunging_server.domain.Like;
import ddareunging.ddareunging_server.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {
    //void deleteByUserId(Long userId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Like l WHERE l.user.user_id = :userId")
    void deleteLikesByUserId(@Param("userId") Long userId);
}
