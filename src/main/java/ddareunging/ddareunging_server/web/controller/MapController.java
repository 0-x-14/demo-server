package ddareunging.ddareunging_server.web.controller;

import ddareunging.ddareunging_server.dto.MapResponseDTO;
import ddareunging.ddareunging_server.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapController {
    private final MapService mapService;

    @GetMapping("")
    public ResponseEntity<List<MapResponseDTO>> getRental(@RequestParam("startLat") Double startLat, @RequestParam("endLat") Double endLat, @RequestParam("startLng") Double startLng, @RequestParam("endLng") Double endLng) {
        System.out.println("여기까진 들어옴");
        System.out.println("startLat : " + startLat);
        System.out.println("endLat : " + endLat);
        System.out.println("startLng : " + startLng);
        System.out.println("endLng : " + endLng);
        List<MapResponseDTO> markers = mapService.getRentalOfMap(startLat, endLat, startLng, endLng);
        return ResponseEntity.ok(markers);
    }
}
