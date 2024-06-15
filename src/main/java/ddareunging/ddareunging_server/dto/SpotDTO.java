package ddareunging.ddareunging_server.dto;

import ddareunging.ddareunging_server.domain.enums.SpotType;

public record SpotDTO(SpotType spotType, String spotName, Double spotLat, Double spotLng) {
}
