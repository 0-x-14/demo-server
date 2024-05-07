package ddareunging.ddareunging_server.service;

import ddareunging.ddareunging_server.controller.HomeController;
import ddareunging.ddareunging_server.domain.Dust;
import ddareunging.ddareunging_server.domain.Region;
import ddareunging.ddareunging_server.domain.Weather;
import ddareunging.ddareunging_server.dto.WeatherResponseDTO;
import ddareunging.ddareunging_server.repository.RegionRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Transactional
@Service
public class HomeWeatherService {

    // getWeather


    private final EntityManager em;
    private final RegionRepository regionRepository;
    private final String weatherApiServiceKey;
    private final String kakaoApiServiceKey;
    public GetAddressService getAddressService;
    public GetWeatherService getWeatherService;
    public GetDustService getDustService;

    @Autowired
    public HomeWeatherService(EntityManager em, RegionRepository regionRepository,
                              GetAddressService getAddressService, GetWeatherService getWeatherService,
                              GetDustService getDustService,
                              @Value("${weatherApi.serviceKey}") String weatherServiceKey,
                              @Value("${kakaoApi.serviceKey}") String kakaoServiceKey) {
        this.em = em;
        this.regionRepository = regionRepository;
        this.getAddressService = getAddressService;
        this.getWeatherService = getWeatherService;
        this.getDustService = getDustService;
        this.weatherApiServiceKey = weatherServiceKey;
        this.kakaoApiServiceKey = kakaoServiceKey;
    }

    public WeatherResponseDTO getWeatherDataByCoordinates(String latitude, String longitude) {
        try {
            List<String> address = getAddressService.getAddress(kakaoApiServiceKey, latitude, longitude);
            String district = address.get(0);
            String dong = address.get(1);

            log.info("district is : " + district + " and dong is : " + dong);

            // 해당 지역 조회
            // 지역 조회 수정
            Region region = regionRepository.findByDistrict(district);
            if (region == null) {
                log.error("No region found for district: " + district);
                return WeatherResponseDTO.builder()
                        .message("지역 정보를 찾을 수 없습니다.").build();
            }

            // 요청 시각 조회
            LocalDateTime now = LocalDateTime.now(); // 현재 시간을 불러옴
            String yyyyMMdd = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            int hour = now.getHour();
            int min = now.getMinute();
            if(min <= 30) { // 30분 전에는 자료가 없으므로 이전 시각을 기준으로 함
                hour -= 1;
            }
            String hourStr = "hourStr";
            if(hour < 10) { // 0900 과 같이 조회해야 함
                hourStr = "0" + hour + "00";
            } else {
                hourStr = hour + "00"; // 정시 기준
            }
            String nx = Integer.toString(region.getNx());
            String ny = Integer.toString(region.getNy());
            String currentChangeTime = now.format(DateTimeFormatter.ofPattern("yy.MM.dd ")) + hour;

            Weather prevWeather = region.getWeather();
            Dust prevDust = region.getDust();
            if(prevWeather != null && prevWeather.getLastUpdateTime() != null) {
                if(prevWeather.getLastUpdateTime().equals(currentChangeTime)) {
                    // 마지막으로 저장한 시간 이후로 데이터가 업데이트 되지 않았다면 기존의 데이터를 그대로 넘김
                    // 미세먼지 정보와 날씨 정보는 함께 업데이트하므로 if문은 prevWeather에 대해서만 검사하였음
                    return WeatherResponseDTO.builder()
                            .address(address)
                            .weather(prevWeather)
                            .dust(prevDust)
                            .message("OK").build();
                }
            }


            log.info("API 요청 발송 >>> 지역: {}, 연월일: {}, 시각: {}", region, yyyyMMdd, hourStr);


            try {
                log.info("weather 서비스 호출 >>> 서비스키 : {}, 연월일 : {}, 위도 : {}, 경도 : {}, 마지막 업데이트 시간 : {}", weatherApiServiceKey, yyyyMMdd, hourStr, nx, ny, currentChangeTime);
                Weather weather = getWeatherService.fetchWeatherData(weatherApiServiceKey, yyyyMMdd, hourStr, nx, ny, currentChangeTime);
                region.updateRegionWeather(weather); // 날씨 정보 업데이트

                log.info("dust 서비스 호출 >>> 서비스키 : {}, 구 이름 : {}", weatherApiServiceKey, district);
                Dust dust = getDustService.fetchDustData(weatherApiServiceKey,district);
                region.updateRegionDust(dust); // 미세먼지 정보 업데이트

                return WeatherResponseDTO.builder()
                        .address(address)
                        .weather(weather)
                        .dust(dust)
                        .message("OK").build();
            } catch (IOException e) {
                return WeatherResponseDTO.builder()
                        .address(address)
                        .weather(null)
                        .dust(null)
                        .message("날씨 및 미세먼지 정보 조회에 실패했습니다. 잠시 후 다시 시도해주세요").build();
            }
        } catch (IOException e) {
            return WeatherResponseDTO.builder()
                    .address(null)
                    .weather(null)
                    .dust(null)
                    .message("위치 정보 조회에 실패했습니다. 잠시 후 다시 시도해주세요").build();

        }
    }
}
