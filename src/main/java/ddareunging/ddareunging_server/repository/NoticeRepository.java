package ddareunging.ddareunging_server.repository;

import ddareunging.ddareunging_server.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
