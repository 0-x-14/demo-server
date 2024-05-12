package ddareunging.ddareunging_server.dto;

import ddareunging.ddareunging_server.domain.Dust;
import ddareunging.ddareunging_server.domain.Weather;
import lombok.Builder;

import java.util.List;

@Builder
public record WeatherResponseDTO (List<String> address, Weather weather, Dust dust, String message) {
}
