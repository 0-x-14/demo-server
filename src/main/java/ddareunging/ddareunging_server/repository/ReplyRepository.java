package ddareunging.ddareunging_server.repository;

import ddareunging.ddareunging_server.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findRepliesByUserUserId(Long userId);
}
