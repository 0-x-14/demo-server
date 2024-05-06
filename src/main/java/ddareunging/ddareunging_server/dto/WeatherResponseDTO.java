package ddareunging.ddareunging_server.dto;

import ddareunging.ddareunging_server.domain.Dust;
import ddareunging.ddareunging_server.domain.Weather;
import lombok.Builder;

@Builder
public record WeatherResponseDTO (Weather weather, Dust dust, String message) {
}
