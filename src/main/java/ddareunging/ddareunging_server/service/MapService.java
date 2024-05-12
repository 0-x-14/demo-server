package ddareunging.ddareunging_server.service;


import ddareunging.ddareunging_server.domain.Rental;
import ddareunging.ddareunging_server.dto.MapResponseDTO;
import ddareunging.ddareunging_server.repository.RentalRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class MapService {
    @Autowired
    private RentalRepository rentalRepository;

    public List<MapResponseDTO> getRentalOfMap(double startLat, double endLat, double startLng, double endLng) {
        List<Rental> rentals = null;
        try {
            rentals = rentalRepository.findRentals(startLat, endLat, startLng, endLng);
            log.info("Rentals retrieved: {}", rentals.size());  // 데이터 수 확인

            if (rentals.isEmpty()) {
                log.info("No rentals found within the specified coordinates.");
                return Collections.emptyList();  // 비어 있는 경우 빈 리스트 반환
            }
        } catch (Exception e) {
            log.error("Error retrieving rentals: ", e);  // 예외 발생 시 로그
            return Collections.emptyList();
        }

        List<MapResponseDTO> responses = rentals.stream()
                .map(rental -> {
                    MapResponseDTO dto = new MapResponseDTO(rental.getRentalLat(), rental.getRentalLng());
                    log.info("Creating MapResponseDTO with lat: {}, lng: {}", dto.myLat(), dto.myLng());
                    return dto;
                })
                .collect(Collectors.toList());
        return responses;
    }
}
