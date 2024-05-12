package ddareunging.ddareunging_server.repository;

import ddareunging.ddareunging_server.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findByDistrict(String district);
}