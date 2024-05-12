package ddareunging.ddareunging_server.dto;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Builder
public record MapResponseDTO(double myLat, double myLng) {
}
