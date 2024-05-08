package ddareunging.ddareunging_server.service;


import ddareunging.ddareunging_server.domain.Rental;
import ddareunging.ddareunging_server.dto.MapResponseDTO;
import ddareunging.ddareunging_server.repository.RentalRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class MapService {
    @Autowired
    private RentalRepository rentalRepository;

    public List<MapResponseDTO> getRentalOfMap(double startLat, double endLat, double startLng, double endLng) {
        List<Rental> rentals = rentalRepository.findRentals(startLat, endLat, startLng, endLng);
        return rentals.stream()
                .map(rental -> new MapResponseDTO(rental.getRentalLat(), rental.getRentalLng()))
                .collect(Collectors.toList());
    }
}
