package ddareunging.ddareunging_server.controller;

import ddareunging.ddareunging_server.dto.WeatherResponseDTO;
import ddareunging.ddareunging_server.service.HomeWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/home")
public class HomeController {

    private final HomeWeatherService homeWeatherService;

//    @GetMapping("/weather")
//    public ResponseEntity<WeatherResponseDTO> getWeatherOfRegion(@RequestParam("region-id") Long regionId) {
//        return ResponseEntity.ok(homeWeatherService.getWeatherDataOfRegion(regionId));
//    }

    @GetMapping("/weather")
    public ResponseEntity<WeatherResponseDTO> getWeatherOfRegion(@RequestParam("lat") String latitude, @RequestParam("lng") String longitude) {
        // 위도와 경도 값을 입력받도록 변경
        return ResponseEntity.ok(homeWeatherService.getWeatherDataByCoordinates(latitude, longitude));
    }
}
