package ddareunging.ddareunging_server.controller;

import ddareunging.ddareunging_server.dto.WeatherResponseDTO;
import ddareunging.ddareunging_server.service.HomeWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {

    private final HomeWeatherService homeWeatherService;

    @GetMapping("/weather")
    public ResponseEntity<WeatherResponseDTO> getWeatherOfRegion(@RequestParam("region-id") Long regionId) {
        return ResponseEntity.ok(homeWeatherService.getWeatherDataOfRegion(regionId));
    }
}
