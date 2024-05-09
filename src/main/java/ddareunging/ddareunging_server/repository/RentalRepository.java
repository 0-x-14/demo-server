package ddareunging.ddareunging_server.repository;

import ddareunging.ddareunging_server.domain.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query("SELECT r FROM Rental r WHERE r.rental_lat BETWEEN ?1 AND ?2 AND r.rental_lng BETWEEN ?3 AND ?4")
    List<Rental> findRentals(double startLat, double endLat, double startLng, double endLng);
}